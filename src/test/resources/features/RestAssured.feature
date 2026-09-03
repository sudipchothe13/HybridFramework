Feature: CaseDetails API - Advanced Banking Validations

# -------------------------- GET --------------------------
@API @GET @Regression
Scenario: Verify user is able to fetch case details using GET API
  Given user sets request specification
  When user sends GET request
  Then user verify success message for GET call


    

