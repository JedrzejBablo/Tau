package pl.edu.pjatk.tau.dao;

import pl.edu.pjatk.tau.domain.Book;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class BookInMemoryDao implements Dao<Book> {
    public List<Book> books = new ArrayList<>();

    @Override
    public Long save(Book o) throws IllegalArgumentException {
        if (books.stream().anyMatch(x -> x.getId().equals(o.getId())))
            throw new IllegalArgumentException("book exist");
        books.add(o);
        return o.getId();
    }

    @Override
    public List<Book> getAll() {
        return books;
    }

    @Override
    public Optional<Book> getById(Long id) throws IllegalArgumentException  {
        if (!books.stream().anyMatch(x -> x.getId().equals(id)))
            throw new IllegalArgumentException("book not exist");
        return books.stream().filter(x -> x.getId().equals(id)).findFirst();
    }

    @Override
    public Long delete(Book o) {
        Long id = o.getId();
        books.remove(o);
        return id;
    }

    @Override
    public Long update(Book o) throws IllegalArgumentException{
        if(!books.contains(o))
            throw new IllegalArgumentException("Book not exist");
        Book b =books.get(o.getId().intValue()-1);
        b=o;
        return o.getId();
    }
}
