package prj.library;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import prj.library.models.Book;
import prj.library.models.Customer;
import prj.library.models.Genre;
import prj.library.models.Lends;
import javafx.scene.layout.GridPane;
import prj.library.networking.ClientController;
import prj.library.utils.CLIUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static prj.library.utils.CLIUtils.*;


public class ViewController {

    private ClientController clientController;

    @FXML
    private Pane homePane;
    @FXML
    private Pane addBookPane;
    @FXML
    private Pane searchBooksPane;
    @FXML
    private Pane lendPane;
    @FXML
    private Pane customersPane;
    @FXML
    private StackPane opStackPane;
    @FXML
    private Button addABButton;
    @FXML
    private TableView<Book> homeTableView;
    @FXML
    private TableView<Book> searchBookTableView;
    @FXML
    private TableView<Customer> customersTableView;
    @FXML
    private TableColumn<Book, Integer> idHColumn;
    @FXML
    private TableColumn<Book, String> titleHColumn;
    @FXML
    private TableColumn<Book, String> authorHColumn;
    @FXML
    private TableColumn<Book, Genre> genreHColumn;
    @FXML
    private TableColumn<Book, Integer> yearHColumn;
    @FXML
    private TableColumn<Book, Integer> copiesHColumn;
    @FXML
    private TableColumn<Book, Integer> idSColumn;
    @FXML
    private TableColumn<Book, String> titleSColumn;
    @FXML
    private TableColumn<Book, String> authorSColumn;
    @FXML
    private TableColumn<Book, Genre> genreSColumn;
    @FXML
    private TableColumn<Book, Integer> yearSColumn;
    @FXML
    private TableColumn<Book, Integer> copiesSColumn;
    @FXML
    private TableColumn<Customer, Integer> idCColumn;
    @FXML
    private TableColumn<Customer, String> nameCColumn;
    @FXML
    private TableColumn<Customer, String> emailCColumn;
    @FXML
    private TableColumn<Customer, String> phoneCColumn;
    @FXML
    private TableColumn<Customer, String> addressCColumn;
    @FXML
    private TextField titleABTextField;
    @FXML
    private TextField authorABTextField;
    @FXML
    private ChoiceBox<Genre> genreABChoiceBox;
    @FXML
    private TextField yearABTextField;
    @FXML
    private TextField copiesABTextField;
    @FXML
    private TextField titleSBTextField;
    @FXML
    private TextField authorSBTextField;
    @FXML
    private ChoiceBox<Genre> genreSBChoiceBox;
    @FXML
    private TextField yearSBTextField;



    public ViewController() {
        try {
            clientController = new ClientController();
        } catch (IOException e) {
            if (clientController != null) clientController.close();
            CLIUtils.clientCriticalError(e.getMessage());
            showErrorDialog("Error", "Critical error", "Could not connect to the server. Please try again later.");
            LibraryApplication.close();
        }
    }

    @FXML
    public void initialize() {

        hideAllPanes();

        // Set up the home view
        showPane(homePane);

        initHomeTableView();
        initSearchTableView();
        //initCustomersTableView();
        initChoiceBoxes();

        loadBooks();
        //add listeners for customers tableview to show add or update TODO: do this.
    }


    /**
     * Shows the add book view.
     */
    private void showPane(Pane pane) {
        pane.toFront();
        pane.setVisible(true);
    }

    /**
     * Shows the home view.
     */
    private void hideAllPanes() {
        homePane.toBack();
        homePane.setVisible(false);
        addBookPane.toBack();
        addBookPane.setVisible(false);
        searchBooksPane.toBack();
        searchBooksPane.setVisible(false);
        lendPane.toBack();
        lendPane.setVisible(false);
        customersPane.toBack();
        customersPane.setVisible(false);
    }

    @FXML
    private void onAddBookButtonClick(){
        String title = titleABTextField.getText();
        String author = authorABTextField.getText();
        Genre genre = genreABChoiceBox.getValue();
        String year = yearABTextField.getText();
        String copies = copiesABTextField.getText();


        if(title.isEmpty() || author.isEmpty() || genre == null || year.isEmpty() || copies.isEmpty()) {
            showErrorDialog("Error", "Invalid input", "Please fill all fields.");
            return;
        }

        try {
            int yearInt = Integer.parseInt(year);
            int copiesInt = Integer.parseInt(copies);

            Book book = new Book(title, author, yearInt, genre, copiesInt);
            clientController.createBook(book);
        } catch (NumberFormatException e) {
            showErrorDialog("Error", "Invalid input", "Please enter a valid year and number of copies.");
        } catch (IllegalArgumentException e) {
            showErrorDialog("Error", "Invalid input", "Please enter a valid genre.");
        }
    }

