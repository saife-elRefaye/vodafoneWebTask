Feature: The user lookup headphones then browse the results in the price range given to me "budget"

  Background:
    Given User opens the browser
    And User navigates to the homepage

  @Regression @AddItemsToCart
  Scenario: Browse noon and search for headsets
    When User searches for "headphones"
    And User applies price filter from "100" to "300"
    And User adds 3 "Headphones" items to the cart within budget from 410 to 510
    Then User verifies the cart has 3 items priced from 490 to 510

  @Regression @AddDifferentItemsRangeToCart
  Scenario Outline: Browse noon and search for headsets and checks the cart
    When User searches for "<item>"
    And User applies price filter from "<minOfItemPrice>" to "<maxOfItemPrice>"
    And User adds <numberOfItems> "<item>" items to the cart within budget from <minOfBudget> to <maxOfBudget>
    Then User verifies the cart has <numberOfItems> items priced from <minOfBudget> to <maxOfBudget>

    Examples:
      | item       | minOfItemPrice | maxOfItemPrice | numberOfItems | minOfBudget | maxOfBudget |
      | headphones | 100            | 300            | 3             | 410         | 510         |
      | headphones | 100            | 300            | 4             | 500         | 1000        |
