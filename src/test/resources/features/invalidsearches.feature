Feature: search headphones then buy within given budget

  Background:
    Given User opens the browser
    And   User navigates to the homepage

  Scenario: Browse noon and search for headsets
    When  User searches for "@@@@@@%%%%%%%^^^^^^"
    Then  User should see no search results and the empty state

  Scenario Outline: Browse category and apply brand & price range filter
    When User searches for "<invalidSearch>"
    Then  User should see no search results and the empty state
    Examples:
      | invalidSearch       |
      | @@@@@@%%%%%%%^^^^^^ |

