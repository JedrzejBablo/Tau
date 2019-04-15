package pl.edu.pjatk.tau.books.jbehave;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.Story;
import org.junit.Assert;
import pl.edu.pjatk.tau.dao.BookInMemoryDao;
import pl.edu.pjatk.tau.domain.Book;

import java.util.ArrayList;

public class BookDelivery extends Story {

    private BookInMemoryDao bookInMemoryDao;
    private int previousQuantityOfBooks;

    @Given("Delivery of 3 books")
    public void delivery_of_3_books() {
        bookInMemoryDao = new BookInMemoryDao();
        bookInMemoryDao.books = new ArrayList<>();
    }

    @When("Books has been delivered")
    public void books_has_been_delivered() {
        previousQuantityOfBooks = bookInMemoryDao.books.size();
        bookInMemoryDao.save(new Book(1L, "Wiedzmin", "2005"));
        bookInMemoryDao.save(new Book(2L, "Gwiezdne wojny", "2017"));
        bookInMemoryDao.save(new Book(3L, "LOTR", "2016"));
    }

    @Then("Quantity of cars has been increased by $quantityOfAddedCars")
    public void quantity_of_books_has_been_increased_by(int quantityOfAddedBooks) {
        Assert.assertEquals(previousQuantityOfBooks + quantityOfAddedBooks, bookInMemoryDao.books.size());
    }

}