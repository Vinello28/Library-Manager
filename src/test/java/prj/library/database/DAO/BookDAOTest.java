package prj.library.database.DAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prj.library.models.Book;
import prj.library.models.Genre;
import java.util.List;
import java.util.Objects;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the BookDAO class.
 * 
 * This class contains test methods to verify the functionality of the BookDAO class,
 * which is responsible for performing CRUD operations on Book objects in the database.
 * 
 * The following methods are tested:
 * 
 * - setUp(): Sets up the test environment before each test method is executed.
 * - createBook(): Tests the creation of a new book in the database.
 * - readBook(): Tests reading a book from the database by its ID.
 * - updateBook(): Tests updating an existing book in the database.
 * - deleteBook(): Tests deleting a book from the database.
 * - getBooksByTitle(): Tests retrieving books from the database by their title.
 * - getBooksByGenre(): Tests retrieving books from the database by their genre.
 * - getBooksByAuthor(): Tests retrieving books from the database by their author.
 * - getBooksByYear(): Tests retrieving books from the database by their publication year.
 * - getBooksByTitleAuthor(): Tests retrieving books from the database by their title and author.
 * - getBooksByTitleGenre(): Tests retrieving books from the database by their title and genre.
 * - getBooksByTitleYear(): Tests retrieving books from the database by their title and publication year.
 * - getBooksByAuthorGenre(): Tests retrieving books from the database by their author and genre.
 * - getBooksByAuthorYear(): Tests retrieving books from the database by their author and publication year.
 * - getBooksByGenreYear(): Tests retrieving books from the database by their genre and publication year.
 * - getBooksByTitleAuthorGenre(): Tests retrieving books from the database by their title, author, and genre.
 * - getBooksByTitleAuthorYear(): Tests retrieving books from the database by their title, author, and publication year.
 * - getBooksByTitleGenreYear(): Tests retrieving books from the database by their title, genre, and publication year.
 * - getBooksByAuthorGenreYear(): Tests retrieving books from the database by their author, genre, and publication year.
 * - getBooksByAllParam(): Tests retrieving books from the database by their title, author, genre, and publication year.
 */
class BookDAOTest {

    BookDAO bookDAO;
    Book book;

    /**
     * Sets up the test environment before each test method is executed.
     * Initializes a new instance of BookDAO and a Book object with predefined values.
     */
    @BeforeEach
    void setUp() {
        bookDAO = new BookDAO();
        book = new Book("Title12121212", "Author", 2021, Genre.ACTION, 1);
    }

    @Test
    void createBook() {
        bookDAO.createBook(book);
        assertEquals("Title12121212", bookDAO.getBooksByTitle("Title12121212").get(0).getTitle());

        bookDAO.deleteBook(bookDAO.getBooksByTitle("Title12121212").get(0).getId());
    }

    @Test
    void readBook() {
        bookDAO.createBook(book);
        assertEquals("Title12121212", bookDAO.readBook(bookDAO.getBooksByTitle("Title12121212").get(0).getId()).getTitle());

        bookDAO.deleteBook(bookDAO.getBooksByTitle("Title12121212").get(0).getId());
    }

    @Test
    void updateBook() {
        bookDAO.createBook(book);
        Book tmp = bookDAO.getBooksByTitle("Title12121212").get(0);
        tmp.setAuthor("A1");
        bookDAO.updateBook(tmp);
        List<Book> b = bookDAO.getBooksByTitle("Title12121212");
        if (b.isEmpty()) {
            fail();
        }
        assertEquals("A1", b.get(0).getAuthor());

        bookDAO.deleteBook(bookDAO.getBooksByTitle("Title12121212").get(0).getId());
    }

    @Test
    void deleteBook() {
        bookDAO.createBook(book);
        List<Book> books = bookDAO.getAllBooks();
        bookDAO.deleteBook(bookDAO.getBooksByTitle("Title12121212").get(0).getId());
        List<Book> books1 = bookDAO.getAllBooks();
        assertEquals(books.size() - 1, books1.size());
    }

    @Test
    void getBooksByTitle() {
        bookDAO.createBook(book);
        assertEquals("Title12121212", bookDAO.getBooksByTitle("Title12121212").get(0).getTitle());

        bookDAO.deleteBook(bookDAO.getBooksByTitle("Title12121212").get(0).getId());
    }

    @Test
    void getBooksByGenre() {
        bookDAO.createBook(book);
        List<Book> books = bookDAO.getBooksByGenre(Genre.ACTION);
        if (books.isEmpty()) {
            fail();
        }
        assertTrue(books.stream().anyMatch(b -> Objects.equals(b.getTitle(), "Title12121212")));

        bookDAO.deleteBook(bookDAO.getBooksByTitle("Title12121212").get(0).getId());
    }

    @Test
    void getBooksByAuthor() {
        bookDAO.createBook(book);
        List<Book> books = bookDAO.getBooksByAuthor("Author");
        if (books.isEmpty()) {
            fail();
        }
        assertTrue(books.stream().anyMatch(b -> Objects.equals(b.getTitle(), "Title12121212")));

        bookDAO.deleteBook(bookDAO.getBooksByTitle("Title12121212").get(0).getId());
    }

    @Test
    void getBooksByYear() {
        bookDAO.createBook(book);
        List<Book> books = bookDAO.getBooksByYear(2021);
        if (books.isEmpty()) {
            fail();
        }
        assertTrue(books.stream().anyMatch(b -> Objects.equals(b.getTitle(), "Title12121212")));

        bookDAO.deleteBook(bookDAO.getBooksByTitle("Title12121212").get(0).getId());
    }

