package pl.edu.pjatk.library.dao;

import pl.edu.pjatk.library.domain.Book;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class BookDaoJdbcImpl implements BookDao {

    public PreparedStatement preparedStatementGetAll;
    public PreparedStatement preparedStatementInsert;
    public PreparedStatement preparedStatementGetBook;
    public PreparedStatement preparedStatementDelete;
    public PreparedStatement preparedStatementUpdate;
    Connection connection;


    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) throws SQLException {
        this.connection = connection;
        preparedStatementGetAll = connection.prepareStatement(
                "SELECT id, title, year FROM Book ORDER BY id");
        preparedStatementInsert = connection.prepareStatement(
                "INSERT INTO Book (title, year) VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        preparedStatementGetBook = connection.prepareStatement("SELECT id, title, year FROM Book WHERE id = ?");
        preparedStatementDelete = connection.prepareStatement("DELETE FROM Book where id = ?");
        preparedStatementUpdate = connection.prepareStatement("UPDATE Book SET title=?,year=? WHERE id = ?");

    }

    @Override
    public List<Book> getAllBooks() {
        try {
            List<Book> ret = new LinkedList<>();
            ResultSet result = preparedStatementGetAll.executeQuery();
            while (result.next()) {
                Book book = new Book();
                book.setId(result.getLong("id"));
                book.setTitle(result.getString("title"));
                book.setYear(result.getInt("year"));
                ret.add(book);
            }
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Book getBook(long id) throws SQLException {
        try {
            preparedStatementGetBook.setLong(1, id);
            ResultSet rs = preparedStatementGetBook.executeQuery();

            if (rs.next()) {
                Book b = new Book();
                b.setId(rs.getLong("id"));
                b.setTitle(rs.getString("title"));
                b.setYear(rs.getInt("year"));
                return b;
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        throw new SQLException("Book with id " + id + " does not exist");
    }

    @Override
    public int addBook(Book book) throws SQLException {
        preparedStatementInsert.setString(1, book.getTitle());
        preparedStatementInsert.setInt(2, book.getYear());
        int r = preparedStatementInsert.executeUpdate();
        if (r <= 0) {
            throw new SQLException();
        }
        return r;
    }

    @Override
    public int updateBook(Book book) throws SQLException {
        int count = 0;
        try {
            preparedStatementUpdate.setString(1, book.getTitle());
            preparedStatementUpdate.setInt(2, book.getYear());
            if (book.getId() != null) {
                preparedStatementUpdate.setLong(3, book.getId());
            } else {
                preparedStatementUpdate.setLong(3, -1);
            }
            count = preparedStatementUpdate.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        if (count <= 0)
            throw new SQLException("Book not found for update");
        return count;
    }

    @Override
    public int deleteBook(Book book) throws SQLException {
        preparedStatementDelete.setLong(1, book.getId());
        int r = preparedStatementDelete.executeUpdate();
        if (r <= 0) {
            throw new SQLException();
        }
        return r;

    }
}
