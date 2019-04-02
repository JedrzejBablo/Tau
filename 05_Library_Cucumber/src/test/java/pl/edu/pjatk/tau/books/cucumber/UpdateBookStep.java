package pl.edu.pjatk.tau.books.cucumber;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import pl.edu.pjatk.tau.dao.BookInMemoryDao;
import pl.edu.pjatk.tau.domain.Book;

import java.util.ArrayList;
import java.util.Collections;

public class UpdateBookStep {
    private BookInMemoryDao bookInMemoryDao;
    public Book book = new Book(6L, "Wiedzmin", "2005");

    @Given("^Book \"([^\"]*)\"$")
    public void book_b(String title) {
        bookInMemoryDao = new BookInMemoryDao();
        bookInMemoryDao.books = new ArrayList<>();
        Collections.addAll(bookInMemoryDao.books,
                new Book(1L, "Wiedzmin", "2005"),
                new Book(2L, "Gwiezdne wojny", "2017"),
                new Book(3L, "LOTR", "2016"),
                new Book(2L, "Hashtag", "2017"),
                new Book(3L, "Znacznik", "2016"));

        bookInMemoryDao.books.add(book);

    }

    @When("^Book title should be updated to \"([^\"]*)\"$")
    public void book_title_should_be_updated(String title) {
        bookInMemoryDao.books.get(5).setTitle(title);

    }

    @Then("^Book have a new title$")
    public void book_new_title() {
        Assert.assertEquals("Ostatnie zyczenie", bookInMemoryDao.books.get(5).getTitle());
    }

}