    @Test
    void getBooksByTitleAuthor() {
        bookDAO.createBook(book);
        List<Book> books = bookDAO.getBooksByTitleAuthor("Title12121212", "Author");
        if (books.isEmpty()) {
            fail();
        }
        assertTrue(books.stream().anyMatch(b -> Objects.equals(b.getTitle(), "Title12121212")));

        bookDAO.deleteBook(bookDAO.getBooksByTitle("Title12121212").get(0).getId());
    }

    @Test
    void getBooksByTitleGenre() {
        bookDAO.createBook(book);
        List<Book> books = bookDAO.getBooksByTitleGenre("Title12121212", Genre.ACTION);
        if (books.isEmpty()) {
            fail();
        }
        assertTrue(books.stream().anyMatch(b -> Objects.equals(b.getTitle(), "Title12121212")));

        bookDAO.deleteBook(bookDAO.getBooksByTitle("Title12121212").get(0).getId());
    }

    @Test
    void getBooksByTitleYear() {
        bookDAO.createBook(book);
        List<Book> books = bookDAO.getBooksByTitleYear("Title12121212", 2021);
        if (books.isEmpty()) {
            fail();
        }
        assertTrue(books.stream().anyMatch(b -> Objects.equals(b.getTitle(), "Title12121212")));

        bookDAO.deleteBook(bookDAO.getBooksByTitle("Title12121212").get(0).getId());
    }

    @Test
    void getBooksByAuthorGenre() {
        bookDAO.createBook(book);
        List<Book> books = bookDAO.getBooksByAuthorGenre("Author", Genre.ACTION);
        if (books.isEmpty()) {
            fail();
        }
        assertTrue(books.stream().anyMatch(b -> Objects.equals(b.getTitle(), "Title12121212")));

        bookDAO.deleteBook(bookDAO.getBooksByTitle("Title12121212").get(0).getId());
    }

    @Test
    void getBooksByAuthorYear() {
        bookDAO.createBook(book);
        List<Book> books = bookDAO.getBooksByAuthorYear("Author", 2021);
        if (books.isEmpty()) {
            fail();
        }
        assertTrue(books.stream().anyMatch(b -> Objects.equals(b.getTitle(), "Title12121212")));

        bookDAO.deleteBook(bookDAO.getBooksByTitle("Title12121212").get(0).getId());
    }

    @Test
    void getBooksByGenreYear() {
        bookDAO.createBook(book);
        List<Book> books = bookDAO.getBooksByGenreYear(Genre.ACTION, 2021);
        if (books.isEmpty()) {
            fail();
        }
        assertTrue(books.stream().anyMatch(b -> Objects.equals(b.getTitle(), "Title12121212")));

        bookDAO.deleteBook(bookDAO.getBooksByTitle("Title12121212").get(0).getId());
    }

    @Test
    void getBooksByTitleAuthorGenre() {
        bookDAO.createBook(book);
        List<Book> books = bookDAO.getBooksByTitleAuthorGenre("Title12121212", "Author", Genre.ACTION);
        if (books.isEmpty()) {
            fail();
        }
        assertTrue(books.stream().anyMatch(b -> Objects.equals(b.getTitle(), "Title12121212")));

        bookDAO.deleteBook(bookDAO.getBooksByTitle("Title12121212").get(0).getId());
    }

    @Test
    void getBooksByTitleAuthorYear() {
        bookDAO.createBook(book);
        List<Book> books = bookDAO.getBooksByTitleAuthorYear("Title12121212", "Author", 2021);
        if (books.isEmpty()) {
            fail();
        }
        assertTrue(books.stream().anyMatch(b -> Objects.equals(b.getTitle(), "Title12121212")));

        bookDAO.deleteBook(bookDAO.getBooksByTitle("Title12121212").get(0).getId());
    }

    @Test
    void getBooksByTitleGenreYear() {
        bookDAO.createBook(book);
        List<Book> books = bookDAO.getBooksByTitleGenreYear("Title12121212", Genre.ACTION, 2021);
        if (books.isEmpty()) {
            fail();
        }
        assertTrue(books.stream().anyMatch(b -> Objects.equals(b.getTitle(), "Title12121212")));

        bookDAO.deleteBook(bookDAO.getBooksByTitle("Title12121212").get(0).getId());
    }

    @Test
    void getBooksByAuthorGenreYear() {
        bookDAO.createBook(book);
        List<Book> books = bookDAO.getBooksByAuthorGenreYear("Author", Genre.ACTION, 2021);
        if (books.isEmpty()) {
            fail();
        }
        assertTrue(books.stream().anyMatch(b -> Objects.equals(b.getTitle(), "Title12121212")));

        bookDAO.deleteBook(bookDAO.getBooksByTitle("Title12121212").get(0).getId());
    }

    @Test
    void getBooksByAllParam() {
        bookDAO.createBook(book);
        List<Book> books = bookDAO.getBooksByAllParam("Title12121212", "Author", Genre.ACTION, 2021);
        if (books.isEmpty()) {
            fail();
        }
        assertTrue(books.stream().anyMatch(b -> Objects.equals(b.getTitle(), "Title12121212")));

        bookDAO.deleteBook(bookDAO.getBooksByTitle("Title12121212").get(0).getId());
    }
}