    @FXML
    private void onCancelButtonClick(){
        titleABTextField.clear();
        authorABTextField.clear();
        genreABChoiceBox.setValue(null);
        yearABTextField.clear();
        copiesABTextField.clear();
    }

    @FXML
    protected void onSearchBookButtonClick() {
        String title = titleSBTextField.getText();
        String author = authorSBTextField.getText();
        int year = yearSBTextField.getText().isEmpty() ? 0 : Integer.parseInt(yearSBTextField.getText());
        Genre genre = genreSBChoiceBox.getValue();
        List<Book> books;
        int choice = 0;

        if(!title.isEmpty() && !author.isEmpty() && year != 0 && genre != null) choice = 0;
        if(!title.isEmpty() && author.isEmpty() && year == 0 && genre == null) choice = 1;
        if(title.isEmpty() && !author.isEmpty() && year == 0 && genre == null) choice = 2;
        if(title.isEmpty() && author.isEmpty() && year != 0 && genre == null) choice = 3;
        if(title.isEmpty() && author.isEmpty() && year == 0 && genre != null) choice = 4;
        if(!title.isEmpty() && !author.isEmpty() && year == 0 && genre == null) choice = 5;
        if(!title.isEmpty() && author.isEmpty() && year != 0 && genre == null) choice = 6;
        if(!title.isEmpty() && author.isEmpty() && year == 0 && genre != null) choice = 7;
        if(title.isEmpty() && !author.isEmpty() && year != 0 && genre == null) choice = 8;
        if(title.isEmpty() && !author.isEmpty() && year == 0 && genre != null) choice = 9;
        if(title.isEmpty() && author.isEmpty() && year != 0 && genre != null) choice = 10;
        if(!title.isEmpty() && !author.isEmpty() && year != 0 && genre == null) choice = 11;
        if(!title.isEmpty() && !author.isEmpty() && year == 0 && genre != null) choice = 12;
        if(!title.isEmpty() && author.isEmpty() && year != 0 && genre != null) choice = 13;
        if(title.isEmpty() && !author.isEmpty() && year != 0 && genre != null) choice = 14;
        if (title.isEmpty() && author.isEmpty() && year == 0 && genre == null) choice = 15;

        Book tmp = new Book(title, author, year, genre);
        books = clientController.searchBooksBy(choice, tmp);
        showBooksOnTableView(books, searchBookTableView);

        clearSearchBooksFields();
    }


