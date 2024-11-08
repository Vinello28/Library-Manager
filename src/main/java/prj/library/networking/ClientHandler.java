package prj.library.networking;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import prj.library.models.Book;
import prj.library.models.Customer;
import prj.library.models.Lends;
import prj.library.DAO.BookDAO;
import prj.library.DAO.CustomerDAO;
import prj.library.DAO.LendsDAO;
import prj.library.networking.messages.*;

/**
 * Class that handles the client requests.
 */
public class ClientHandler extends NetworkInterface implements Runnable {

    //private Socket clientSocket;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/biblioteca"; //mysql url;
    private static final String DB_USER = "root"; //
    private static final String DB_PASSWORD = "Aridaje68"; //password
    private BookDAO bookDAO;
    private LendsDAO lendDAO;
    private CustomerDAO customerDAO;


    public ClientHandler(Socket socket) {
        super(socket);
        bookDAO = new BookDAO(DB_URL, DB_USER, DB_PASSWORD);
        lendDAO = new LendsDAO(DB_URL, DB_USER, DB_PASSWORD);
        customerDAO = new CustomerDAO(DB_URL, DB_USER, DB_PASSWORD);
    }

    @Override
    public void run() {
        while (true) {
            Message message = (Message) receive();

            System.out.println("SERVER | INFO: received message " + message.getOperation() + " " + message.getMessage());

            switch (message.getOperation()) {
                case ADD_BOOK:
                case GET_BOOK:
                case UPDATE_BOOK:
                case REMOVE_BOOK:
                case GET_BOOKS:
                    handleBookOperation((BookMessage) message);
                    break;
                case SEARCH_BY_ALL:
                case SEARCH_BY_TITLE:
                case SEARCH_BY_AUTHOR:
                case SEARCH_BY_GENRE:
                case SEARCH_BY_YEAR:
                case SEARCH_BY_TITLE_AUTHOR:
                case SEARCH_BY_TITLE_GENRE:
                case SEARCH_BY_TITLE_YEAR:
                case SEARCH_BY_AUTHOR_GENRE:
                case SEARCH_BY_AUTHOR_YEAR:
                case SEARCH_BY_GENRE_YEAR:
                case SEARCH_BY_TITLE_AUTHOR_GENRE:
                case SEARCH_BY_TITLE_AUTHOR_YEAR:
                case SEARCH_BY_TITLE_GENRE_YEAR:
                case SEARCH_BY_AUTHOR_GENRE_YEAR:
                    handleSearchBookOperation((BookMessage) message);
                    break;
                case ADD_LEND:
                case GET_LEND:
                case UPDATE_LEND:
                case REMOVE_LEND:
                case GET_LENDS:
                    handleLendOperation((LendMessage) message);
                    break;
                case SEARCH_LEND_BY_ALL:
                case SEARCH_LEND_BY_BOOK:
                case SEARCH_LEND_BY_RETURN_DATE:
                    handleSearchLendOperation((LendMessage) message);
                    break;
                case ADD_CUSTOMER:
                case GET_CUSTOMER:
                case UPDATE_CUSTOMER:
                case REMOVE_CUSTOMER:
                case GET_CUSTOMERS:
                    handleCustomerOperation((CustomerMessage) message);
                    break;
                case SEARCH_CUSTOMER_BY_ALL:
                case SEARCH_CUSTOMER_BY_NAME:
                case SEARCH_CUSTOMER_BY_ADDRESS:
                case SEARCH_CUSTOMER_BY_PHONE:
                case SEARCH_CUSTOMER_BY_EMAIL:
                case SEARCH_CUSTOMER_BY_NAME_ADDRESS:
                case SEARCH_CUSTOMER_BY_NAME_PHONE:
                case SEARCH_CUSTOMER_BY_NAME_EMAIL:
                case SEARCH_CUSTOMER_BY_EMAIL_ADDRESS:
                case SEARCH_CUSTOMER_BY_NAME_PHONE_EMAIL:
                    handleSearchCustomersMessage((CustomerMessage) message);
                    break;
                default:
                    System.out.println("SERVER | ERROR: Invalid operation");
                    break;
            }
        }
    }

