package pl.edu.pjatk.tau.dao;

import pl.edu.pjatk.tau.domain.Book;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class BookDaoJdbcImpl implements BookDao {

    private Connection connection;
    private PreparedStatement addBookStmt;
    private PreparedStatement getAllBooksStmt;
    private PreparedStatement getBookStmt;


    public BookDaoJdbcImpl() throws SQLException {
    }

    public BookDaoJdbcImpl(Connection connection) throws SQLException {
        this.connection = connection;
        // if (!isDatabaseReady()) {
        //    createTables();
        //}
        if (!isDatabaseReady()) {
            createTables();
        }
        setConnection(connection);
    }

    public void createTables() throws SQLException {
        connection.createStatement()
                .executeUpdate("CREATE TABLE " +
                        "Book" +
                        "(id bigint GENERATED BY DEFAULT AS IDENTITY, " +
                        "title varchar(20) NOT NULL," +
                        "year integer)");
    }

    private boolean isDatabaseReady() {
        try {
            ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
            boolean tableExists = false;
            while (rs.next()) {
                if ("Book".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
                    tableExists = true;
                    break;
                }
            }
            return tableExists;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public int addBook(Book book) {
        int count = 0;
        try {
            addBookStmt.setString(1, book.getTitle());
            addBookStmt.setInt(2, book.getYear());
            count = addBookStmt.executeUpdate();
            ResultSet generatedKeys = addBookStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        return count;
    }

    public List<Book> getAllBooks() {
        List<Book> bookss = new LinkedList<>();
        try {
            ResultSet rs = getAllBooksStmt.executeQuery();

            while (rs.next()) {
                Book b = new Book();
                b.setId(rs.getLong("id"));
                b.setTitle(rs.getString("title"));
                b.setYear(rs.getInt("year"));
                bookss.add(b);
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        return bookss;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) throws SQLException {
        this.connection = connection;
        addBookStmt = connection.prepareStatement(
                "INSERT INTO Book(title, year) VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        getAllBooksStmt = connection.prepareStatement("SELECT id, title, year FROM Book ORDER BY id");
        getBookStmt = connection.prepareStatement("SELECT id, title, year FROM Book WHERE id = ?");
    }

    @Override
    public Book getBook(long id) throws SQLException {
        try {
            getBookStmt.setLong(1, id);
            ResultSet rs = getBookStmt.executeQuery();

            if (rs.next()) {
                Book d = new Book();
                d.setId(rs.getLong("id"));
                d.setTitle(rs.getString("title"));
                d.setYear(rs.getInt("year"));
                return d;
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        throw new SQLException("Book with id " + id + " does not exist");
    }
}