    /**
     * Edits the selected book from the list view of searched books.
     */
    @FXML
    protected void onUpdateBookButtonClick() {
        Book selectedBook = searchBookTableView.getSelectionModel().getSelectedItem();

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
            clientController.updateBook(editedBook);
            List<Book> books = new ArrayList<>();
            books.add(clientController.readBook(editedBook.getId()));
            showBooksOnTableView(books, searchBookTableView);
        });
    }

    /**
     * Deletes the selected books from the list view of searched books.
     */
    @FXML
    protected void onDeleteBookButtonClick() {
        Book selectedBook = searchBookTableView.getSelectionModel().getSelectedItem();

        if (selectedBook == null) {
            CLIUtils.clientInfo("No book selected for editing.");
            showErrorDialog("Error", "No book selected", "Please select a book to delete.");
            return;
        }
        clientController.deleteBook(selectedBook.getId());
        searchBookTableView.getItems().remove(selectedBook);
    }

    /**
     * Deletes the selected lends from the list view of searched lends.
     */
    @FXML
    protected void onLendBookButtonClick() {
        Book selectedBook = searchBookTableView.getSelectionModel().getSelectedItem();
        final Lends[] lend = new Lends[1];

        if (selectedBook == null) {
            CLIUtils.clientInfo("No book selected for editing.");
            showErrorDialog("Error", "No book selected", "Please select a book to lend.");
            return;
        }

        Book refreshedBook = clientController.readBook(selectedBook.getId());
        if (refreshedBook.getCopies() <= 0) {
            showErrorDialog("Error", "No copies available", "There are no copies of this book available.");
            return;
        }

        Dialog<Book> dialog = new Dialog<>();
        dialog.setTitle("Lend Book");

        // Set the button types.
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Create the fields and populate with current book data.
        ChoiceBox<Customer> customerChoiceBox = new ChoiceBox<>();
        customerChoiceBox.getItems().addAll(clientController.getCustomers());
        DatePicker returnDatePicker = new DatePicker();
        Label booktitleLabel = new Label(selectedBook.getTitle());

        // Create a grid pane and add the fields
        GridPane grid = new GridPane();
        grid.add(new Label("Chose customer:"), 0, 0);
        grid.add(customerChoiceBox, 1, 0);
        grid.add(new Label("Return date:"), 0, 1);
        grid.add(returnDatePicker, 1, 1);
        grid.add(new Label("Book's title:"), 0, 2);
        grid.add(booktitleLabel, 1, 2);

        dialog.getDialogPane().setContent(grid);
        lend[0] = null;

        // Convert the result to a Book object when the save button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                if(clientController.readBook(selectedBook.getId()).getCopies() <= 0) {
                    showErrorDialog("Error", "No copies available", "There are no copies of this book available.");
                    return null;
                } else {
                    selectedBook.setCopies(selectedBook.getCopies()-1);
                    return selectedBook;
                }
            } else {
                return null;
            }
        });

        dialog.showAndWait().ifPresent(editedBook -> {
            lend[0] = new Lends(selectedBook.getId(), customerChoiceBox.getValue().getId(),returnDatePicker.getValue(), false);
            clientController.createLend(lend[0]);
            clientController.updateBook(editedBook);
            List<Book> books = new ArrayList<>();
            books.add(clientController.readBook(editedBook.getId())); //TODO check to remove
        });
    }



    @FXML
    public void onHomeClick(){
        hideAllPanes();
        showPane(homePane);
        loadBooks();
    }

    @FXML
    public void onAddClick(){
        hideAllPanes();
        showPane(addBookPane);
    }

    @FXML
    public void onSearchClick(){
        hideAllPanes();
        showPane(searchBooksPane);
    }

    @FXML
    public void onLendClick(){
        hideAllPanes();
        showPane(lendPane);
    }

    @FXML
    public void onCustomerClick(){
        hideAllPanes();
        showPane(customersPane);
    }

    /**
     * Shows an error dialog with the given title, header and content.
     * @param title the title of the dialog
     * @param header the header of the dialog
     * @param content the content of the dialog
     */
    private void showErrorDialog(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Shows the books on the given table view.
     * @param books the books to show
     * @param tableView the table view to show the books on
     */
    private void showBooksOnTableView(List<Book> books, TableView tableView) {

        if(books == null) {
            showErrorDialog("Error", "Critical error", "Could not connect to the server. Please try again later.");
            return;
        }

        ObservableList<Book> observableList = FXCollections.observableArrayList(books);
        tableView.setItems(observableList);
    }

    /**
     * Loads the books from the server and shows them on the home table view.
     * This method is called when the home view is shown.
     */
    private void loadBooks() {
        List<Book> books = clientController.getBooks();
        showBooksOnTableView(books, homeTableView);
    }

    /**
     * Initializes the home table view.
     */
    private void initHomeTableView() {
        idHColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleHColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorHColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        genreHColumn.setCellValueFactory(new PropertyValueFactory<>( "genre"));
        yearHColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        copiesHColumn.setCellValueFactory(new PropertyValueFactory<>("copies"));
    }

    /**
     * Initializes the search table view.
     */
    private void initSearchTableView() {
        idSColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleSColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorSColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        genreSColumn.setCellValueFactory(new PropertyValueFactory<>( "genre"));
        yearSColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        copiesSColumn.setCellValueFactory(new PropertyValueFactory<>("copies"));
    }

    /**
     * Initializes the customers table view.
     */
    private void initCustomersTableView() {
        idCColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailCColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneCColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addressCColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    /**
     * Shows the customers on the given table view.
     * @param customers the customers to show
     * @param tableView the table view to show the customers on
     */
    private void showCustomersOnTableView(List<Customer> customers, TableView tableView) {
        if(customers == null) {
            showErrorDialog("Error", "Critical error", "Could not connect to the server. Please try again later.");
            return;
        }
        ObservableList<Customer> observableList = FXCollections.observableArrayList(customers);
        tableView.setItems(observableList);
    }

    /**
     * Initializes choice boxes.
     */
    private void initChoiceBoxes() {
        genreABChoiceBox.getItems().addAll(Genre.values());
        genreSBChoiceBox.getItems().addAll(Genre.values());
    }

    private void clearSearchBooksFields() {
        titleSBTextField.clear();
        authorSBTextField.clear();
        yearSBTextField.clear();
        genreSBChoiceBox.setValue(null);
    }
}
