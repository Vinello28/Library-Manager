package prj.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import prj.library.models.Book;
import prj.library.networking.Client;

import java.io.IOException;
import java.util.List;

public class LibraryController {
    @FXML
    private Label welcomeText;

    @FXML
    private ListView<String> bookListView;


    private Client client;

    public LibraryController() {
        client = new Client();
        try {
            client.start();
        } catch (IOException e) {
            try {
                client.close();
            } catch (IOException ex) {
                System.out.println("Client exception: " + ex.getMessage());
            }
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void initialize() {
        loadBooks();
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onAddButtonClick() {

    }

    @FXML
    protected void onSearchButtonClick() {

    }

    @FXML
    protected void onEditButtonClick() {

    }

    @FXML
    protected void onDeleteButtonClick() {

    }

    @FXML
    protected void onLendButtonClick() {
        //implementare più avanti, più semplice rispetto gli altri
    }

    private void loadBooks() {
        try {
            List<Book> books = client.getBooks();
            if(books.isEmpty()) {
                bookListView.getItems().add("No books found");
                return;
            }
            ObservableList<String> bookTitles = FXCollections.observableArrayList();
            for (Book book : books) {
                bookTitles.add(book.getTitle());
            }
            bookListView.setItems(bookTitles);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading books: " + e.getMessage());
        }
    }
}