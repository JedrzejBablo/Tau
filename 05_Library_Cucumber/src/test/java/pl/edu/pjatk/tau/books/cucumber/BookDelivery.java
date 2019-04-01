package pl.edu.pjatk.tau.books.cucumber;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import pl.edu.pjatk.tau.dao.BookInMemoryDao;
import pl.edu.pjatk.tau.domain.Book;

import java.util.ArrayList;

public class BookDelivery {

    private BookInMemoryDao bookInMemoryDao;
    private int previousQuantityOfBooks;

    @Given("^Delivery of 3 books$")
    public void delivery_of_3_books() {
        bookInMemoryDao = new BookInMemoryDao();
        bookInMemoryDao.books = new ArrayList<>();
    }

    @When("^Books has been delivered$")
    public void books_has_been_delivered() {
        previousQuantityOfBooks = bookInMemoryDao.books.size();
        bookInMemoryDao.save(new Book(1L, "Wiedzmin", "2005"));
        bookInMemoryDao.save(new Book(2L, "Gwiezdne wojny", "2017"));
        bookInMemoryDao.save(new Book(3L, "LOTR", "2016"));
    }

    @Then("^Quantity of books has been increased by (\\d+)$")
    public void quantity_of_books_has_been_increased_by(int quantityOfAddedBooks) {
        Assert.assertEquals(previousQuantityOfBooks + quantityOfAddedBooks, bookInMemoryDao.books.size());
    }

}