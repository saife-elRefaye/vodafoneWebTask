package org.vodafoneWeb.pages;

import org.openqa.selenium.WebDriver;

public class openPage {

    private WebDriver driver;

    public openPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open(String url) {
        driver.get(url);
    }

    public String getTitle() {
        return driver.getTitle();
    }
}
