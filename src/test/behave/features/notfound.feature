Feature: Tests 404 not found


Scenario Outline: Test 404 error on "<url>" page
  When I request "<url>" with method "GET"
  Then I expect "404" as a status code

  Examples:
    | url      |
    | notfound |
    | bad      |
