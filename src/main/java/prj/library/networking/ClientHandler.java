package prj.library.networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import prj.library.models.Book;
import prj.library.models.Lends;
import prj.library.networking.DAO.BookDAO;
import prj.library.networking.DAO.LendsDAO;
import prj.library.networking.messages.*;


public class ClientHandler implements Runnable {

    private Socket clientSocket;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/biblioteca"; //mysql url;
    private static final String DB_USER = "root"; //
    private static final String DB_PASSWORD = "Aridaje68"; //password
    private BookDAO bookDAO;
    private LendsDAO lendDAO;


    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        bookDAO = new BookDAO(DB_URL, DB_USER, DB_PASSWORD);
        lendDAO = new LendsDAO(DB_URL, DB_USER, DB_PASSWORD);
    }

    @Override
    public void run() {
        try (
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())
        ) {
            while (true) {
                Message message = (Message) in.readObject();

                System.out.println("SERVER | DEBUG INFO: received message " + message.getOperation()+" "+message.getMessage());

                if(message instanceof BookMessage) {
                    handleBookOperation((BookMessage) message, out);
                    continue;
                } else if (message instanceof SearchBooksMessage) {
                    System.out.println("SERVER | DEBUG INFO: into book's search handling");
                    handleSearchBookOperation((SearchBooksMessage) message, out);
                    continue;
                }
                else if(message instanceof LendMessage) {
                    System.out.println("SERVER | DEBUG INFO: into lend handling");
                    handleLendOperation((LendMessage) message, out);
                    continue;
                }
                else if(message instanceof SearchLendsMessage) {
                    System.out.println("SERVER | DEBUG INFO: into lend's search handling");
                    handleSearchLendOperation((SearchLendsMessage) message, out);
                    continue;
                }
                else if(message instanceof RefreshBooksMessage) {
                    System.out.println("SERVER | DEBUG INFO: into refresh books");
                    handleRefreshBooksOperation((RefreshBooksMessage) message, out);
                    continue;
                }
                else if(message instanceof RefreshLendsMessage) {
                    System.out.println("SERVER | DEBUG INFO: into refresh lends");
                    handleRefreshLendsOperation((RefreshLendsMessage) message, out);
                    continue;
                }
                else {
                    System.out.println("What have you... done?");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Client handler exception: " + e.getMessage());
        }
    }

     /**
     * Handles the search book operations
     * @param message the message containing the search operation
     * @param out the output stream
     */
    private void handleSearchBookOperation(SearchBooksMessage message, ObjectOutputStream out) throws IOException {
        Book received = message.getBook();
        List <Book> results = null;
        boolean ok = true;

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
                ok = false;
                System.out.println("Invalid operation");
                break;
        }
        if(ok)out.writeObject(MessageFactory.createMessage(Operation.GET_BOOKS, new ArrayList<Book>(results)));
    }

    /**
     * Handles the search lend operations
     * @param message the message containing the search operation
     * @param out the output stream
     */
    private void handleSearchLendOperation(SearchLendsMessage message, ObjectOutputStream out) throws IOException {
        Lends received = message.getLend();
        List <Lends> results = new ArrayList<>();
        boolean ok = true;

        switch (message.getOperation()) {
            case SEARCH_LEND_BY_ALL:
                results = lendDAO.readAllLends();
                break;
            case SEARCH_LEND_BY_BOOK:
                results = lendDAO.readLendsByBookId(received.getBookId());
                break;
            case SEARCH_LEND_BY_RETURN_DATE:
                results = lendDAO.readLendsByReturnDate((java.sql.Date) received.getReturnDate());
                break;
            default:
                ok = false;
                System.out.println("Invalid operation");
                break;
        }
        if(ok)out.writeObject(MessageFactory.createMessage(Operation.GET_LENDS, new ArrayList<Lends>(results)));
    }

    /**
     * Handles the book CRUD operations
     * @param bookMessage the message containing the book operation
     * @param out the output stream
     * @throws IOException
     */
    private void handleBookOperation(BookMessage bookMessage, ObjectOutputStream out) throws IOException {
        switch (bookMessage.getOperation()) {
            case ADD_BOOK:
                bookDAO.createBook(bookMessage.getBook());
                out.writeObject(MessageFactory.createMessage(Operation.GENERIC_RESPONSE, true));
            break;
            case GET_BOOK:
                Book book = bookDAO.readBook(bookMessage.getBook().getId());
                out.writeObject(MessageFactory.createMessage(Operation.RESP_BOOK, book));
            break;
            case UPDATE_BOOK:
                bookDAO.updateBook(bookMessage.getBook());
                out.writeObject(MessageFactory.createMessage(Operation.GENERIC_RESPONSE, true));
            break;
            case REMOVE_BOOK:
                bookDAO.deleteBook(bookMessage.getBook().getId());
                out.writeObject(MessageFactory.createMessage(Operation.GENERIC_RESPONSE, true));
            break;
            case GET_BOOKS:
                List<Book> books = bookDAO.getAllBooks();
                out.writeObject(MessageFactory.createMessage(Operation.GET_BOOKS, new ArrayList<>(books)));
            break;
            default:
                System.out.println("Invalid operation");
            break;
        }
    }

    /**
     * Handles the lend CRUD operations
     * @param lendMessage the message containing the lend operation
     * @param out the output stream
     * @throws IOException
     */
    private void handleLendOperation(LendMessage lendMessage, ObjectOutputStream out) throws IOException {
        switch (lendMessage.getOperation()) {
            case ADD_LEND:
                lendDAO.createLend(lendMessage.getLend());
                out.writeObject(MessageFactory.createMessage(Operation.GENERIC_RESPONSE, true));
            break;
            case GET_LEND:
                Lends lend = lendDAO.readLend(lendMessage.getLend().getId());
                out.writeObject(MessageFactory.createMessage(Operation.RESP_LEND, lend));
            break;
            case UPDATE_LEND:
                lendDAO.updateLend(lendMessage.getLend());
                out.writeObject(MessageFactory.createMessage(Operation.GENERIC_RESPONSE, true));
            break;
            case REMOVE_LEND:
                lendDAO.deleteLend(lendMessage.getLend().getId());
                out.writeObject(MessageFactory.createMessage(Operation.GENERIC_RESPONSE, true));
            break;
            case GET_LENDS:
                List<Lends> lends = lendDAO.readAllLends();
                out.writeObject(MessageFactory.createMessage(Operation.GET_LENDS, new ArrayList<>(lends)));
            break;
            default:
                System.out.println("Invalid operation");
            break;
        }
    }

    /**
     * Handles the refresh books operation
     * @param message the message containing the refresh operation
     * @param out the output stream
     * @throws IOException
     */
    private void handleRefreshBooksOperation(RefreshBooksMessage message, ObjectOutputStream out) throws IOException {
        List<Book> books = bookDAO.getAllBooks();
        out.writeObject(MessageFactory.createMessage(Operation.GET_BOOKS, new ArrayList<Book>(books)));
        System.out.println("SERVER | DEBUG INFO: sent books " + books);
    }

    /**
     * Handles the refresh lends operation
     * @param message the message containing the refresh operation
     * @param out the output stream
     * @throws IOException
     */
    private void handleRefreshLendsOperation(RefreshLendsMessage message, ObjectOutputStream out) throws IOException {
        List<Lends> lends = lendDAO.readAllLends();
        out.writeObject(MessageFactory.createMessage(Operation.GET_LENDS, new ArrayList<Lends>(lends)));
    }

}


