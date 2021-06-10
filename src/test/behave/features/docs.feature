Feature: Endpoint documentation test

  Scenario: Check if openapi file is present on server
    When I request "openapi/openapi.json" on "root"
    Then I expect "200" as a status code
    And I expect response body is "NOT_EMPTY"
