Feature: Book delivery
  Delivery of new books to the library

  Scenario: Delivery of books
    Given Delivery of 3 books
    When Books has been delivered
    Then Quantity of books has been increased by 3