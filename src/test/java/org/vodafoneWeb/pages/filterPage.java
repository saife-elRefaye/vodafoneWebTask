package org.vodafoneWeb.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class filterPage {

    WebDriver driver;
    WebDriverWait wait;

    public filterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Helper to remove hover by moving mouse to neutral spot
    private void removeHover() {
        try {
            Actions actions = new Actions(driver);
            WebElement body = driver.findElement(By.tagName("body"));
            actions.moveToElement(body, 5, 5).perform();
            System.out.println("Hover removed");
        } catch (Exception e) {
            System.out.println("Could not remove hover: " + e.getMessage());
        }
    }

    public void setPriceRange(String min, String max) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Locate the price filter section
        WebElement priceFilterSection = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#catalog-page-container > div > div.ProductListDesktop-module-scss-module__okqu8G__container > div.FiltersWrapper-module-scss-module__7iwmHW__sidebar > div:nth-child(4)")
        ));

        // Scroll into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", priceFilterSection);

        // Click expand button
        WebElement expandButton = priceFilterSection.findElement(By.cssSelector("div > button > div > img"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", expandButton);

        // Wait for inputs
        WebElement minInput = wait.until(ExpectedConditions.visibilityOf(
                priceFilterSection.findElement(By.cssSelector("div:nth-child(1) > input[type=number]"))
        ));
        WebElement maxInput = priceFilterSection.findElement(By.cssSelector("div:nth-child(3) > input[type=number]"));

        // Clear and type min
        minInput.clear();
        minInput.sendKeys(min);

        // Wait 0.5s
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }

        // Clear and type max
        maxInput.clear();
        maxInput.sendKeys(max);

        // Wait 0.5s
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }

        // Click Go button
        WebElement goButton = priceFilterSection.findElement(By.cssSelector("form > button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", goButton);
        goButton.click();

        // Remove any hover after interaction
        removeHover();
    }


    public void hoverAndSelectBrand(String category, String brandName) {
        Actions actions = new Actions(driver);

        // Hover over the main category
        String categoryXpath = "//span[text()='" + category + "']/ancestor::a";
        WebElement categoryElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(categoryXpath))
        );
        actions.moveToElement(categoryElement).pause(Duration.ofSeconds(1)).perform();
        System.out.println("Hovered on category: " + category);

        // Wait for brands container to appear
        WebElement brandsContainer = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("#default-header-desktop div.MainCategories-module-scss-module__OBdwmW__gridBrands")
                )
        );

        // Locate the brand by its span text
        String brandXpath = ".//li//a/span[text()='" + brandName + "']";
        WebElement brandElement = brandsContainer.findElement(By.xpath(brandXpath));

        // Hover + click
        actions.moveToElement(brandElement).pause(Duration.ofMillis(500)).click().perform();
        System.out.println("Clicked brand: " + brandName);

        // Remove hover after clicking brand
        removeHover();
    }

    public boolean isBrandApplied(String brandName) {
        try {
            // Locate the checkbox for the brand by label text
            WebElement brandCheckbox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@class,'BrandFilter-module')]//label[contains(.,'" + brandName + "')]//input[@type='checkbox']")
            ));
            return brandCheckbox.isSelected(); // Returns true if the brand checkbox is checked
        } catch (TimeoutException e) {
            System.out.println("Brand checkbox not found: " + brandName);
            return false;
        }
    }


    public void applySortByBestRated() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Scroll to top
        js.executeScript("window.scrollTo(0, 0);");
        try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }

        // Locate dropdown button and scroll into view
        WebElement element2 = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"catalog-page-container\"]/div/div[2]/div[2]/div[1]/div[2]/div/button")
        ));
        js.executeScript("arguments[0].scrollIntoView(true);", element2);
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }

        // Click dropdown
        js.executeScript("arguments[0].click();", element2);
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }

        // Click "Best Rated"
        WebElement element1 = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"overlay-portal\"]/div[2]/ul/li[5]/a")
        ));
        js.executeScript("arguments[0].click();", element1);

        System.out.println("Sort applied: Best Rated");

        // Wait after finishing
        try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }

        // Remove hover after sorting
        removeHover();
    }

}
