package prj.library.networking.messages;

import prj.library.models.Book;
import java.util.ArrayList;
import java.util.List;

public class RefreshBooksMessage extends Message {


    public RefreshBooksMessage() {
    }

    public RefreshBooksMessage(ArrayList<Book> message) {
        this.operation = Operation.GET_BOOKS;
        this.message = message;
    }

    public List<Book> getBooks() {
        return (List<Book>) message;
    }
}
