package org.vodafoneWeb.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class categoryPage {

    WebDriver driver;
    WebDriverWait wait;
    // Define the filter section locator once
    private final By filterSectionLocator = By.xpath("//div[contains(@class,'filters')]");

    public categoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }



}