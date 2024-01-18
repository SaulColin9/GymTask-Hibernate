Feature: a user can be logged in
  Scenario: client makes call to POST /login
    When the client calls /login
    Then the client receives a status code of 200 after login
    And the access token is returned