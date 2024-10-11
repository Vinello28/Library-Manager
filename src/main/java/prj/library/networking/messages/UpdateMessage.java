package prj.library.networking.messages;

import prj.library.models.Book;

public class UpdateMessage extends Message {

    public UpdateMessage() {
        this.operation = Operation.UPDATE_BOOK;
    }

    public UpdateMessage(Object message) {
        this.message = (Book) message;
        this.operation = Operation.UPDATE_BOOK;
    }
}
