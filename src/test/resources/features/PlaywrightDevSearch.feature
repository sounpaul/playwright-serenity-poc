#Author: Sounak Paul

Feature: Search functionality in Playwright Dev documentation page

  @PlaywrightDocSearch @PlaywrightPoc
  Scenario Outline: Search for topic in Playwright Documentation Portal
    Given the user is on Playwright documentation portal home page
    When the user select "<preferredLanguage>" from the preferred programming language dropdown
    When the user clicks on the searchbar
    And enters "<topicName>" as topic name
    Then the search operation is marked as "<searchOperationResult>"
    Then the user should be able to see documentation related for "<topicName>"

    Examples:
      | TC | preferredLanguage | topicName           | searchOperationResult |
      | 1  | Java              | Locators            | Success               |
      |    | Python            | kjshgfdghjklsajhsgd | Failure               |