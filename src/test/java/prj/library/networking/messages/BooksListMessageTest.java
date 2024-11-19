package prj.library.networking.messages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prj.library.models.Book;
import prj.library.models.Genre;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BooksListMessageTest {

    BooksListMessage booksListMessage;

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