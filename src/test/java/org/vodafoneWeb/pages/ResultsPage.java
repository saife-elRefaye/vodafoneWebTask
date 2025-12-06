package org.vodafoneWeb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ResultsPage {

    WebDriver driver;
    WebDriverWait wait;

    public ResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Check if a specific brand filter is applied
    public boolean verifyProductsMatchFilters(String expectedCategory, String expectedBrand, int minPrice, int maxPrice) {
        boolean allGood = true;

        // 1. Verify category (contains, not exact match)
        try {
            String categoryText = driver.findElement(By.xpath("//*[@id='catalog-page-container']/div/div[2]/div[1]/div[2]/div/div/ul/li/div/ul/a/label"))
                    .getText().trim();
            if (!categoryText.contains(expectedCategory)) {
                System.out.println("Category mismatch. Expected: " + expectedCategory + ", Found: " + categoryText);
                allGood = false;
            } else {
                System.out.println("Category matches: " + categoryText);
            }
        } catch (Exception e) {
            System.out.println("Failed to find category element: " + e.getMessage());
            allGood = false;
        }

        // 2. Verify brand for all products
        try {
            List<WebElement> brands = driver.findElements(By.xpath("//*[@id='catalog-page-container']/div/div[2]/div[2]/div[2]/div/div[1]/a"));
            for (WebElement brandEl : brands) {
                if (!brandEl.getText().toLowerCase().contains(expectedBrand.toLowerCase())) {
                    System.out.println("Brand mismatch found: " + brandEl.getText());
                    allGood = false;
                }
            }
            System.out.println("All products have the correct brand");
        } catch (Exception e) {
            System.out.println("Failed to find brand elements: " + e.getMessage());
            allGood = false;
        }

        // 3. Verify price range
        try {
            List<WebElement> prices = driver.findElements(By.xpath("//*[@id='catalog-page-container']/div/div[2]/div[2]/div[2]/div/div[2]//span[contains(@class,'price')]"));
            for (WebElement priceEl : prices) {
                int price = Integer.parseInt(priceEl.getText().replaceAll("[^0-9]", ""));
                if (price < minPrice || price > maxPrice) {
                    System.out.println("Price out of range: " + price);
                    allGood = false;
                }
            }
            System.out.println("Price filter applied correctly");
        } catch (Exception e) {
            System.out.println("Failed to find price elements: " + e.getMessage());
            allGood = false;
        }

        // 4. Verify sorting (Best Rated)
        try {
            String sortText = driver.findElement(By.xpath("//*[@id='catalog-page-container']/div/div[2]/div[2]/div[1]/div[2]/div/button/div/div[2]/span[2]")).getText();
            if (!sortText.equalsIgnoreCase("Best Rated")) {
                System.out.println("Sort mismatch. Expected: Best Rated, Found: " + sortText);
                allGood = false;
            } else {
                System.out.println("Sort is set to Best Rated");
            }
        } catch (Exception e) {
            System.out.println("Failed to find sorting element: " + e.getMessage());
            allGood = false;
        }

        return allGood;
    }



}
