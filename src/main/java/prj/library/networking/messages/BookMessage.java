package prj.library.networking.messages;

import prj.library.models.Book;

public class BookMessage extends Message {


    public BookMessage(Operation operation, Book message) {
        this.operation = operation;
        this.message = message;
    }

    public Book getBook() {
        return (Book) message;
    }
}
