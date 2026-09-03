@AAPI
Feature: Companies and Student API Validation

  Scenario: Create companies using POJO serialization and response deserialization
    Given User initializes company request using POJO
    When User sends POST request to companies endpoint
    Then Company response should be validated

  Scenario: Create company using external JSON payload
    Given User reads "updateCompany" payload from external JSON
    When User sends POST request to companies endpoint with query parameter
    Then External JSON response should be validated

  Scenario: Serialize and deserialize student payload
    Given User creates student payload using JSONObject
    When User sends POST request to student endpoint
    Then Student response should be validated

  Scenario: Read payload directly using JsonNode
    Given User reads payload using JsonNode from external JSON
    When User sends POST request to student endpoint using external payload
    Then Request and response JSON should match