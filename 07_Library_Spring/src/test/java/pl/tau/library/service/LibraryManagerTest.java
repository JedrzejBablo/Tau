package pl.tau.library.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNoException;

import java.util.LinkedList;
import java.util.List;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import pl.tau.library.domain.Book;
import pl.tau.library.domain.Author;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/beans.xml"})
//@Rollback
@Commit
@Transactional(transactionManager = "txManager")
public class LibraryManagerTest {

    @Autowired
    BookManager libraryManager;

    private List<Long> bookIds;
    private List<Long> authorsIds;

  /*  private Book addBookHelper(String title, Integer year) {
        Long bookId;
        Book book;
        book = new Book();
        book.setTitle(title);
        book.setYear(year);
        book.setId(null);
        bookIds.add(bookId = libraryManager.addBookjkk(book));
        assertNotNull(bookId);
        return book;
    }*/


    @Before
    public void setup() {
        bookIds = new LinkedList<>();
        bookIds.add(libraryManager.addBookjkk(new Book("Opowiadanie", 2003)));
        bookIds.add(libraryManager.addBookjkk(new Book("Wiedzmin", 2005)));
        Book book = libraryManager.findBookById(bookIds.get(0));
        Book book1 = libraryManager.findBookById(bookIds.get(1));
        authorsIds = new LinkedList<>();
        authorsIds.add(libraryManager.addAuthor(new Author("Andrzej", new LinkedList<Book>(Arrays.asList(book)))));
        authorsIds.add(libraryManager.addAuthor(new Author("Jan", new LinkedList<Book>(Arrays.asList(book1)))));
    }

    @Test
    public void addBookTest() {
        assertTrue(bookIds.size() > 0);
    }

    @Test
    public void getAllBooksTest() {
        List<Long> foundIds = new LinkedList<>();
        for (Book book : libraryManager.findAllBook()) {
            if (bookIds.contains(book.getId())) foundIds.add(book.getId());
        }
        assertEquals(bookIds.size(), foundIds.size());
    }

    @Test
    public void getBookByIdTest() {
        Book book = libraryManager.findBookById(bookIds.get(0));
        assertEquals("Opowiadanie", book.getTitle());
    }

    @Test
    public void deleteBookTest() {
        /*int prevSize = libraryManager.findAllBook().size();
        Book book = libraryManager.findBookById(bookIds.get(0));
        assertNotNull(book);
        libraryManager.deleteBook(book);
        assertNull(libraryManager.findBookById(bookIds.get(0)));
        assertEquals(prevSize - 1, libraryManager.findAllBook().size());*/

        Book book = libraryManager.findBookById(bookIds.get(0));
        libraryManager.deleteBook(book);
        assertNull(libraryManager.findBookById(book.getId()));

    }
    @Test
    public void findBookByNameTest() {
        List<Book> books = libraryManager.findBookByName("Lis");
        assertEquals("List", books.get(0).getTitle());
    }
    @Test()
    public void updateBookTest() {
        Book b = libraryManager.findBookById(3L);
        b.setTitle("Fraszka");
        libraryManager.updateBook(b);
        Assert.assertEquals("Fraszka",libraryManager.findBookById(3L).getTitle());
    }

    @Test
    public void findBooksByTitleTest() {
        List<Book> books = libraryManager.findBookByName("Opowiadanie");
        assertEquals(2, books.size());
    }

    @Test
    public void findBooksByDAuthor() {
        Author author = libraryManager.findAuthorById(authorsIds.get(1));
        List<Book> books = libraryManager.getAllBooksForAuthor(author);
        assertEquals(1, books.size());
    }

    @Test
    public void transferBookToAnotherAuthor() {
        Book book = libraryManager.findBookById(bookIds.get(0));
        Book book1 = libraryManager.findBookById(bookIds.get(1));
        Author author = libraryManager.findAuthorById(authorsIds.get(0));
        Author author1 = libraryManager.findAuthorById(authorsIds.get(1));
        libraryManager.transferBookToAnotherAuthor(
                book, book1, author, author1);
        assertEquals("Wiedzmin",libraryManager.getAllBooksForAuthor(author).get(0).getTitle());
        assertEquals(1,libraryManager.getAllBooksForAuthor(author1).size());

    }

}
