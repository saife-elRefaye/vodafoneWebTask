Feature: search headphones then buy within given budget

  As a guest user
  I want to browse Noon.com
  So I can lookup headphones then browse the results in the price range given to me "budget"

  Background:
    Given User opens the browser

  Scenario: Browse noon and search for headsets
    Given User navigates to the homepage
    When User searches for "headphones"
    Then User applies price filter from "100" to "300"
    When User adds 3 "Headphones" items to the cart within budget from 410 to 510
    Then User verifies the cart has 3 items priced from 490 to 510

  Scenario Outline: Browse noon and search for headsets and checks the cart
    Given User navigates to the homepage
    When User searches for "<item>"
    Then User applies price filter from "<minOfItemPrice>" to "<maxOfItemPrice>"
    When User adds <numberOfItems> "<item>" items to the cart within budget from <minOfBudget> to <maxOfBudget>
    Then User verifies the cart has <numberOfItems> items priced from <minOfBudget> to <maxOfBudget>

    Examples:
      | item       | minOfItemPrice | maxOfItemPrice | numberOfItems | minOfBudget | maxOfBudget |
      | headphones | 100            | 300            | 3             | 410         | 510         |
      | headphones | 100            | 300            | 4             | 500         | 1000        |
