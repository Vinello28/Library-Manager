package prj.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import prj.library.models.Book;
import prj.library.models.Genre;
import prj.library.models.Lends;
import prj.library.networking.Client;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

//import static jdk.nashorn.internal.runtime.regexp.joni.Syntax.Java;

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
    private TextField yearSearchTextField;

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

    @FXML
    private TextField copiesTextField;

    @FXML
    private Button searchLendButton;

    @FXML
    private Button returnButton;

    @FXML
    private Button editLendButton;

    @FXML
    private ListView<Lends> searchedLendListView;

    @FXML
    private ChoiceBox genreLendChoiceBox;

    @FXML
    private Tab lendopsTab;



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
        loadLends();
        fillChoiceBoxes();
    }

    @FXML
    protected void onAddButtonClick() {
        String title = titleTextField.getText();
        String author = authorTextField.getText();
        int year = Integer.parseInt(yearTextField.getText());
        Genre genre = (Genre) genreChoiceBox.getValue();
        int copies = Integer.parseInt(copiesTextField.getText());
        Book book = new Book(title, author, year, genre, copies);
        //System.out.println("CLIENT | DEBUG INFO:  adding this book " + book);
        client.createBook(book);
        loadBooks();
        onEmptyButtonClick();
    }

    @FXML
    protected void onEmptyButtonClick() {
        titleTextField.clear();
        authorTextField.clear();
        yearTextField.clear();
        copiesTextField.clear();
        genreChoiceBox.setValue(null);
    }

    @FXML
    protected void onSearchButtonClick() {
        String title = titleSearchTextField.getText();
        String author = authorSearchTextField.getText();
        int year = yearSearchTextField.getText().isEmpty() ? 0 : Integer.parseInt(yearSearchTextField.getText());
        Genre genre = (Genre) genreSearchChoiceBox.getValue();
        List<Book> books;
        int choice = 0;
        if(title.isEmpty() && author.isEmpty() && year == 0 && genre == null) choice = 15;
        if(!title.isEmpty() && author.isEmpty() && year == 0 && genre == null) choice = 1;
        if(title.isEmpty() && !author.isEmpty() && year == 0 && genre == null) choice = 2;
        if(title.isEmpty() && author.isEmpty() && year != 0 && genre == null) choice = 4;
        if(title.isEmpty() && author.isEmpty() && year == 0 && genre != null) choice = 3;
        if(!title.isEmpty() && !author.isEmpty() && year == 0 && genre == null) choice = 7;
        if(!title.isEmpty() && author.isEmpty() && year != 0 && genre == null) choice = 8;
        if(title.isEmpty() && !author.isEmpty() && year != 0 && genre == null) choice = 9;
        if(!title.isEmpty() && author.isEmpty() && year == 0 && genre != null) choice = 6;
        if(title.isEmpty() && author.isEmpty() && year != 0 && genre != null) choice = 10;
        if(title.isEmpty() && !author.isEmpty() && year == 0 && genre != null) choice = 5;
        if(!title.isEmpty() && !author.isEmpty() && year == 0 && genre != null) choice = 11;
        if(!title.isEmpty() && !author.isEmpty() && year != 0 && genre == null) choice = 12;
        if(!title.isEmpty() && author.isEmpty() && year != 0 && genre != null) choice = 13;
        if(title.isEmpty() && !author.isEmpty() && year != 0 && genre != null) choice = 14;
        if(!title.isEmpty() && !author.isEmpty() && year != 0 && genre != null) choice = 0;

        books = client.searchBooksBy(choice, title, author, year, genre);
        bookCheck_N_View(books, searchedListView);

        titleSearchTextField.clear();
        authorSearchTextField.clear();
        yearSearchTextField.clear();
        copiesTextField.clear();
        genreSearchChoiceBox.setValue(null);
    }


    @FXML
    protected void onEditButtonClick() {

        Book selectedBook = (Book) searchedListView.getSelectionModel().getSelectedItem();
        if (selectedBook == null) {
            System.out.println("No book selected for editing.");
            return;
        }

        Dialog<Book> dialog = new Dialog<>();
        dialog.setTitle("Edit Book");

        // Set the button types.
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Create the fields and populate with current book data.
        TextField titleField = new TextField(selectedBook.getTitle());
        TextField authorField = new TextField(selectedBook.getAuthor());
        TextField yearField = new TextField(String.valueOf(selectedBook.getYear()));
        ChoiceBox<Genre> genreChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(Genre.values()));
        genreChoiceBox.setValue(selectedBook.getGenre());
        TextField copiesField = new TextField(String.valueOf(selectedBook.getCopies()));

        System.out.println("CLIENT | DEBUG INFO:  editing this book " + selectedBook);

        // Create a grid pane and add the fields
        GridPane grid = new GridPane();
        grid.add(new Label("Title:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Author:"), 0, 1);
        grid.add(authorField, 1, 1);
        grid.add(new Label("Year:"), 0, 2);
        grid.add(yearField, 1, 2);
        grid.add(new Label("Genre:"), 0, 3);
        grid.add(genreChoiceBox, 1, 3);
        grid.add(new Label("Copies:"), 0, 4);
        grid.add(copiesField, 1, 4);

        dialog.getDialogPane().setContent(grid);

        // Convert the result to a Book object when the save button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                selectedBook.setTitle(titleField.getText());
                selectedBook.setAuthor(authorField.getText());
                selectedBook.setYear(Integer.parseInt(yearField.getText()));
                selectedBook.setGenre(genreChoiceBox.getValue());
                selectedBook.setCopies(Integer.parseInt(copiesField.getText()));
                return selectedBook;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(editedBook -> {
            client.updateBook(editedBook);
            loadBooks();
        });
    }


    /**
     * Deletes the selected books from the list view of searched books.
     */
    @FXML
    protected void onDeleteButtonClick() {
        Book selectedBook = (Book) searchedListView.getSelectionModel().getSelectedItem();
        client.deleteBook(selectedBook.getId());
        loadBooks();
    }

    @FXML
    protected void onLendButtonClick() {

        Book selectedBook = (Book) searchedListView.getSelectionModel().getSelectedItem();
        final Lends[] lend = new Lends[1];

        if (selectedBook == null) {
            System.out.println("No book selected for editing.");
            return;
        }

        Dialog<Book> dialog = new Dialog<>();
        dialog.setTitle("Lend Book");

        // Set the button types.
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Create the fields and populate with current book data.
        TextField cellTextfield = new TextField();
        DatePicker returnDatePicker = new DatePicker();
        TextField surnameTextField = new TextField();
        Label booktitleLabel = new Label(selectedBook.getTitle());

        System.out.println("CLIENT | DEBUG INFO:  editing this book " + selectedBook);

        // Create a grid pane and add the fields
        GridPane grid = new GridPane();
        grid.add(new Label("Cellphone number:"), 0, 0);
        grid.add(cellTextfield, 1, 0);
        grid.add(new Label("Return date:"), 0, 1);
        grid.add(returnDatePicker, 1, 1);
        grid.add(new Label("Surname:"), 0, 2);
        grid.add(surnameTextField, 1, 2);
        grid.add(new Label("Book's title:"), 0, 3);
        grid.add(booktitleLabel, 1, 3);

        dialog.getDialogPane().setContent(grid);
        lend[0] = null;

        // Convert the result to a Book object when the save button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {

                selectedBook.setCopies(selectedBook.getCopies()-1);
                return selectedBook;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(editedBook -> {
            lend[0] = new Lends(selectedBook.getId(), java.sql.Date.valueOf(returnDatePicker.getValue()), surnameTextField.getText(), cellTextfield.getText());
            client.createLend(lend[0]);
            client.updateBook(editedBook);
            loadBooks();
            //loadLends();
        });
    }

    /**
     * Loads the books from the server and displays them in the list view.
     */
    private void loadBooks() {
        List<Book> books = client.getBooks();
        bookCheck_N_View(books, bookListView);
    }

    /**
     * Loads the lends from the server and displays them in the list view.
     */
    private void loadLends() {
        List<Lends> lends = client.getLends();
        lendCheck_N_View(lends, searchedLendListView);
    }


    @FXML
    private void onLateSearchButtonClick() {
        List<Lends> lends = client.getLends();
        List<Lends> lateLends = lends.stream().filter(Lends::isLate).collect(Collectors.toList());
        lendCheck_N_View(lateLends, searchedLendListView);
    }

    /**
     * Fills the choice boxes with the available genres.
     */
    private void fillChoiceBoxes() {
        genreChoiceBox.getItems().addAll(Genre.values());
        genreSearchChoiceBox.getItems().addAll(Genre.values());
        genreLendChoiceBox.getItems().addAll(Genre.values());
    }


    /**
     * Checks if the list of books is empty and adds a message to the list view.
     * @param books the list of books to check
     * @param searchedListView the list view to add the message to
     * @return true if the list is empty, false otherwise
     */
    private void bookCheck_N_View(List<Book> books, ListView searchedListView) {
        if(books==null || books.isEmpty()) {
            searchedListView.getItems().add(new Book("No books found", "", 404, Genre.Genre));
        }
        ObservableList<Book> bookTitles = FXCollections.observableArrayList();
        bookTitles.addAll(books);
        searchedListView.setItems(bookTitles);
    }

    /**
     * Checks if the list of lends is empty and adds a message to the list view.
     * @param lends the list of lends to check
     * @param searchedLendListView the list view to add the message to
     * @return true if the list is empty, false otherwise
     */
    private void lendCheck_N_View(List<Lends> lends, ListView searchedLendListView) {
        if(lends==null || lends.isEmpty()) {
            System.out.println("CLIENT | DEBUG_INFO: lends is null");
            searchedLendListView.getItems().add(new Lends(404, new Date(), "No lends found", "404"));
        }
        ObservableList<Lends> lendTitles = FXCollections.observableArrayList();

        System.out.println("CLIENT | DEBUG_INFO: " + lends);
        assert lends != null;
        lendTitles.addAll(lends);
        searchedLendListView.setItems(lendTitles);
    }
}