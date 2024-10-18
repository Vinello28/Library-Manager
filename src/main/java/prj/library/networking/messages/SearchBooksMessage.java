package prj.library.networking.messages;

import prj.library.models.Book;

public class SearchBooksMessage extends Message {

    public SearchBooksMessage(Operation operation, Object message) {
        this.operation = Operation.SEARCH_BY_ALL;
    }

    public SearchBooksMessage(Operation op, Book message) {
        this.operation = op;
        this.message = message;
    }

    public Book getBook() {
        return (Book) message;
    }
}
