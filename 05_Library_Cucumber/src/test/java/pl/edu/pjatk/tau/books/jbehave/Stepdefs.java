package pl.edu.pjatk.tau.books.jbehave;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import pl.edu.pjatk.tau.dao.BookInMemoryDao;
import pl.edu.pjatk.tau.domain.Book;

class CheckBookModel {
    static String isItBook(String title) {
        if (title.equals("Wiedzmin")) {
            return "Yes";
        }
        return "no";
    }

}

public class Stepdefs {
    private String title;
    private String actualAnswer;


    @Given("Book $title")
    public void this_is(String title) {
        this.title = title;
    }

    @When("Customer ask about this book$")
    public void Customer_ask_about_this_book() {
        this.actualAnswer = CheckBookModel.isItBook(title);
    }

    @Then("I should be told $expectedAnswer")
    public void i_should_be_told(String expectedAnswer) {
        Assert.assertEquals(expectedAnswer, actualAnswer);
    }
}
