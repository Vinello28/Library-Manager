package prj.library.networking.messages;

import prj.library.models.Book;
import java.util.ArrayList;
import java.util.List;

public class BooksListMessage extends Message {


    public BooksListMessage() {
    }

    public BooksListMessage(Operation operation, ArrayList<Book> message) {
        this.operation = operation;
        this.message = message;
    }

    public List<Book> getBooks() {
        return (List<Book>) message;
    }
}
