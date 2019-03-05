package pl.edu.pjatk.tau.dao;

import pl.edu.pjatk.tau.domain.Book;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

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

    @Override
    public Optional<Book> get(int id) throws IllegalArgumentException  {
        if(books.get(id) == null ){
            throw new IllegalArgumentException("error");
        }
        return Optional.ofNullable(books.get(id));
    }
}
