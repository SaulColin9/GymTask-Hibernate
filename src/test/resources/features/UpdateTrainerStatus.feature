Feature: a trainer status can be updated
  Scenario: client makes call to PUT /trainer/status
    When the client calls PUT /trainer/status
    Then the client receives a status code of 200 after trainer update