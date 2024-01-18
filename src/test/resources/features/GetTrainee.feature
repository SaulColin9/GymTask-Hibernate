Feature: a trainee can be retrieved
  Scenario: client makes call to GET /trainee
    When the client calls GET /trainee
    Then the client receives a status code of 200 after calling /trainee
    And the trainee information is returned