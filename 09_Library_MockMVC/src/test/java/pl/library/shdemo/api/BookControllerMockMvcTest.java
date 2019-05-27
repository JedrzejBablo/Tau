package pl.library.shdemo.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.library.shdemo.domain.Author;
import pl.library.shdemo.domain.Book;
import pl.library.shdemo.service.BookManager;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/*
This example uses MockMvc to mock server and http requests. This way we don't need to run real server.
We can also use Mockito to mock access to database.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerMockMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookManager service;

    @Test
    public void contextLoads() throws Exception {
        assertNotNull(mockMvc);
    }

    @Test
    public void greetingShouldReturnHelloMessage() throws Exception {
        this.mockMvc.perform(get("/"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello")));
    }


    @Test
    public void getAllShouldReturnEmptyResults() throws Exception {
        when(service.findAllBook()).thenReturn(new LinkedList<Book>());
        this.mockMvc.perform(get("/books"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void getAllShouldReturnSomeResults() throws Exception {
        List<Book> expectedResult = new LinkedList<Book>();
        Book np = new Book();
        np.setId(13L);
        np.setTitle("Opowiadanie");
        np.setYear(2005);
        expectedResult.add(np);
        when(service.findAllBook()).thenReturn(expectedResult);
        this.mockMvc.perform(get("/books"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":13,\"title\":\"Opowiadanie\",\"year\":2005}]"));
    }
    @Test
    public void postNewBookShouldReallyAddItToDatabase() throws Exception {
        Book p = new Book();
        p.setTitle("Opowiadanie");
        p.setYear(2005);
        when(service.addBookjkk(p)).thenReturn(0L);
        this.mockMvc.perform(post("/books")
                .content("{\"title\":\"Opowiadanie\"," +
                        "\"year\":\"2005\"}")
                .contentType("application/json"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":0,\"title\":\"Opowiadanie\",\"year\":2005,\"author\":null}"));
        p.setId(0L);
        verify(service).addBookjkk(p);
    }

    @Test
    public void deleteShouldRemoveMovieFromDatabase() throws Exception {
        Book p = new Book();
        p.setId(1L);
        p.setTitle("List");
        p.setYear(2010);
        when(service.findBookById(1L)).thenReturn(p);
        this.mockMvc.perform(delete("/book/" + p.getId()
        )
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("OK"));

        verify(service).deleteBook(p);

    }

    @Test
    public void getShouldReturnMovieFromDatabase() throws Exception {
        Book p = new Book();
        p.setId(1L);
        p.setTitle("List");
        p.setYear(2010);
        when(service.findBookById(1L)).thenReturn(p);
        this.mockMvc.perform(get("/book/" + p.getId())
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"title\":\"List\",\"year\":2010,\"author\":null}"));
        verify(service).findBookById(1L);

    }

    @Test
    public void putShouldReturnUpdatedMovieFromDatabase() throws Exception {
        Book p = new Book();
        p.setId(1L);
        p.setTitle("List");
        p.setYear(2010);
        this.mockMvc.perform(put("/book")
                .content("{\"id\":1,\"title\":\"Fraszka\",\"year\":2010,\"author\":null}")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("ok"));
        p.setTitle("Fraszka");
        verify(service).updateBook(p);

    }
}