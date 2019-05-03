package pl.tau.library.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNoException;

import java.util.LinkedList;
import java.util.List;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/beans.xml"})
//@Rollback
@Commit
@Transactional(transactionManager = "txManager")
public class LibraryManagerTest {

    @Autowired
    BookManager libraryManager;

    List<Long> bookIds;

    private Book addBookHelper(String title, Integer year) {
        Long bookId;
        Book book;
        book = new Book();
        book.setTitle(title);
        book.setYear(year);
        book.setId(null);
        bookIds.add(bookId = libraryManager.addBookjkk(book));
        assertNotNull(bookId);
        return book;
    }


    @Before
    public void setup() {
        bookIds = new LinkedList<>();
        addBookHelper("Wiedzmin", 2005);
        addBookHelper("Opowiadanie", 2010);
        Book book = addBookHelper("List", 2015);
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
    public void deletePhoneTest() {
        int prevSize = libraryManager.findAllBook().size();
        Book book = libraryManager.findBookById(bookIds.get(0));
        assertNotNull(book);
        libraryManager.deleteBook(book);
        assertNull(libraryManager.findBookById(bookIds.get(0)));
        assertEquals(prevSize - 1, libraryManager.findAllBook().size());
    }
    @Test
    public void findBookByNameTest() {
        List<Book> books = libraryManager.findBookByName("Lis");
        assertEquals("List", books.get(0).getTitle());
    }
    @Test()
    public void updatePhoneTest() {
        Book b = libraryManager.findBookById(3L);
        b.setTitle("Fraszka");
        libraryManager.updateBook(b);
        Assert.assertEquals("Fraszka",libraryManager.findBookById(3L).getTitle());
    }
}
