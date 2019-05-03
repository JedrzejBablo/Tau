package pl.tau.library.service;

import java.util.List;

import pl.tau.library.domain.Book;

public interface BookManager {
	
	Long addBookjkk(Book book);
	List<Book> findAllBook();
	Book findBookById(Long id);
	void deleteBook(Book book);
	void updateBook(Book book);
	List<Book> findBookByName(String bookName);

}
