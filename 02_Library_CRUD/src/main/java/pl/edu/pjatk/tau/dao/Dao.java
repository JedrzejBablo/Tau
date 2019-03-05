package pl.edu.pjatk.tau.dao;

import pl.edu.pjatk.tau.domain.Book;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    void save(Book o);
    List<T> getAll();
    Optional<T> get(int id);
}