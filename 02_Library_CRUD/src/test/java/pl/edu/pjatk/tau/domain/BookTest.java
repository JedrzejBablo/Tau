package pl.edu.pjatk.tau.domain;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class BookTest {

     @Test
    public void checkBook() {
        Book book = new Book(1L, "Wiedzmin", 2003);
        Assert.assertNotNull(book);
    }

    @Test
    public void bookTest() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Wiedzmin");
        book.setYear(2003);
        Assert.assertEquals(new Long(1), book.getId());
        }
}
