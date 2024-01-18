Feature: a trainer can be retrieved
  Scenario: client makes call to GET /trainer
    When the client calls GET /trainer
    Then the client receives a status code of 200 after calling /trainer
    And the trainer information is returned