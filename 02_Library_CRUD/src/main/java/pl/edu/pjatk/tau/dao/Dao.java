package pl.edu.pjatk.tau.dao;

import pl.edu.pjatk.tau.domain.Book;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    Long save(Book o) throws IllegalArgumentException;
    List<T> getAll();
    Optional<T> get(Long id) throws IllegalArgumentException;
    Long delete(T o);
    Long update(T o) throws IndexOutOfBoundsException;
}