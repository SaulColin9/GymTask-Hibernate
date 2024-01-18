Feature: a trainee status can be updated
  Scenario: client makes call to PUT /trainee/status
    When the client calls PUT /trainee/status
    Then the client receives a status code of 200 after update