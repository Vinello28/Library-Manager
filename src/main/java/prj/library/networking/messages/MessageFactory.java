package prj.library.networking.messages;

import prj.library.models.Book;
import prj.library.models.Lends;

import java.util.ArrayList;
import java.util.List;

public class MessageFactory {
    public static Message createMessage(Operation operation, Object messageContent) {
        switch (operation) {
            case ADD_BOOK:
            case GET_BOOK:
            case UPDATE_BOOK:
            case REMOVE_BOOK:
            case RESP_BOOK:
                return new BookMessage(operation, (Book) messageContent);
            case ADD_LEND:
            case GET_LEND:
            case UPDATE_LEND:
            case REMOVE_LEND:
            case RESP_LEND:
                return new LendMessage(operation, (Lends) messageContent);
            case GET_BOOKS:
                return new RefreshBooksMessage((ArrayList<Book>) messageContent);
            case GET_LENDS:
            case REFRESH_LENDS:
                return new RefreshLendsMessage(operation, (List<Lends>) messageContent);
            case SEARCH_BY_TITLE_GENRE:
            case SEARCH_BY_ALL:
            case SEARCH_BY_AUTHOR:
            case SEARCH_BY_AUTHOR_GENRE:
            case SEARCH_BY_AUTHOR_GENRE_YEAR:
            case SEARCH_BY_AUTHOR_YEAR:
            case SEARCH_BY_GENRE:
            case SEARCH_BY_GENRE_YEAR:
            case SEARCH_BY_TITLE:
            case SEARCH_BY_TITLE_AUTHOR:
            case SEARCH_BY_TITLE_AUTHOR_YEAR:
            case SEARCH_BY_TITLE_AUTHOR_GENRE:
            case SEARCH_BY_TITLE_YEAR:
            case SEARCH_BY_YEAR:
            case SEARCH_BY_TITLE_GENRE_YEAR:
                return new SearchBooksMessage(operation, (Book) messageContent);
            case SEARCH_LEND_BY_ALL:
            case SEARCH_LEND_BY_BOOK:
            case SEARCH_LEND_BY_RETURN_DATE:
                return new SearchLendsMessage(operation, (Lends) messageContent);
            case GENERIC_RESPONSE:
                return new GenericMessage((Boolean) messageContent);
            default:
                throw new IllegalArgumentException("Invalid operation: " + operation);
        }
    }
}