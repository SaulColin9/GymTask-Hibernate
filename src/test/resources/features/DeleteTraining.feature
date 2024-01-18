Feature: a training can be deleted
  Scenario: client makes call to DELETE /training
    When the client calls DELETE /training
    Then the client receives a status code of 200 after training deletion