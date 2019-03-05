package pl.edu.pjatk.tau.dao;

import pl.edu.pjatk.tau.domain.Book;
import java.util.List;
import java.util.ArrayList;

public class BookInMemoryDao implements Dao<Book> {
    public List<Book> books = new ArrayList<>();

    @Override
    public void save(Book o) {
        books.add(o);
    }

    @Override
    public List<Book> getAll() {
        return books;
    }
}
