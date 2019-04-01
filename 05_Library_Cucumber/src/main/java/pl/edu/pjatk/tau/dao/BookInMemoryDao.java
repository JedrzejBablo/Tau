package pl.edu.pjatk.tau.dao;

import pl.edu.pjatk.tau.domain.Book;

import java.util.List;
import java.util.Optional;

public class BookInMemoryDao implements BookDao {
    public List<Book> books;

    @Override
    public List<Book> getAll() {
        return books;
    }

    @Override
    public Optional<Book> getById(Long id) throws IllegalArgumentException {
        if (books.stream().noneMatch(book -> book.getId().equals(id)))
            throw new IllegalArgumentException("Book with id " + id + " not exists");
        return books.stream().filter(book -> book.getId().equals(id)).findFirst();
    }

    @Override
    public void save(Book o) throws IllegalArgumentException {
        if (books.stream().anyMatch(book -> book.getId().equals(o.getId())))
            throw new IllegalArgumentException("Book has already exists");
        books.add(o);
    }

    @Override
    public void update(Book o) throws IllegalArgumentException {
        if (books.stream().noneMatch(book -> book.getId().equals(o.getId())))
            throw new IllegalArgumentException("Book not exists");
        books.add(o.getId().intValue() - 1, o);
    }

    @Override
    public void delete(Book o) throws IllegalArgumentException {
        if (books.stream().noneMatch(book -> book.getId().equals(o.getId())))
            throw new IllegalArgumentException("Book not exists");
        int id = o.getId().intValue();
        books.remove(id);
    }
}
