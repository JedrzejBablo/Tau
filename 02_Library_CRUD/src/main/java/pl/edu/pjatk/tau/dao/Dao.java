package pl.edu.pjatk.tau.dao;

import pl.edu.pjatk.tau.domain.Book;

import java.util.List;

public interface Dao<T> {
    void save(Book o);
    List<T> getAll();
}