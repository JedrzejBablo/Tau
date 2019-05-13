package pl.tau.library.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity(name = "Author")
@NamedQuery(name = "author.all", query = "Select a from Author a")

public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    @OneToMany(cascade = CascadeType.PERSIST,
            mappedBy = "author"
    )
    private List<Book> books;

    public Author() {
    }

    public Author(String firstName, List<Book> books) {
        this.firstName = firstName;
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(id, author.id) &&
                Objects.equals(firstName, author.firstName) &&
                Objects.equals(books, author.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, books);
    }
}