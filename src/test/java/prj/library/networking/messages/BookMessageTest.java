package prj.library.networking.messages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prj.library.models.Book;
import prj.library.models.Genre;

import static org.junit.jupiter.api.Assertions.*;

class BookMessageTest {

    BookMessage bookMessage;
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