package pl.edu.pjatk.tau.dao;

import pl.edu.pjatk.tau.domain.Book;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    Long save(T o);
    List<T> getAll();
    Optional<T> getById(Long id);
    Long delete(T o);
    Long update(T o);
}