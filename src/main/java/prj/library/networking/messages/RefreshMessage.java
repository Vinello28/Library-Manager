package prj.library.networking.messages;

import prj.library.models.Book;
import java.util.ArrayList;

public class RefreshMessage extends Message {

    public RefreshMessage() {
        this.operation = Operation.GET_BOOKS;
    }

    public RefreshMessage(ArrayList<Book> message) {
        this.message = message;
        this.operation = Operation.GET_BOOKS;
    }
}
