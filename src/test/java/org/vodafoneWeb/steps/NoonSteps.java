package org.vodafoneWeb.steps;
import org.openqa.selenium.WebDriver;
import org.vodafoneWeb.hooks.Hooks;
import org.vodafoneWeb.pages.*;
import io.cucumber.java.en.*;
import static org.testng.Assert.assertTrue;
import org.vodafoneWeb.utils.configReader;

public class NoonSteps {

    WebDriver driver;
    SearchPage searchPage;
    homePage homePage;
    filterPage filterPage;
    ResultsPage resultsPage;
    ShoppingCartHelper cartHelper;

    @Given("User opens the browser")
    public void user_opens_the_browser() {
        driver = Hooks.driver;
        homePage = new homePage(driver);
        filterPage = new filterPage(driver);
        resultsPage = new ResultsPage(driver);
        searchPage = new SearchPage(driver);
        cartHelper = new ShoppingCartHelper(driver);
    }

    @And("User navigates to the homepage")
    public void user_navigates_to_homepage() {
        String baseUrl = configReader.get("baseUrl");
        homePage.openUrl(baseUrl);
    }



    @When("User selects category {string}")
    public void user_selects_category(String categoryName) {
        homePage.selectCategory(categoryName);
    }

    @Then("User should see brand {string} applied")
    public void user_should_see_brand_applied(String brand) {
        assertTrue(filterPage.isBrandApplied(brand), "Brand filter is not applied!");
        System.out.println("✔ Brand '" + brand + "' is applied");
    }

    @Then("User applies price filter from {string} to {string}")
    public void user_applies_price_filter(String from, String to) {
        filterPage.setPriceRange(from, to);
    }

    @Then("User applies brand filter {string} in category {string}")
    public void user_applies_brand_filter_in_category(String brand, String category) {
        filterPage.hoverAndSelectBrand(category, brand);
    }

    @Then("User sorts results by Best Rated")
    public void user_sorts_results_by() {
        filterPage.applySortByBestRated();
    }

    @Then("User should see items matching category {string}, brand {string}, price from {string} to {string}")
    public void user_should_see_filtered_items(String category, String brand, String minPriceStr, String maxPriceStr) {
        int minPrice = Integer.parseInt(minPriceStr);
        int maxPrice = Integer.parseInt(maxPriceStr);
        boolean allGood = resultsPage.verifyProductsMatchFilters(category, brand, minPrice, maxPrice);
        assertTrue(allGood, "Filters not correctly applied on the product list!");
        System.out.println("Products match category: " + category + ", brand: " + brand +
                ", price range: " + minPrice + "-" + maxPrice +
                ", and sorting (Best Rated).");
    }

    @When("User searches for {string}")
    public void userSearchesFor(String searchText) {
        searchPage.searchForItem(searchText);
    }

    @Then("User should see no search results and the empty state")
    public void userShouldSeeNoSearchResultsAndEmptyState() {
        if (searchPage.isEmptyStateDisplayed()) {
            System.out.println("Empty state is displayed correctly!");
        } else {
            System.out.println("Empty state NOT displayed!");
            throw new AssertionError("Empty state element not found!");
        }
    }

    @When("User adds {int} {string} items to the cart within budget from {int} to {int}")
    public void userAddsItemsWithinBudget(int itemCount, String itemName, int minBudget, int maxBudget) throws InterruptedException {
        cartHelper.addItemsWithinBudget(itemCount, minBudget, maxBudget, itemName);
    }
    @Then("User verifies the cart has {int} items priced from {int} to {int}")
    public void user_verifies_cart(int expectedCount, int minBudget, int maxBudget) throws InterruptedException {
        // Directly propagate the exception
        boolean ok = cartHelper.validateCartItemsAndTotal(expectedCount, minBudget, maxBudget);
        assertTrue(ok, "Cart validation failed!");
    }


}
