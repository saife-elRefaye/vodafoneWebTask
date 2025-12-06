Feature: search headphones then buy within given budget

  As a guest user
  I want to browse Noon.com
  So i can lookup headphones then brows the results in the price range given to me "budget"

  Background:
    Given User opens the browser

  Scenario: Browse noon and search for headsets
    Given User navigates to the homepage
    When  User searches for "@@@@@@%%%%%%%^^^^^^"
    Then  User should see no search results and the empty state

  Scenario Outline: Browse category and apply brand & price range filter
    Given User navigates to the homepage
    When User searches for "<invalidSearch>"
    Then  User should see no search results and the empty state
    Examples:
      | invalidSearch       |
      | @@@@@@%%%%%%%^^^^^^ |

