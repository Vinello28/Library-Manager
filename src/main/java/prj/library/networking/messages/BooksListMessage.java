package prj.library.networking.messages;

import prj.library.models.Book;
import java.util.ArrayList;
import java.util.List;

/**
 * Message containing a list of books.
 */
public class BooksListMessage extends Message {

    public BooksListMessage(Operation operation, ArrayList<Book> message) {
        super(operation, message);
    }

    /**
     * @return the books
     */
    public List<Book> getBooks() {
        return (List<Book>) message;
    }
}
