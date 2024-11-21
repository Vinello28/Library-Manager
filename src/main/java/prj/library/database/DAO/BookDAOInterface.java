package prj.library.database.DAO;

import prj.library.models.Book;
import prj.library.models.Genre;
import java.util.List;

/**
 * Interface for the BookDAO class
 */
public interface BookDAOInterface {

    /**
     * creates a new book in the database
     * @param book the book to be created
     */
    void createBook(Book book);

    /**
     * deletes a book from the database
     * @param id the id of the book to be deleted
     */
    void deleteBook(int id);

    /**
     * updates a book in the database
     * @param book the book to be updated
     */
    void updateBook(Book book);

    /**
     * gets all books from the database
     * @return a list of all books
     */
    Book readBook(int id);

    /**
     * gets all books from the database
     * @return a list of all books
     */
    List<Book> getAllBooks();

    /**
     * gets books by title
     * @param title the title of the book
     * @return a list of books with the given title
     */
    List<Book> getBooksByTitle(String title);

    /**
     * gets books by author
     * @param author the author of the book
     * @return a list of books with the given author
     */
    List<Book> getBooksByAuthor(String author);

    /**
     * gets books by genre
     * @param genre the genre of the book
     * @return a list of books with the given genre
     */
    List<Book> getBooksByGenre(Genre genre);

    /**
     * gets books by year
     * @param year the year of the book
     * @return a list of books with the given year
     */
    List<Book> getBooksByYear(int year);

    /**
     * gets books by title and author
     * @param title the title of the book
     * @param author the author of the book
     * @return a list of books with the given title and author
     */
    List<Book> getBooksByTitleAuthor(String title, String author);

    /**
     * gets books by title and genre
     * @param title the title of the book
     * @param genre the genre of the book
     * @return a list of books with the given title and genre
     */
    List<Book> getBooksByTitleGenre(String title, Genre genre);

    /**
     * gets books by title and year
     * @param title the title of the book
     * @param year the year of the book
     * @return a list of books with the given title and year
     */
    List<Book> getBooksByTitleYear(String title, int year);

    /**
     * gets books by author and genre
     * @param author the author of the book
     * @param genre the genre of the book
     * @return a list of books with the given author and genre
     */
    List<Book> getBooksByAuthorGenre(String author, Genre genre);

    /**
     * gets books by author and year
     * @param author the author of the book
     * @param year the year of the book
     * @return a list of books with the given author and year
     */
    List<Book> getBooksByAuthorYear(String author, int year);

    /**
     * gets books by genre and year
     * @param genre the genre of the book
     * @param year the year of the book
     * @return a list of books with the given genre and year
     */
    List<Book> getBooksByGenreYear(Genre genre, int year);

    /**
     * gets books by title, author and genre
     * @param title the title of the book
     * @param author the author of the book
     * @param genre the genre of the book
     * @return a list of books with the given title, author and genre
     */
    List<Book> getBooksByTitleAuthorGenre(String title, String author, Genre genre);

    /**
     * gets books by title, author and year
     * @param title the title of the book
     * @param author the author of the book
     * @param year the year of the book
     * @return a list of books with the given title, author and year
     */
    List<Book> getBooksByTitleAuthorYear(String title, String author, int year);

    /**
     * gets books by title, genre and year
     * @param title the title of the book
     * @param genre the genre of the book
     * @param year the year of the book
     * @return a list of books with the given title, genre and year
     */
    List<Book> getBooksByTitleGenreYear(String title, Genre genre, int year);

    /**
     * gets books by author, genre and year
     * @param author the author of the book
     * @param genre the genre of the book
     * @param year the year of the book
     * @return a list of books with the given author, genre and year
     */
    List<Book> getBooksByAuthorGenreYear(String author, Genre genre, int year);

    /**
     * gets books by title, author, genre and year
     * @param title the title of the book
     * @param author the author of the book
     * @param genre the genre of the book
     * @param year the year of the book
     * @return a list of books with the given title, author, genre and year
     */
    List<Book> getBooksByAllParam(String title, String author, Genre genre, int year);

}
