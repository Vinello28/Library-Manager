package prj.library.networking.messages;

public enum Operation {
    //books CRUD
    ADD_BOOK,
    REMOVE_BOOK,
    GET_BOOKS,
    GET_BOOK,
    UPDATE_BOOK,
    RESP_BOOK,
    GENERIC_RESPONSE,
    RESULT_BOOKS,
    //search books
    SEARCH_BY_ALL,
    SEARCH_BY_TITLE,
    SEARCH_BY_AUTHOR,
    SEARCH_BY_GENRE,
    SEARCH_BY_YEAR,
    SEARCH_BY_TITLE_AUTHOR,
    SEARCH_BY_TITLE_GENRE,
    SEARCH_BY_TITLE_YEAR,
    SEARCH_BY_AUTHOR_GENRE,
    SEARCH_BY_AUTHOR_YEAR,
    SEARCH_BY_GENRE_YEAR,
    SEARCH_BY_TITLE_AUTHOR_GENRE,
    SEARCH_BY_TITLE_AUTHOR_YEAR,
    SEARCH_BY_TITLE_GENRE_YEAR,
    SEARCH_BY_AUTHOR_GENRE_YEAR,

    //lends CRUD
    ADD_LEND,
    REMOVE_LEND,
    GET_LENDS,
    GET_LEND,
    UPDATE_LEND,
    RESP_LEND,
    RESULT_LENDS,

    //search lends
    SEARCH_LEND_BY_ALL,
    SEARCH_LEND_BY_ALL_RETURNED,
    SEARCH_LEND_BY_LATE,
    SEARCH_LEND_BY_BOOK,
    SEARCH_LEND_BY_BOOK_RETURNED,
    SEARCH_LEND_BY_CUSTOMER,
    SEARCH_LEND_BY_CUSTOMER_RETURNED,
    SEARCH_LEND_BY_RETURN_DATE,
    SEARCH_LEND_BY_RETURN_DATE_RETURNED,
    SEARCH_LEND_BY_BOOK_CUSTOMER,
    SEARCH_LEND_BY_BOOK_CUSTOMER_RETURNED,
    SEARCH_LEND_BY_BOOK_RETURN_DATE,
    SEARCH_LEND_BY_BOOK_RETURN_DATE_RETURNED,
    SEARCH_LEND_BY_CUSTOMER_RETURN_DATE,
    SEARCH_LEND_BY_CUSTOMER_RETURN_DATE_RETURNED,
    ALERT_ALL,
    GET_LENDS_RETURNED,
    GET_LENDS_NR_COUNT,
    REFRESH_LENDS,

    //customers CRUD
    ADD_CUSTOMER,
    REMOVE_CUSTOMER,
    GET_CUSTOMERS,
    GET_CUSTOMER,
    UPDATE_CUSTOMER,
    RESP_CUSTOMER,
    RESULT_CUSTOMERS,
    //search customers
    SEARCH_CUSTOMER_BY_ALL,
    SEARCH_CUSTOMER_BY_NAME,
    SEARCH_CUSTOMER_BY_PHONE,
    SEARCH_CUSTOMER_BY_EMAIL,
    SEARCH_CUSTOMER_BY_ADDRESS,
    SEARCH_CUSTOMER_BY_NAME_PHONE,
    SEARCH_CUSTOMER_BY_NAME_EMAIL,
    SEARCH_CUSTOMER_BY_NAME_ADDRESS,
    SEARCH_CUSTOMER_BY_PHONE_EMAIL,
    SEARCH_CUSTOMER_BY_PHONE_ADDRESS,
    SEARCH_CUSTOMER_BY_EMAIL_ADDRESS,
    SEARCH_CUSTOMER_BY_NAME_PHONE_EMAIL,
    SEARCH_CUSTOMER_BY_NAME_PHONE_ADDRESS,
    SEARCH_CUSTOMER_BY_NAME_EMAIL_ADDRESS,
    SEARCH_CUSTOMER_BY_PHONE_EMAIL_ADDRESS
}
