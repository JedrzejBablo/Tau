Scenario: Book after replacment change their title

Given Book "Wiedzmin"
When Book title should be updated to "Ostatnie zyczenie"
Then Book have a new title
