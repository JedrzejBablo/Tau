package pl.tau.library.service;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.tau.library.domain.Book;
import pl.tau.library.domain.Author;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class BookManagerHibernateImpl implements BookManager {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Long addBookjkk(Book book) {
        // Pierwsza wersja dodawania do bazy danych - korzystamy z persist - to jest dostepne w JPA
        if (book.getId() != null)
            throw new IllegalArgumentException("the person ID should be null if added to database");
        sessionFactory.getCurrentSession().persist(book);
        sessionFactory.getCurrentSession().flush();
        return book.getId();
    }

    @Override
    public List<Book> findAllBook() {
        return sessionFactory.getCurrentSession().getNamedQuery("book.all")
                .list();
    }

    @Override
    public Book findBookById(Long id) {
        return sessionFactory.getCurrentSession().get(Book.class, id);
    }

    @Override
    public void deleteBook(Book book) {
        book = (Book) sessionFactory.getCurrentSession().get(Book.class,
                book.getId());
        sessionFactory.getCurrentSession().delete(book);

    }

    @Override
    public void updateBook(Book book) {
        sessionFactory.getCurrentSession().update(book);

    }
    @Override
    public List<Book> findBookByName(String bookName) {
        return (List<Book>) sessionFactory.getCurrentSession()
                .getNamedQuery("book.findBookByName")
                .setString("modelNameFragment", "%"+bookName+"%")
                .list();
    }

    @Override
    public List<Book> getAllBooksForAuthor(Author author) {
        return (List<Book>) sessionFactory.getCurrentSession()
                .getNamedQuery("book.findBooksByAuthor")
                .setParameter("author", author)
                .list();
    }

    @Override
    public Long addAuthor(Author author) {
        if (author.getId() != null)
            throw new IllegalArgumentException("the director ID should be null if added to database");
        sessionFactory.getCurrentSession().persist(author);
        for (Book book : author.getBooks()) {
            book.setAuthor(author);
            sessionFactory.getCurrentSession().update(book);
        }
        sessionFactory.getCurrentSession().flush();
        return author.getId();
    }

    @Override
    public Author findAuthorById(Long id) {
        return (Author) sessionFactory.getCurrentSession().get(Author.class, id);
    }

    @Override
    public void transferBookToAnotherAuthor(Book book1, Book book2, Author author1, Author author2) {

        book1.setAuthor(author2);
        sessionFactory.getCurrentSession().save(author2);
        sessionFactory.getCurrentSession().save(book1);

        book2.setAuthor(author1);
        sessionFactory.getCurrentSession().save(author1);
        sessionFactory.getCurrentSession().save(book2);
    }

}