     /**
     * Handles the search book operations
     * @param message the message containing the search operation
     */
    private void handleSearchBookOperation(BookMessage message) {
        Book received = message.getBook();
        List <Book> results = null;

        switch (message.getOperation()) {
            case SEARCH_BY_TITLE:
                results = bookDAO.getBooksByTitle(received.getTitle());
                break;
            case SEARCH_BY_AUTHOR:
                results = bookDAO.getBooksByAuthor(received.getAuthor());
                break;
            case SEARCH_BY_GENRE:
                results = bookDAO.getBooksByGenre(received.getGenre());
                break;
            case SEARCH_BY_YEAR:
                results = bookDAO.getBooksByYear(received.getYear());
                break;
            case SEARCH_BY_ALL:
                results = bookDAO.getBooksByAllParam(received.getTitle(), received.getAuthor(), received.getGenre(), received.getYear());
                break;
            case SEARCH_BY_TITLE_AUTHOR:
                results = bookDAO.getBooksByTitleAuthor(received.getTitle(), received.getAuthor());
                break;
            case SEARCH_BY_TITLE_GENRE:
                results = bookDAO.getBooksByTitleGenre(received.getTitle(), received.getGenre());
                break;
            case SEARCH_BY_TITLE_YEAR:
                results = bookDAO.getBooksByTitleYear(received.getTitle(), received.getYear());
                break;
            case SEARCH_BY_AUTHOR_GENRE:
                results = bookDAO.getBooksByAuthorGenre(received.getAuthor(), received.getGenre());
                break;
            case SEARCH_BY_AUTHOR_YEAR:
                results = bookDAO.getBooksByAuthorYear(received.getAuthor(), received.getYear());
                break;
            case SEARCH_BY_GENRE_YEAR:
                results = bookDAO.getBooksByGenreYear(received.getGenre(), received.getYear());
                break;
            case SEARCH_BY_TITLE_AUTHOR_GENRE:
                results = bookDAO.getBooksByTitleAuthorGenre(received.getTitle(), received.getAuthor(), received.getGenre());
                break;
            case SEARCH_BY_TITLE_AUTHOR_YEAR:
                results = bookDAO.getBooksByTitleAuthorYear(received.getTitle(), received.getAuthor(), received.getYear());
                break;
            case SEARCH_BY_TITLE_GENRE_YEAR:
                results = bookDAO.getBooksByTitleGenreYear(received.getTitle(), received.getGenre(), received.getYear());
                break;
            case SEARCH_BY_AUTHOR_GENRE_YEAR:
                results = bookDAO.getBooksByAuthorGenreYear(received.getAuthor(), received.getGenre(), received.getYear());
                break;
            default:
                System.out.println("SERVER | ERROR: Invalid operation");
                break;
        }
        send(MessageFactory.createMessage(Operation.RESULT_BOOKS, new ArrayList<Book>(results)));

        System.out.println("SERVER | DEBUG INFO: Results size " + results.size()); //TODO: remove before production

    }

    /**
     * Handles the search lend operations
     * @param message the message containing the search operation
     */
    private void handleSearchLendOperation(LendMessage message) {
        Lends received = message.getLend();
        List <Lends> results = new ArrayList<>();
        boolean ok = true;

        switch (message.getOperation()) {
            case SEARCH_LEND_BY_ALL:
                results = lendDAO.getLends();
                break;
            case SEARCH_LEND_BY_BOOK:
                results = lendDAO.getLendsByBookId(received.getBookId());
                break;
            case SEARCH_LEND_BY_RETURN_DATE:
                results = lendDAO.getLendsByReturnDate(received.getReturnDate());
                break;
            default:
                ok = false;
                System.out.println("SERVER | ERROR: Invalid operation");
                break;
        }
        if(ok)send(MessageFactory.createMessage(Operation.GET_LENDS, new ArrayList<Lends>(results)));
    }

    /**
     * Handles the book CRUD operations
     * @param bookMessage the message containing the book operation
     */
    private void handleBookOperation(BookMessage bookMessage) {
        switch (bookMessage.getOperation()) {
            case ADD_BOOK:
                bookDAO.createBook(bookMessage.getBook());
                send(MessageFactory.createMessage(Operation.GENERIC_RESPONSE, true));
            break;
            case GET_BOOK:
                Book book = bookDAO.readBook(bookMessage.getBook().getId());
                send(MessageFactory.createMessage(Operation.RESP_BOOK, book));
            break;
            case UPDATE_BOOK:
                bookDAO.updateBook(bookMessage.getBook());
                send(MessageFactory.createMessage(Operation.GENERIC_RESPONSE, true));
            break;
            case REMOVE_BOOK:
                bookDAO.deleteBook(bookMessage.getBook().getId());
                send(MessageFactory.createMessage(Operation.GENERIC_RESPONSE, true));
            break;
            case GET_BOOKS:
                List<Book> books = bookDAO.getAllBooks();
                send(MessageFactory.createMessage(Operation.RESULT_BOOKS, new ArrayList<>(books)));
            break;
            default:
                System.out.println("SERVER | ERROR: Invalid operation");
            break;
        }
    }

