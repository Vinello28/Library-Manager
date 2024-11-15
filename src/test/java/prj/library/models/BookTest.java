package prj.library.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    Book book;
    @BeforeEach
    void setUp() {
        book = new Book(1, "title", "author", 2021, Genre.ACTION, 1);
    }

    @Test
    void getId() {
        assertEquals(1, book.getId());
    }

    @Test
    void getTitle() {
        assertEquals("title", book.getTitle());
    }

    @Test
    void getAuthor() {
        assertEquals("author", book.getAuthor());
    }

    @Test
    void getYear() {
        assertEquals(2021, book.getYear());
    }

    @Test
    void setId() {
        book.setId(2);
        assertEquals(2, book.getId());
    }

    @Test
    void setTitle() {
        book.setTitle("new title");
        assertEquals("new title", book.getTitle());
    }

    @Test
    void setAuthor() {
        book.setAuthor("new author");
        assertEquals("new author", book.getAuthor());
    }

    @Test
    void setYear() {
        book.setYear(2022);
        assertEquals(2022, book.getYear());
    }

    @Test
    void getGenre() {
        assertEquals(Genre.ACTION, book.getGenre());
    }

    @Test
    void setGenre() {
        book.setGenre(Genre.ADVENTURE);
        assertEquals(Genre.ADVENTURE, book.getGenre());
    }

    @Test
    void getCopies() {
        assertEquals(1, book.getCopies());
    }

    @Test
    void setCopies() {
        book.setCopies(2);
        assertEquals(2, book.getCopies());
    }

    @Test
    void setAvailable() {
        book.setAvailable();
        assertEquals(2, book.getCopies());
    }
}