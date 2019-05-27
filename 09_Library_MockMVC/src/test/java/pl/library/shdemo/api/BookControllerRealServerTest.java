package pl.library.shdemo.api;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.test.context.junit4.SpringRunner;
import pl.library.shdemo.domain.Book;
import pl.library.shdemo.domain.Author;
import org.springframework.transaction.annotation.Transactional;
import pl.library.shdemo.service.BookManager;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;


/**
 * This example creates real server that will handle requests. The spring test will
 * query the server and tests checks the correctness of responses
 */

@Ignore
@RunWith(SpringRunner.class)
@ComponentScan({"pl.library"})
@PropertySource("classpath:jdbc.properties")
@ImportResource({"classpath:/beans.xml"})
@Rollback
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerRealServerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private BookController controller;
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    BookManager libraryManager; // manager is needed for direct manipulation with database

    @Test
    public void contextLoads() throws Exception {
        assertNotNull(controller);
    }

    @Test
    public void greetingShouldReturnHelloMessage() throws Exception {
        assertThat(
                this.restTemplate.getForObject("http://localhost:" + port + "/",
                        String.class)).contains("Hello");
    }

    @Test
    public void getAllShouldReturnSomeResultsFromDatabase() throws Exception {
        assertThat(
                this.restTemplate.getForObject("http://localhost:" + port + "/books",
                        List.class)).isNotNull();
    }

    @Test
    public void getAllShouldReturnResultsThatWerePreviouslyPutIntoDatabase() throws Exception {
        Book newBook = new Book();
        newBook.setTitle("Restowy Rester");
        Long newId = libraryManager.addBookjkk(newBook);
        List<java.util.LinkedHashMap> personsFromRest =
                this.restTemplate.getForObject("http://localhost:" + port + "/books", List.class);
        boolean found = false;
        for (LinkedHashMap p: personsFromRest) {
            if (p.get("id").toString().equals(newId.toString())) found = true;
        }
        assertTrue(found);
    }
}
