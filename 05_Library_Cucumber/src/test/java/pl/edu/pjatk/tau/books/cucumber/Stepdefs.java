package pl.edu.pjatk.tau.books.cucumber;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

class CheckBookModel {
    static String isItBook(String title) {
        if (title.equals("Wiedzmin")) {
            return "Yes";
        }
        return "no";
    }

}

public class Stepdefs {
    private String modelName;
    private String actualAnswer;


    @Given("^this is \"([^\"]*)\"$")
    public void this_is(String today) {
        this.modelName = today;
    }

    @When("^Customer ask about this book$")
    public void I_ask_are_you_sure_iPhone() {
        this.actualAnswer = CheckBookModel.isItBook(modelName);
    }

    @Then("^I should be told \"([^\"]*)\"$")
    public void i_should_be_told(String expectedAnswer) {
        Assert.assertEquals(expectedAnswer, actualAnswer);
    }
}

