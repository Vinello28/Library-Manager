package prj.library.database.DAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prj.library.models.Book;
import prj.library.models.Genre;
import java.util.List;
import java.util.Objects;
import static org.junit.jupiter.api.Assertions.*;

class BookDAOTest {

    BookDAO bookDAO;
    Book book;

    @BeforeEach
    void setUp() {
        bookDAO = new BookDAO();
        book = new Book("Title12121212", "Author", 2021, Genre.ACTION, 1);
    }

    @Test
    void createBook() {
        bookDAO.createBook(book);
        assertEquals(bookDAO.getBooksByTitle("Title12121212").get(0).getTitle(), "Title12121212");

        bookDAO.deleteBook(bookDAO.getBooksByTitle("Title12121212").get(0).getId());
    }

    @Test
    void readBook() {
        bookDAO.createBook(book);
        assertEquals(bookDAO.readBook(bookDAO.getBooksByTitle("Title12121212").get(0).getId()).getTitle(), "Title12121212");

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