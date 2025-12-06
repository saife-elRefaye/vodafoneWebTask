package org.vodafoneWeb.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartHelper {

    WebDriver driver;
    WebDriverWait wait;

    public ShoppingCartHelper(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Helper class to store product info
    private static class ProductItem {
        String name;
        double price;
        WebElement addButton;

        ProductItem(String name, double price, WebElement addButton) {
            this.name = name;
            this.price = price;
            this.addButton = addButton;
        }
    }

    /**
     * Adds items to the cart based on search term and budget constraints
     */
    public void addItemsWithinBudget(int itemCount, int minBudget, int maxBudget, String searchTerm) throws InterruptedException {
        List<ProductItem> candidates = new ArrayList<>();
        Actions actions = new Actions(driver);
        int scrollAttempts = 0;

        while (candidates.size() < itemCount && scrollAttempts < 10) {
            List<WebElement> items = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.cssSelector("div[data-qa='plp-product-box']"))
            );

            for (WebElement item : items) {
                try {
                    String name = item.findElement(By.cssSelector("h2[data-qa='plp-product-box-name']")).getText();
                    if (!name.toLowerCase().contains(searchTerm.toLowerCase())) continue;

                    String priceText = item.findElement(By.cssSelector("div[data-qa='plp-product-box-price'] .Price-module-scss-module__q-4KEG__amount"))
                            .getText().replaceAll("[^0-9.]", "");
                    double price = Double.parseDouble(priceText);

                    WebElement addBtnImg = item.findElement(By.cssSelector("button img[alt='add-to-cart']"));
                    WebElement addBtn = addBtnImg.findElement(By.xpath("./.."));

                    ProductItem p = new ProductItem(name, price, addBtn);
                    if (!candidates.contains(p)) candidates.add(p);
                } catch (Exception ignored) {}
            }

            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 1000);");
            Thread.sleep(1000);
            scrollAttempts++;
        }

        if (candidates.size() < itemCount) {
            System.out.println("Not enough products found on the page.");
            return;
        }

        List<ProductItem> selectedItems = findClosestCombination(candidates, itemCount, maxBudget);

        if (selectedItems.isEmpty()) {
            System.out.println("Could not find combination within budget.");
            return;
        }

        double total = 0;
        for (ProductItem product : selectedItems) {
            boolean clicked = false;
            int retries = 0;
            while (!clicked && retries < 3) {
                try {
                    actions.moveToElement(product.addButton).pause(Duration.ofMillis(300)).perform();
                    ((JavascriptExecutor) driver).executeScript(
                            "window.scrollTo({top: arguments[0].getBoundingClientRect().top + window.scrollY - 100});",
                            product.addButton
                    );
                    product.addButton.click();
                    Thread.sleep(500);
                    clicked = true;
                } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
                    retries++;
                    Thread.sleep(500);
                }
            }
            total += product.price;
        }

        System.out.println("Added " + selectedItems.size() + " items. Total price: " + total);
    }

    private List<ProductItem> findClosestCombination(List<ProductItem> candidates, int itemCount, double maxBudget) {
        List<ProductItem> bestCombo = new ArrayList<>();
        double bestTotal = 0;
        int n = candidates.size();

        int[] indices = new int[itemCount];
        for (int i = 0; i < itemCount; i++) indices[i] = i;

        while (indices[0] <= n - itemCount) {
            double total = 0;
            List<ProductItem> combo = new ArrayList<>();
            for (int idx : indices) {
                combo.add(candidates.get(idx));
                total += candidates.get(idx).price;
            }

            if (total <= maxBudget && total > bestTotal) {
                bestCombo = combo;
                bestTotal = total;
            }

            int t = itemCount - 1;
            indices[t]++;
            while (t > 0 && indices[t] >= n - (itemCount - 1 - t)) {
                t--;
                indices[t]++;
                for (int j = t + 1; j < itemCount; j++) indices[j] = indices[j - 1] + 1;
            }
            if (indices[0] > n - itemCount) break;
        }
        return bestCombo;
    }

    /**
     * Validates cart items count and total price
     */
    public boolean validateCartItemsAndTotal(int expectedItemCount, int minBudget, int maxBudget) throws InterruptedException {

        Actions actions = new Actions(driver);
        By cartButton = By.cssSelector("a[data-qa='btn_cartLink-Header-Desktop']");
        By itemPriceLocator = By.cssSelector("div[data-qa='cart-item-price'] b");

        WebElement cartIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(cartButton));
        actions.moveToElement(cartIcon).perform();

        cartIcon.click();

        // Give a short pause for AJAX content to load
        Thread.sleep(2000);

        // Wait for prices to appear
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(itemPriceLocator));

        List<WebElement> priceElements = driver.findElements(itemPriceLocator);
        int actualItemCount = priceElements.size();
        double total = 0;

        for (WebElement price : priceElements) {
            String text = price.getText().replaceAll("[^0-9.]", "");
            if (!text.isEmpty()) {
                total += Double.parseDouble(text);
            }
        }

        System.out.println("Cart item count = " + actualItemCount);
        System.out.println("Cart total price = " + total + " EGP");

        boolean countMatches = (actualItemCount == expectedItemCount);
        boolean totalWithinRange = (total >= minBudget && total <= maxBudget);

        if (!countMatches) {
            System.out.println("❌ Item count mismatch! Expected " + expectedItemCount + " but found " + actualItemCount);
        }

        if (!totalWithinRange) {
            System.out.println("❌ Total price NOT within allowed range: " + minBudget + " - " + maxBudget);
        }

        // Pause 5 seconds after validation
        Thread.sleep(5000);

        return (countMatches && totalWithinRange);
    }
}
