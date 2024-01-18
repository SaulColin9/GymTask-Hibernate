Feature: a training can be added
  Scenario: client makes call to POST /training
    When the client calls /training
    Then the client receives a status code of 200 after training creation
    And the training is returned