#Author: Sounak Paul

Feature: Search functionality in Capgemini corporate directory

  @TalentSearch @PlaywrightPoc
  Scenario: Search for an user in Capgemini corporate directory
    Given the user is inside the talent home page
    When the user navigates to global links page
    When the user navigates to corporate directory page
    And the user enters the name of the employee in the text box
    Then fetch the details of the given employee