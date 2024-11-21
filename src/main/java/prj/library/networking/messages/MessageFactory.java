package prj.library.networking.messages;

import prj.library.models.*;

import java.util.ArrayList;

public class MessageFactory {
    public static Message createMessage(Operation operation, Object messageContent) {
        switch (operation) {
            case ADD_BOOK:
            case GET_BOOK:
            case GET_BOOKS:
            case UPDATE_BOOK:
            case REMOVE_BOOK:
            case RESP_BOOK:
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
                return new BookMessage(operation, (Book) messageContent);
            case ADD_LEND:
            case GET_LEND:
            case UPDATE_LEND:
            case REMOVE_LEND:
            case RESP_LEND:
            case GET_LENDS:
            case SEARCH_LEND_BY_ALL:
            case SEARCH_LEND_BY_BOOK:
            case SEARCH_LEND_BY_RETURN_DATE:
            case SEARCH_LEND_BY_CUSTOMER:
            case SEARCH_LEND_BY_LATE:
            case SEARCH_LEND_BY_BOOK_RETURNED:
            case SEARCH_LEND_BY_RETURN_DATE_RETURNED:
            case SEARCH_LEND_BY_CUSTOMER_RETURNED:
            case SEARCH_LEND_BY_BOOK_CUSTOMER:
            case SEARCH_LEND_BY_BOOK_RETURN_DATE:
            case SEARCH_LEND_BY_CUSTOMER_RETURN_DATE:
            case SEARCH_LEND_BY_BOOK_CUSTOMER_RETURNED:
            case SEARCH_LEND_BY_BOOK_RETURN_DATE_RETURNED:
            case SEARCH_LEND_BY_CUSTOMER_RETURN_DATE_RETURNED:
            case SEARCH_LEND_BY_ALL_RETURNED:
            case GET_LENDS_RETURNED:
                return new LendMessage(operation, (Lends) messageContent);
            case RESULT_BOOKS:
                return new BooksListMessage(operation, (ArrayList<Book>) messageContent);
            case RESULT_LENDS:
            case REFRESH_LENDS:
                return new LendsListMessage(operation, (ArrayList<Lends>) messageContent);
            case ADD_CUSTOMER:
            case GET_CUSTOMER:
            case UPDATE_CUSTOMER:
            case REMOVE_CUSTOMER:
            case GET_CUSTOMERS:
            case RESP_CUSTOMER:
            case SEARCH_CUSTOMER_BY_ALL:
            case SEARCH_CUSTOMER_BY_NAME:
            case SEARCH_CUSTOMER_BY_PHONE:
            case SEARCH_CUSTOMER_BY_EMAIL:
            case SEARCH_CUSTOMER_BY_ADDRESS:
            case SEARCH_CUSTOMER_BY_NAME_PHONE:
            case SEARCH_CUSTOMER_BY_NAME_EMAIL:
            case SEARCH_CUSTOMER_BY_NAME_ADDRESS:
            case SEARCH_CUSTOMER_BY_PHONE_EMAIL:
            case SEARCH_CUSTOMER_BY_PHONE_ADDRESS:
            case SEARCH_CUSTOMER_BY_EMAIL_ADDRESS:
            case SEARCH_CUSTOMER_BY_NAME_PHONE_EMAIL:
            case SEARCH_CUSTOMER_BY_NAME_PHONE_ADDRESS:
            case SEARCH_CUSTOMER_BY_NAME_EMAIL_ADDRESS:
            case SEARCH_CUSTOMER_BY_PHONE_EMAIL_ADDRESS:
                return new CustomerMessage(operation, (Customer) messageContent);
            case RESULT_CUSTOMERS:
                return new CustomersListMessage(operation, (ArrayList<Customer>) messageContent);
            case GENERIC_RESPONSE:
                return new GenericMessage((Boolean) messageContent);
            default:
                System.out.println("Invalid operation: " + operation);
                return null;
        }
    }
}