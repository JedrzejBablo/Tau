package pl.tau.library.domain;

import javax.persistence.*;
import java.util.Objects;
@Entity(name = "Book")
@Table(name = "Book")
@NamedQueries({
        @NamedQuery(name = "book.all", query = "Select b from Book b"),
        @NamedQuery(name = "book.findBook", query = "Select b from Book b where b.title like :modelNameFragment"),
        @NamedQuery(name = "book.findBooksByAuthor", query = "Select c from Book c where c.author= :author")
})
public class Book {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private Integer year;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Book() {
    }

    public Book(Long id, String title, Integer year) {
        this.id = id;
        this.title = title;
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) &&
                Objects.equals(title, book.title) &&
                Objects.equals(year, book.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, year);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", year=" + year +
                '}';
    }
}
