package pl.library.shdemo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.library.shdemo.domain.Book;
import pl.library.shdemo.service.BookManager;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@RestController
public class PersonController {

    @Autowired
    BookManager libraryManager;

    @RequestMapping("/books")
    public java.util.List<Book> getBooks() {
        List<Book> books = new LinkedList<>();
        for (Book p : libraryManager.findAllBook()) {
            books.add(p.clone());
        }
        return books;
    }

    @RequestMapping(value = "/books",method = RequestMethod.POST)
    public Book addBook(@RequestBody Book nbook) {
        nbook.setId(libraryManager.addBookjkk(nbook));
        return nbook;
    }

    @RequestMapping(value = "/book",method = RequestMethod.PUT)
    public String updateBook(@RequestBody Book nbook) {
        libraryManager.updateBook(nbook);
        return "ok";
    }




    @RequestMapping("/")
    public String index() {
        return "Hello";
    }

    @RequestMapping(value = "/book/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Book getBook(@PathVariable("id") Long id) throws SQLException {
        return libraryManager.findBookById(id).clone();
    }

    @RequestMapping(value = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Book> getBooks(@RequestParam(value = "filter", required = false) String f) throws SQLException {
        List<Book> books = new LinkedList<Book>();
        for (Book p : libraryManager.findAllBook()) {
            if (f == null) {
                books.add(p.clone());
            } else if (p.getTitle().contains(f)) {
                books.add(p);
            }
        }
        return books;
    }

    @RequestMapping(value = "/book/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteBook(@PathVariable("id") Long id) throws SQLException {
        libraryManager.deleteBook(libraryManager.findBookById(id));
        return "OK";
    }

}
