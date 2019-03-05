package pl.edu.pjatk.tau.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import pl.edu.pjatk.tau.domain.Book;

import java.util.Collections;

@RunWith(JUnit4.class)
public class BookInMemoryDaoTest {
    BookInMemoryDao dao;

    @Before
    public void setup() {
        dao = new BookInMemoryDao();
        Book b1 = new Book(1L, "Wiedzmin", 2003);
        Book b2 = new Book(2L, "Harry Potter", 1997);
        Collections.addAll(dao.books, b1, b2);
    }

    @Test
    public void createDaoObjectTest() {
        Assert.assertNotNull(dao);
    }

    @Test
    public void checkSaving() {
        Book b3 = new Book(3L, "Gwiezdne Wojny", 1995);
        Assert.assertEquals(new Long(3), b3.getId());
    }

    @Test
    public void gettingAllBooksTest(){
        Assert.assertArrayEquals(dao.books.toArray(),dao.getAll().toArray());
        Assert.assertEquals(dao.books.size(),dao.getAll().size());
    }
}
