package prj.library.networking.messages;

import prj.library.models.Book;

/**
 * Message containing a book.
 */
public class BookMessage extends Message {

    public BookMessage(Operation operation, Book message) {
        super(operation, message);
    }

    public Book getBook() {
        return (Book) message;
    }
}
