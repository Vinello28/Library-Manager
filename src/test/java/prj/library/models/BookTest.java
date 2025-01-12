package prj.library.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit tests for the Book model.
 * 
 * This class contains unit tests for the Book class, verifying the correctness
 * of its getter and setter methods.
 * The tests include:
 * 
 * - Initialization of a Book object before each test.
 * - Testing getter methods: getId, getTitle, getAuthor, getYear, getGenre, getCopies.
 * - Testing setter methods: setId, setTitle, setAuthor, setYear, setGenre, setCopies, setAvailable.
 * 
 * Each test method uses assertions to verify that the expected values match the actual values
 * returned by the Book object's methods.
 */
class BookTest {

    Book book;

    /**
     * Sets up the test environment before each test method is executed.
     * Initializes a Book object with predefined values.
     */
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