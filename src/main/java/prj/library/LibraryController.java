package prj.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import prj.library.models.Book;
import prj.library.models.Genre;
import prj.library.networking.Client;

import java.io.IOException;
import java.util.List;

public class LibraryController {

    @FXML
    private Button addButton;

    @FXML
    private Button emptyButton;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField authorTextField;

    @FXML
    private TextField yearTextField;

    @FXML
    private ChoiceBox genreChoiceBox;

    @FXML
    private TextField titleSearchTextField;

    @FXML
    private ChoiceBox genreSearchChoiceBox;

    @FXML
    private Button searchButton;

    @FXML
    private TextField authorSearchTextField;

    @FXML
    private Button lendButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private ListView searchedListView;
    
    @FXML
    private Label welcomeText;

    @FXML
    private ListView<Book> bookListView;


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
        fillChoiceBoxes();
    }

    @FXML
    protected void onAddButtonClick() {
        String title = titleTextField.getText();
        String author = authorTextField.getText();
        int year = Integer.parseInt(yearTextField.getText());
        Genre genre = (Genre) genreChoiceBox.getValue();
        Book book = new Book(title, author, year, genre);
        try {
            client.createBook(book);
            loadBooks();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error adding book: " + e.getMessage());
        }

    }

    @FXML
    protected void onEmptyButtonClick() {
        titleTextField.clear();
        authorTextField.clear();
        yearTextField.clear();
        genreChoiceBox.setValue(Genre.Genre);
    }

    @FXML
    protected void onSearchButtonClick() {

    }

    @FXML
    protected void onEditButtonClick() {

    }

    /**
     * Deletes the selected books from the list view of searched books.
     */
    @FXML
    protected void onDeleteButtonClick() {
       Book selectedBook = (Book) searchedListView.getSelectionModel().getSelectedItem();
        try {
            client.deleteBook(selectedBook.getId());
            loadBooks();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error deleting book: " + e.getMessage());
        }
    }

    @FXML
    protected void onLendButtonClick() {
        //implementare più avanti, più semplice rispetto gli altri
    }

    /**
     * Loads the books from the server and displays them in the list view.
     */
    private void loadBooks() {
        try {
            List<Book> books = client.getBooks();
            if(books.isEmpty()) {
                bookListView.getItems().add(new Book("No books found", "", 404, Genre.Genre));
                return;
            }
            ObservableList<Book> bookTitles = FXCollections.observableArrayList();
            bookTitles.addAll(books);
            bookListView.setItems(bookTitles);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading books: " + e.getMessage());
        }
    }


    /**
     * Fills the choice boxes with the available genres.
     */
    private void fillChoiceBoxes() {
        genreChoiceBox.getItems().addAll(Genre.values());
        genreSearchChoiceBox.getItems().addAll(Genre.values());
    }
}