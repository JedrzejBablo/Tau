package pl.edu.pjatk.tau.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.edu.pjatk.tau.domain.Book;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.hamcrest.CoreMatchers.equalTo;

public class BookDaoTest {

    public static String url = "jdbc:hsqldb:hsql://localhost/workdb";

    BookDao bookManager;
    List<Book> expectedDbState;

    @Before
    public void setup() throws SQLException {
        Connection connection = DriverManager.getConnection(url);
        try {
            connection.createStatement()
                    .executeUpdate("CREATE TABLE " +
                            "Book(id bigint GENERATED BY DEFAULT AS IDENTITY, " +
                            "title varchar(50) NOT NULL, " +
                            "year integer)");

        } catch (SQLException e) {
        }

        Random rand = new Random();
        PreparedStatement addBookStmt = connection.prepareStatement(
                "INSERT INTO Book (title, year) VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS);

        expectedDbState = new LinkedList<Book>();
        for (int i = 0; i < 10; i++) {
            Book book = new Book("book" + rand.nextInt(1000), 1000 + rand.nextInt(1000));
            try {
                addBookStmt.setString(1, book.getTitle());
                addBookStmt.setInt(2, book.getYear());
                addBookStmt.executeUpdate();
                ResultSet generatedKeys = addBookStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    book.setId(generatedKeys.getLong(1));
                }
            } catch (SQLException e) {
                throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
            }

            expectedDbState.add(book);
        }
        bookManager = new BookDaoJdbcImpl(connection);
    }

    @After
    public void cleanup() throws SQLException {
        Connection connection = DriverManager.getConnection(url);
        try {
            connection.prepareStatement("DELETE FROM Book").executeUpdate();
        } catch (Exception e) {
            System.out.println("Probably the database was not yet initialized");
        }
    }

    @Test
    public void checkAdding() throws Exception {
        Book book = new Book();
        book.setTitle("Wiedzmin");
        book.setYear(1997);
        Assert.assertEquals(1, bookManager.addBook(book));
        expectedDbState.add(book);
        Assert.assertThat(bookManager.getAllBooks(), equalTo(expectedDbState));

    }

    @Test
    public void checkGettingAll() {
        Assert.assertThat(bookManager.getAllBooks(), equalTo(expectedDbState));
    }

    @Test
    public void checkGettingById() throws Exception {
        Book book = expectedDbState.get(5);
        Assert.assertEquals(book, bookManager.getBook(book.getId()));
    }

    @Test(expected = Exception.class)
    public void checkGettingByIdException() throws Exception {
        Book book = expectedDbState.get(12);
        Assert.assertEquals(book, bookManager.getBook(book.getId()));

    }

    @Test()
    public void checkUpdatingSuccess() throws SQLException {
        Book b = expectedDbState.get(3);
        b.setTitle("Wiedzmin");
        expectedDbState.set(3, b);
        Assert.assertEquals(1, bookManager.updateBook(b));
        Assert.assertThat(bookManager.getAllBooks(), equalTo(expectedDbState));
    }

    @Test(expected = SQLException.class)
    public void checkUpdatingFailure() throws SQLException {
        Book b = new Book("Wiedzmin", 2003);
        bookManager.updateBook(b);
    }

    @Test()
    public void checkDeleting() throws SQLException {
        Book b = expectedDbState.get(1);
        expectedDbState.remove(b);
        Assert.assertEquals(1, bookManager.deleteBook(b));
        Assert.assertThat(bookManager.getAllBooks(), equalTo(expectedDbState));
    }

    @Test(expected = SQLException.class)
    public void checkDeletingFailure() throws SQLException {
        Book book = new Book("Wiedzmin", 2003);
        book.setId(3000L);
        bookManager.deleteBook(book);
    }

    

}