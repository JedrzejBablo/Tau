package pl.edu.pjatk.library.dao;

import pl.edu.pjatk.library.domain.Book;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface BookDao {
    public Connection getConnection();

    public void setConnection(Connection connection) throws SQLException;

    public List<Book> getAllBooks();

    public int addBook(Book book) throws SQLException;

    public Book getBook(long id) throws SQLException;

    public int deleteBook(Book book) throws SQLException;
}