package org.vodafoneWeb.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class SearchPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void searchForItem(String searchText) {
        try {
            WebElement searchInput = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//*[@id=\"default-header-desktop\"]/header/div/div[2]//input")
                    )
            );
            searchInput.click();
            searchInput.clear();
            searchInput.sendKeys(searchText);
            searchInput.sendKeys(Keys.ENTER);

            System.out.println("Searched for: " + searchText);

        } catch (TimeoutException e) {
            System.out.println("Search element not found: " + e.getMessage());
        }
    }

    public boolean isEmptyStateDisplayed() {
        try {
            WebElement emptyState = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("div.EmptyState-module-scss-module__BvetVG__container")
            ));

            String heading = emptyState.findElement(By.tagName("h3")).getText();
            return heading.contains("We couldn't find what you were looking for");
        } catch (Exception e) {
            return false;
        }
    }
}


