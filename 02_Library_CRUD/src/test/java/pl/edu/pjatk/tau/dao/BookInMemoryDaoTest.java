package pl.edu.pjatk.tau.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class BookInMemoryDaoTest {
    BookInMemoryDao dao;

    @Before
    public void setup() {
        dao = new BookInMemoryDao();
    }

    @Test
    public void createDaoObjectTest() {
        Assert.assertNotNull(dao);
    }
}
