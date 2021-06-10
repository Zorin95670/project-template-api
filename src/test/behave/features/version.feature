Feature: Endpoint version test


Scenario: Verify that get version is working
  When I request "version"
  Then I expect "200" as a status code
   And I expect "version" is "NOT_NULL"