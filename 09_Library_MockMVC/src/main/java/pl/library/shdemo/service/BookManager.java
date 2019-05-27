package pl.library.shdemo.service;

import java.util.List;

import pl.library.shdemo.domain.Book;
import pl.library.shdemo.domain.Author;

public interface BookManager {

    Long addBookjkk(Book book);

    List<Book> findAllBook();

    Book findBookById(Long id);

    void deleteBook(Book book);

    void updateBook(Book book);

    List<Book> findBookByName(String bookName);

    Long addAuthor(Author author);

    List<Book> getAllBooksForAuthor(Author author);

    Author findAuthorById(Long id);

    void transferBookToAnotherAuthor(Book book1, Book book2, Author author1, Author author2);

}