@Data
Feature: DataTable Concept

  Scenario: Login using asLists
    Given user is on Login page
    When user enter username and password using asLists
      | Admin | admin123 |
    Then user click on Login button


  Scenario: Login using cell
    Given user is on Login page
    When user enter username and password using cell
      | Admin | admin123 |
    Then user click on Login button


  Scenario: Login using asMaps
    Given user is on Login page
    When user enter username and password using asMaps
      | username | password |
      | Admin    | admin123 |
    Then user click on Login button
