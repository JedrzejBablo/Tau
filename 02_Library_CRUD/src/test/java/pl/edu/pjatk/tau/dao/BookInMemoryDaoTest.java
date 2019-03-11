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
    BookInMemoryDao bookDao;

    @Before
    public void setup() {
        bookDao = new BookInMemoryDao();
        Book b1 = new Book(1L, "Wiedzmin", 2003);
        Book b2 = new Book(2L, "Harry Potter", 1997);
        Collections.addAll(bookDao.books, b1, b2);
    }

    @Test
    public void createDaoObjectTest() {
        Assert.assertNotNull(bookDao);
    }

    @Test
    public void checkSaving() {
        Book b3 = new Book(3L, "Gwiezdne Wojny", 1995);
        Assert.assertEquals(3L,bookDao.save(b3).longValue());
        Assert.assertEquals(bookDao.books.size(),3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkSavingExist(){
        Book b1 =new Book(1L,"Wiedzmin",2003);
        bookDao.save(b1);
    }

    @Test
    public void gettingAllBooksTest(){
        Assert.assertArrayEquals(bookDao.books.toArray(),bookDao.getAll().toArray());
        Assert.assertEquals(bookDao.books.size(),bookDao.getAll().size());
    }

    @Test
    public void checkGettingById(){
        Book b= new Book(3L, "Gwiezdne Wojny", 1995);
        bookDao.save(b);
        Assert.assertEquals(b,bookDao.get(3L).get());
    }
    @Test(expected = IllegalArgumentException.class)
    public void checkGettingExist(){
        bookDao.get(5L);
    }

    @Test
    public void checkDeleteMethod(){
        Book book= bookDao.get(1L).get();
        Assert.assertEquals(1L,bookDao.delete(book).longValue());
        Assert.assertEquals(1L,bookDao.books.size());
    }

    @Test
    public void checkUpdateMethod(){
        Book book= new Book();
        book.setId(1L);
        book.setTitle("Ostatnie życzenie");
        book.setYear(2003);
        bookDao.update(book);
        Assert.assertEquals("Ostatnie życzenie", bookDao.get(1L).get().getTitle());
        Assert.assertEquals(2003, bookDao.get(1L).get().getYear());

    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void checkUpdateExistMethod(){
        Book b = new Book(3L,"Gwiezdne Wojny",1995);
        bookDao.update(b);

    }

}
