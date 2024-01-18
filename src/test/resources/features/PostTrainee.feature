Feature: a trainee can be added
  Scenario: client makes call to POST /trainee
    When the client calls /trainee
    Then the client receives a status code of 200 after creation
    And the trainee credentials are returned