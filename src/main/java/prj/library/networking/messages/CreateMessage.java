package prj.library.networking.messages;

import prj.library.models.Book;

public class CreateMessage extends Message {

    public CreateMessage() {
        this.operation = Operation.ADD_BOOK;
    }

    public CreateMessage(Object message) {
        this.message = (Book) message;
        this.operation = Operation.ADD_BOOK;
    }
}
