package org.vodafoneWeb.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class homePage {

    WebDriver driver;
    WebDriverWait wait;

    public homePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void openUrl(String url) {
        driver.get(url);
    }

    public void selectCategory(String categoryName) {
        WebElement category = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//span[normalize-space()='" + categoryName + "']")
                )
        );

        Actions actions = new Actions(driver);
        actions.moveToElement(category).perform(); // hover, no click

        System.out.println("Hovered over category: " + categoryName);
    }

}
