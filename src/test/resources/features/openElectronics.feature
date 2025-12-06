Feature: Navigate to Electronics category then filter and validate

  Background:
    Given User opens the browser
    And User navigates to the homepage

  @Regression @bBrowseCategory
  Scenario: Browse Electronics and apply Samsung filter
    When User selects category "Electronics"
    And User applies brand filter "Samsung" in category "Electronics"
    Then User should see brand "Samsung" applied
    And User applies price filter from "1000" to "3000"
    And User sorts results by Best Rated
    Then User should see items matching category "Electronics", brand "Samsung", price from "1000" to "3000"

  @Regression @bBrowseMultiCategory
    Scenario Outline: Browse category and apply brand & price range filter
      When User selects category "<category>"
      And User applies brand filter "<brand>" in category "<category>"
      Then User should see brand "<brand>" applied
      And User applies price filter from "<from>" to "<to>"
      And User sorts results by Best Rated
      Then User should see items matching category "<category>", brand "<brand>", price from "<from>" to "<to>"

      Examples:
        | category    | brand   | from | to   |
        | Electronics | Samsung | 1000 | 3000 |
        | Electronics | Apple   | 500  | 2000 |
