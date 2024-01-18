Feature: a training can be added
  Scenario: client makes call to POST /trainer
    When the client calls /trainer
    Then the client receives a status code of 200
    And the trainer credentials are returned