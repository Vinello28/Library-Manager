package prj.library.networking.messages;

import prj.library.models.Book;

public class SearchByMessage extends Message {

    public SearchByMessage() {
        this.operation = Operation.SEARCH_BY_ALL;
    }

    public SearchByMessage(Operation op, Book message) {
        this.operation = op;
        this.message = message;
    }
}
