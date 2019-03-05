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
        Collections.addAll(dao.books,
                new Book(1L, "Wiedzmin", 2003),
                new Book(2L, "Harry Potter", 1997));
    }

    @Test
    public void createDaoObjectTest() {
        Assert.assertNotNull(dao);
    }

    @Test
    public void checkSaving() {
        Book book = new Book(3L, "Gwiezdne Wojny", 1995);
        Assert.assertEquals(3L,dao.save(book).longValue());
        Assert.assertEquals(dao.books.size(),3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkSavingExist(){
        Book book =new Book(1L,"Wiedzmin",2003);
        dao.save(book);
    }

    @Test
    public void gettingAllBooksTest(){
        Assert.assertArrayEquals(dao.books.toArray(),dao.getAll().toArray());
        Assert.assertEquals(dao.books.size(),dao.getAll().size());
    }

    @Test
    public void checkGettingById(){
        Book book= new Book(3L, "Gwiezdne Wojny", 1995);
        dao.save(book);
        Assert.assertEquals(book,dao.getById(3L).get());
    }
    @Test(expected = IllegalArgumentException.class)
    public void checkGettingExist(){
        dao.getById(3L);
    }

    @Test
    public void checkDeleteMethod(){
        Book book= dao.getById(1L).get();
        Assert.assertEquals(1L,dao.delete(book).longValue());
        Assert.assertEquals(1,dao.books.size());
    }

    @Test
    public void checkUpdateMethod(){
        Book book=dao.getById(1L).get();
        book.setTitle("Wiedzmin");
        book.setYear(2003);
        Assert.assertEquals(1L,dao.update(book).longValue());
        Assert.assertEquals("Wiedzmin",dao.getById(1L).get().getTitle());
        Assert.assertEquals(2003,dao.getById(1L).get().getYear());

    }
    @Test(expected = IllegalArgumentException.class)
    public void checkUpdateExistMethod(){
        Book m = new Book(3L,"Gwiezdne Wojny",1995);
        dao.update(m);

    }

}
