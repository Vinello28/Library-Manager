package prj.library.networking.messages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prj.library.models.Book;
import prj.library.models.Genre;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the BookMessage class.
 * 
 * This class contains tests for the following methods:
 * 
 * - setUp(): Sets up the test environment before each test method is executed.
 * - getOperation(): Tests the getOperation() method of the BookMessage class.
 * - getMessage(): Tests the getMessage() method of the BookMessage class.
 * - setOperation(): Tests the setOperation() method of the BookMessage class.
 * - setMessage(): Tests the setMessage() method of the BookMessage class.
 * - getBook(): Tests the getBook() method of the BookMessage class.
 * 
 * The setUp() method initializes a Book object and a BookMessage object with the operation to add a book.
 * Each test method verifies the correctness of the corresponding method in the BookMessage class.
 */
class BookMessageTest {

    BookMessage bookMessage;

    /**
     * Sets up the test environment before each test method is executed.
     * Initializes a Book object and a BookMessage object with the operation to add a book.
     */
    @BeforeEach
    void setUp() {
        Book b = new Book(1, "Title", "Author", 2021, Genre.DRAMA, 1);
        bookMessage = new BookMessage(Operation.ADD_BOOK, b);
    }

    @Test
    void getOperation() {
        assertEquals(Operation.ADD_BOOK, bookMessage.getOperation());
    }

    @Test
    void getMessage() {
        Book b = new Book(1, "Title", "Author", 2021, null, 1);
        Book k = (Book) bookMessage.getMessage();
        assertEquals(b.getId(), k.getId());
    }

    @Test
    void setOperation() {
        bookMessage.setOperation(Operation.REMOVE_BOOK);
        assertEquals(Operation.REMOVE_BOOK, bookMessage.getOperation());
    }

    @Test
    void setMessage() {
        Book b = new Book(1, "Title", "Author", 2024, Genre.ACTION, 1);
        bookMessage.setMessage(b);
        assertEquals(b, bookMessage.getMessage());
    }

    @Test
    void getBook() {
        Book b = new Book(1, "Title", "Author", 2021, Genre.DRAMA, 1);
        assertEquals(b.getTitle(), bookMessage.getBook().getTitle());
    }
}