package pl.edu.pjatk.tau.books.jbehave;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import pl.edu.pjatk.tau.dao.BookInMemoryDao;
import pl.edu.pjatk.tau.domain.Book;

import java.util.ArrayList;
import java.util.Collections;

public class BookBuying {

    private BookInMemoryDao bookInMemoryDao;
    private String choosedTitle;
    private String choosedYear;

    @Given("Customer chooses a book")
    public void customer_chooses_a_book() {
        bookInMemoryDao = new BookInMemoryDao();
        bookInMemoryDao.books = new ArrayList<>();
        Collections.addAll(bookInMemoryDao.books,
                new Book(1L, "Wiedzmin", "2005"),
                new Book(2L, "Gwiezdne wojny", "2017"),
                new Book(3L, "LOTR", "2016"));
    }

    @When("Customer chose title $title")
    public void customer_choose_title(String title) {
        choosedTitle = title;
    }

    @When("Customer chose year $year")
    public void customer_chose_year(String year) {
        choosedYear = year;
    }

    @Then(value = "Book has been sold", priority = 1)
    public void book_has_been_sold() {
        Book choosedBook = bookInMemoryDao.getAll().stream().filter(book -> book.getTitle().equals(choosedTitle) && book.getYear().equals(choosedYear)).findFirst().get();
        Assert.assertEquals(choosedBook, bookInMemoryDao.getById(choosedBook.getId()).get());
        bookInMemoryDao.delete(choosedBook);
        Assert.assertEquals(2, bookInMemoryDao.books.size());
    }

}