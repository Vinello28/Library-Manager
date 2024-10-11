package prj.library.networking.messages;

import prj.library.models.Book;

public class BookMessage extends Message {

    public BookMessage() {
        this.operation = Operation.RESP_BOOK;
    }

    public BookMessage(Book book) {
        this.operation = Operation.RESP_BOOK;
        this.message = book;
    }
}
