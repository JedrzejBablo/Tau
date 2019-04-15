Feature: Book delivery
  Delivery of new books to the library

  Scenario: Delivery of books
    Given Delivery of 3 books
    When Books has been delivered
    But Book with title Wiedzmin was destroyed
    Then Quantity of books has been increased by 2