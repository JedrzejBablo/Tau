Feature: Change book title
  Change book title after replacement

  Scenario: Book after replacement change their title
    Given Book "Wiedzmin"
    When Book title should be updated to "Ostatnie zyczenie"
    Then Book have a new title