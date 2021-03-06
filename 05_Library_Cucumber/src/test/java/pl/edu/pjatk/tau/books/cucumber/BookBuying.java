package pl.edu.pjatk.tau.books.cucumber;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import pl.edu.pjatk.tau.dao.BookInMemoryDao;
import pl.edu.pjatk.tau.domain.Book;

import java.util.ArrayList;
import java.util.Collections;

public class BookBuying {

    private BookInMemoryDao bookInMemoryDao;
    private String choosedTitle;
    private String choosedYear;

    @Given("^Customer chooses a book$")
    public void customer_chooses_a_book() {
        bookInMemoryDao = new BookInMemoryDao();
        bookInMemoryDao.books = new ArrayList<>();
        Collections.addAll(bookInMemoryDao.books,
                new Book(1L, "Wiedzmin", "2005"),
                new Book(2L, "Gwiezdne wojny", "2017"),
                new Book(3L, "LOTR", "2016"));
    }

    @When("^Customer chose title \"([^\"]*)\"$")
    public void customer_choose_title(String title) {
        choosedTitle = title;
    }

    @And("^Customer chose year \"([^\"]*)\"$")
    public void customer_chose_year(String year) {
        choosedYear = year;
    }

    @Then("^Book has been sold$")
    public void book_has_been_sold() {
        Book choosedBook = bookInMemoryDao.getAll().stream().filter(book -> book.getTitle().equals(choosedTitle) && book.getYear().equals(choosedYear)).findFirst().get();
        Assert.assertEquals(choosedBook, bookInMemoryDao.getById(choosedBook.getId()).get());
        bookInMemoryDao.delete(choosedBook);
        Assert.assertEquals(2, bookInMemoryDao.books.size());
    }

}