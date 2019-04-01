Feature: Is it "Wiedzmin" ?
 Customer want to know is this the book

  Scenario Outline: It is "wiedzmin" or not
    Given this is "<title>"
    When Customer ask about this book
    Then I should be told "<answer>"

    Examples:
    | title | answer |
    | Wiedzmin | Yes |
    | Gwiezdne wojny | no |
    | LOTR | no |