    /**
     * Handles the lend CRUD operations
     * @param lendMessage the message containing the lend operation
     */
    private void handleLendOperation(LendMessage lendMessage) {
        switch (lendMessage.getOperation()) {
            case ADD_LEND:
                lendDAO.createLend(lendMessage.getLend());
                send(MessageFactory.createMessage(Operation.GENERIC_RESPONSE, true));
            break;
            case GET_LEND:
                Lends lend = lendDAO.readLend(lendMessage.getLend().getId());
                send(MessageFactory.createMessage(Operation.RESP_LEND, lend));
            break;
            case UPDATE_LEND:
                lendDAO.updateLend(lendMessage.getLend());
                send(MessageFactory.createMessage(Operation.GENERIC_RESPONSE, true));
            break;
            case REMOVE_LEND:
                lendDAO.deleteLend(lendMessage.getLend());
                send(MessageFactory.createMessage(Operation.GENERIC_RESPONSE, true));
            break;
            case GET_LENDS:
                List<Lends> lends = lendDAO.getLends();
                send(MessageFactory.createMessage(Operation.RESULT_LENDS, new ArrayList<>(lends)));
            break;
            default:
                System.out.println("SERVER | ERROR: Invalid operation");
            break;
        }
    }

    /**
     * Handles the customer CRUD operations
     * @param customerMessage the message containing the customer operation
     */
    private void handleCustomerOperation(CustomerMessage customerMessage) {
        switch (customerMessage.getOperation()) {
            case ADD_CUSTOMER:
                customerDAO.createCustomer(customerMessage.getCustomer());
                System.out.println("SERVER | INFO: Customer added");
                send(MessageFactory.createMessage(Operation.GENERIC_RESPONSE, true));
            break;
            case GET_CUSTOMER:
                Customer customer = customerDAO.readCustomer(customerMessage.getCustomer().getId());
                send(MessageFactory.createMessage(Operation.RESP_CUSTOMER, customer));
            break;
            case UPDATE_CUSTOMER:
                customerDAO.updateCustomer(customerMessage.getCustomer());
                send(MessageFactory.createMessage(Operation.GENERIC_RESPONSE, true));
            break;
            case REMOVE_CUSTOMER:
                customerDAO.deleteCustomer(customerMessage.getCustomer());
                send(MessageFactory.createMessage(Operation.GENERIC_RESPONSE, true));
            break;
            case GET_CUSTOMERS:
                List<Customer> customers = customerDAO.readAllCustomers();
                System.out.println("SERVER | INFO: Sending customers " + customers.size());
                send(MessageFactory.createMessage(Operation.RESULT_CUSTOMERS, new ArrayList<>(customers)));
            break;
            default:
                System.out.println("SERVER | ERROR: Invalid operation");
            break;
        }
    }

    /**
     * Handles the search customer operations
     * @param customerMessage the message containing the search operation
     */
    private void handleSearchCustomersMessage(CustomerMessage customerMessage){
        Customer received = customerMessage.getCustomer();
        List <Customer> results = null;
        boolean ok = true;

        switch (customerMessage.getOperation()) {
            case SEARCH_CUSTOMER_BY_NAME:
                results = customerDAO.searchCustomerByName(received.getName());
                break;
            case SEARCH_CUSTOMER_BY_ADDRESS:
                results = customerDAO.searchCustomerByAddress(received.getAddress());
                break;
            case SEARCH_CUSTOMER_BY_PHONE:
                results = customerDAO.searchCustomerByPhoneNumber(received.getPhone());
                break;
            case SEARCH_CUSTOMER_BY_EMAIL:
                results = customerDAO.searchCustomerByEmail(received.getEmail());
                break;
            case SEARCH_CUSTOMER_BY_NAME_ADDRESS:
                results = customerDAO.searchCustomerByNameAndAddress(received.getName(), received.getAddress());
                break;
            case SEARCH_CUSTOMER_BY_NAME_PHONE:
                results = customerDAO.searchCustomerByNameAndPhoneNumber(received.getName(), received.getPhone());
                break;
            case SEARCH_CUSTOMER_BY_NAME_EMAIL:
                results = customerDAO.searchCustomerByNameAndEmail(received.getName(), received.getEmail());
                break;
            case SEARCH_CUSTOMER_BY_EMAIL_ADDRESS:
                results = customerDAO.searchCustomerByEmailAndAddress(received.getEmail(), received.getAddress());
                break;
            case SEARCH_CUSTOMER_BY_NAME_PHONE_EMAIL:
                results = customerDAO.searchCustomerByNameAndPhoneNumberAndEmail(received.getName(), received.getPhone(), received.getEmail());
                break;
            case SEARCH_CUSTOMER_BY_ALL:
                results = customerDAO.searchCustomerByAll(received.getName(), received.getPhone(), received.getEmail(), received.getAddress());
                break;
            default:
                ok = false;
                System.out.println("SERVER | ERROR: Invalid operation");
                break;
        }
        if(ok)send(MessageFactory.createMessage(Operation.RESULT_CUSTOMERS, new ArrayList<Customer>(results)));
    }
}


