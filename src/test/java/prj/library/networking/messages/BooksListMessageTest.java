package prj.library.networking.messages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prj.library.models.Book;
import prj.library.models.Genre;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the BooksListMessage class.
 * 
 * This test class verifies the functionality of the BooksListMessage class,
 * which is responsible for handling messages related to a list of books.
 * 
 * The following test methods are included:
 * 
 * - setUp(): Sets up the test environment before each test method is executed.
 *   Initializes an ArrayList of Book objects with sample data and 
 *   creates a BooksListMessage instance with the Operation.RESULT_BOOKS operation.
 * 
 * - getBooks(): Tests the getBooks() method to ensure it returns the correct
 *   list of books.
 */
class BooksListMessageTest {

    BooksListMessage booksListMessage;

    /**
     * Sets up the test environment before each test execution.
     * Initializes an ArrayList of Book objects with sample data and 
     * creates a BooksListMessage instance with the Operation.RESULT_BOOKS operation.
     */
    @BeforeEach
    void setUp() {
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book(1, "title", "author", 2020, Genre.ACTION, 1));
        books.add(new Book(1, "title2", "author2", 2020, Genre.ACTION, 2));
        booksListMessage = new BooksListMessage(Operation.RESULT_BOOKS, books);
    }

    @Test
    void getBooks() {
        assertEquals(2, booksListMessage.getBooks().size());
        assertEquals("title", booksListMessage.getBooks().get(0).getTitle());
        assertEquals("author2", booksListMessage.getBooks().get(1).getAuthor());
    }
}