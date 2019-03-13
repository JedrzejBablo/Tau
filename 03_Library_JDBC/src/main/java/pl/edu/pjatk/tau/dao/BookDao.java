package pl.edu.pjatk.tau.dao;

import pl.edu.pjatk.tau.domain.Book;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface BookDao {
    Connection getConnection();

    void setConnection(Connection connection) throws SQLException;

    int addBook(Book book);

    List<Book> getAllBooks();

    Book getBook(long id) throws SQLException;
}