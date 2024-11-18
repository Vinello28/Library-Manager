package prj.library.networking.messages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prj.library.models.Book;
import prj.library.models.Customer;
import prj.library.models.Genre;
import prj.library.models.Lends;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static prj.library.models.Genre.*;

class MessageFactoryTest {

    @BeforeEach
    void setUp() {
    }

    // Test for createMessage method
    @Test
    void createMessageBook () {
        Book book = new Book(1, "Title", "Author", 2021, ACTION, 1);
        Message message = MessageFactory.createMessage(Operation.ADD_BOOK, book);
        assertEquals(Operation.ADD_BOOK, message.getOperation());
        assertEquals(book, message.getMessage());
        assertEquals(BookMessage.class, message.getClass());
    }

    @Test
    void createMessageLend () {
        Lends lend = new Lends(1, 1, LocalDate.now());
        Message message = MessageFactory.createMessage(Operation.ADD_LEND, lend);
        assertEquals(Operation.ADD_LEND, message.getOperation());
        assertEquals(lend, message.getMessage());
        assertEquals(LendMessage.class, message.getClass());
    }

    @Test
    void createMessageBooksList () {
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book(1, "Title", "Author", 2021, ACTION, 1));
        Message message = MessageFactory.createMessage(Operation.RESULT_BOOKS, books);
        assertEquals(Operation.RESULT_BOOKS, message.getOperation());
        assertEquals(books, message.getMessage());
        assertEquals(BooksListMessage.class, message.getClass());
    }

    @Test
    void createMessageLendsList () {
        ArrayList<Lends> lends = new ArrayList<>();
        lends.add(new Lends(1, 1, LocalDate.now()));
        Message message = MessageFactory.createMessage(Operation.RESULT_LENDS, lends);
        assertEquals(Operation.RESULT_LENDS, message.getOperation());
        assertEquals(lends, message.getMessage());
        assertEquals(LendsListMessage.class, message.getClass());
    }

    @Test
    void createMessageBookSearch () {
        Book book = new Book("Title", "Author", 2021, ACTION);
        Message message = MessageFactory.createMessage(Operation.SEARCH_BY_TITLE_GENRE, book);
        assertEquals(Operation.SEARCH_BY_TITLE_GENRE, message.getOperation());
        assertEquals(book, message.getMessage());
        assertEquals(BookMessage.class, message.getClass());
    }

    @Test
    void createMessageLendSearch () {
        Lends lend = new Lends(1, 1, LocalDate.now());
        Message message = MessageFactory.createMessage(Operation.SEARCH_LEND_BY_BOOK, lend);
        assertEquals(Operation.SEARCH_LEND_BY_BOOK, message.getOperation());
        assertEquals(lend, message.getMessage());
        assertEquals(LendMessage.class, message.getClass());
    }

    @Test
    void customerMessage (){
        Customer customer = new Customer(1, "Name", "Surname", "email", "phone");
        Message message = MessageFactory.createMessage(Operation.ADD_CUSTOMER, customer);
        assertEquals(Operation.ADD_CUSTOMER, message.getOperation());
        assertEquals(customer, message.getMessage());
        assertEquals(CustomerMessage.class, message.getClass());
    }

    @Test
    void customerListMessage (){
        ArrayList<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1, "Name", "Surname", "email", "phone"));
        Message message = MessageFactory.createMessage(Operation.RESULT_CUSTOMERS, customers);
        assertEquals(Operation.RESULT_CUSTOMERS, message.getOperation());
        assertEquals(customers, message.getMessage());
        assertEquals(CustomersListMessage.class, message.getClass());
    }

    @Test
    void customerSearchMessage (){
        Customer customer = new Customer("Name", "Surname", "email", "phone");
        Message message = MessageFactory.createMessage(Operation.SEARCH_CUSTOMER_BY_NAME, customer);
        assertEquals(Operation.SEARCH_CUSTOMER_BY_NAME, message.getOperation());
        assertEquals(customer, message.getMessage());
        assertEquals(CustomerMessage.class, message.getClass());
    }
}