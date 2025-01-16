package prj.library;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import prj.library.models.Book;
import prj.library.models.Customer;
import prj.library.models.Genre;
import prj.library.models.Lends;
import javafx.scene.layout.GridPane;
import prj.library.networking.ClientController;
import prj.library.utils.CLIUtils;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The ViewController class is responsible for handling the GUI of the application.
 */
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
    private Pane statsPane;
    @FXML
    private Pane customersPane;
    @FXML
    private TableView<Book> homeTableView;
    @FXML
    private TableView<Book> searchBookTableView;
    @FXML
    private TableView<Lends> searchLendTableView;
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
    private TableColumn<Lends, Integer> idLColumn;
    @FXML
    private TableColumn<Lends, Integer> bidLColumn;
    @FXML
    private TableColumn<Lends, Integer> cidLColumn;
    @FXML
    private TableColumn<Lends, String> returnLColumn;
    @FXML
    private TableColumn<Lends, Boolean> returnedLColumn;
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
    @FXML
    private TextField titleLendTextField;
    @FXML
    private DatePicker returnLendDatePicker;
    @FXML
    private TextField phoneLendTextField;
    @FXML
    private ChoiceBox<Boolean> returnedLendChoiceBox;
    @FXML
    private Button addateCustomerButton;
    @FXML
    private TextField nameCustomerTextField;
    @FXML
    private TextField phoneCustomerTextField;
    @FXML
    private TextField emailCustomerTextField;
    @FXML
    private TextField addressCustomerTextField;
    @FXML
    private PieChart genrePieChart;
    @FXML
    private BarChart<String, Number> numusBarChart;
    @FXML
    private Label labelCount;


    /**
     * Constructor
     * <p>
     * Initialize the client controller
     */
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

        //Setting up the home view
        showPane(homePane);

        initHomeTableView();
        initSearchTableView();
        initCustomersTableView();
        initLendsTableView();
        initChoiceBoxes();
        initGenrePieChart();
        initNumusBarChart();

        loadBooks();

        //Add listener to ListView
        customersTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                //Update TextFields with selected customer data
                nameCustomerTextField.setText(newValue.getName());
                phoneCustomerTextField.setText(newValue.getPhone());
                emailCustomerTextField.setText(newValue.getEmail());
                addressCustomerTextField.setText(newValue.getAddress());

                //Change button text to "Update"
                addateCustomerButton.setText("\uD83D\uDCA1 update");
            }
        });
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

        clearBookFields();
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

    @FXML
    protected void onUpdateBookButtonClick() {
        Book selectedBook = searchBookTableView.getSelectionModel().getSelectedItem();

        if (selectedBook == null) {
            showErrorDialog("Warning", "Selection error", "No books selected for editing");
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

    @FXML
    protected void onLendBookButtonClick() {
        Book selectedBook = searchBookTableView.getSelectionModel().getSelectedItem();
        final Lends[] lend = new Lends[1];

        if (selectedBook == null) {
            showErrorDialog("Error", "No book selected", "Please select a book to lend.");
            return;
        }

        Book refreshedBook = clientController.readBook(selectedBook.getId());
        if (refreshedBook.getCopies() < 1) {
            showErrorDialog("Error", "No copies available", "There are no copies of this book available.");
            return;
        }

        Dialog<Book> dialog = new Dialog<>();
        dialog.setTitle("Lend Book");

        //Set button types
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        //Create the fields and populate with current book data
        ChoiceBox<Customer> customerChoiceBox = new ChoiceBox<>();
        customerChoiceBox.getItems().addAll(clientController.getCustomers());
        DatePicker returnDatePicker = new DatePicker();
        Label booktitleLabel = new Label(selectedBook.getTitle());

        //Create a grid pane and add the fields
        GridPane grid = new GridPane();
        grid.add(new Label("Chose customer:"), 0, 0);
        grid.add(customerChoiceBox, 1, 0);
        grid.add(new Label("Return date:"), 0, 1);
        grid.add(returnDatePicker, 1, 1);
        grid.add(new Label("Book's title:"), 0, 2);
        grid.add(booktitleLabel, 1, 2);

        dialog.getDialogPane().setContent(grid);
        lend[0] = null;

        //Convert the result to a Book object when the save button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                if(clientController.readBook(selectedBook.getId()).getCopies() < 1) {
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
            books.add(clientController.readBook(editedBook.getId()));
            showBooksOnTableView(books, searchBookTableView);
        });
    }

    @FXML
    protected void onLateLendButtonClick() {
        List<Lends> lends = clientController.searchLateLends();
        clearSearchLends();
        showLendsOnTableView(lends, searchLendTableView);
    }

    @FXML
    protected void onSearchLendButtonClick(){
        searchLendTableView.getItems().clear();
        String title = titleLendTextField.getText();
        LocalDate date = returnLendDatePicker.getValue();
        String cell = phoneLendTextField.getText();
        Boolean retVal = returnedLendChoiceBox.getValue();

        int b_id=0; //book id
        int c_id=0; //customer id
        boolean returned;
        boolean sentinel = false;

        if(retVal != null && retVal) returned = true;
        else if (retVal != null && !retVal) {
            returned = false;
            sentinel = true;
        } else returned = false;

        //checks if book and customer exists
        if (!title.isEmpty()) {
            List<Book> books = clientController.searchBooksBy(1, new Book(0, title, "", 0, Genre.NULL, 0));
            if (books == null || books.isEmpty()) {
                showErrorDialog("Error", "Book not found", "Book you are looking for is not found. Try something else.");
                clearSearchLends();
                return;
            }
            b_id = books.get(0).getId();
        }
        if(!cell.isEmpty()) {
            List<Customer> customers = clientController.searchCustomersBy(2, new Customer("", "", cell, ""));
            if (customers == null || customers.isEmpty()) {
                showErrorDialog("Error", "Customer not found", "Customer you are looking for is not found. Try something else.");
                clearSearchLends();
                return;
            }
            c_id = customers.get(0).getId();
        }

        int choice=7;
        if (b_id != 0 && c_id != 0 && date != null) choice = 0;
        else if (b_id != 0 && c_id == 0 && date == null) choice = 1;
        else if (b_id == 0 && c_id != 0 && date == null) choice = 2;
        else if (b_id == 0 && c_id == 0 && date != null) choice = 3;
        else if (b_id != 0 && c_id != 0 && date == null) choice = 4;
        else if (b_id != 0 && c_id == 0 && date != null) choice = 5;
        else if (b_id == 0 && c_id != 0 && date != null) choice = 6;

        List<Lends> lends = clientController.searchLendsBy(choice, b_id, date, c_id, returned, sentinel);
        showLendsOnTableView(lends, searchLendTableView);
        clearSearchLends();
    }

    @FXML
    protected void onReturnLendButtonClick() {
        Lends selectedLend = searchLendTableView.getSelectionModel().getSelectedItem();
        if (selectedLend == null) {
            showErrorDialog("Error", "No lend selected", "Please select a lend to return.");
            return;
        }

        Book book = clientController.readBook(selectedLend.getBookId());

        book.setCopies(book.getCopies()+1);
        clientController.updateBook(book);
        selectedLend.setReturned(true);
        clientController.updateLend(selectedLend);

        List<Lends> lends = new ArrayList<>();
        lends.add(clientController.readLend(selectedLend));
        showLendsOnTableView(lends, searchLendTableView);
        clearSearchLends();
    }

    @FXML
    protected void onDeleteLendButtonClick() {
        Lends selectedLend = searchLendTableView.getSelectionModel().getSelectedItem();
        if (selectedLend == null) {
            showErrorDialog("Error", "No lend selected", "Please select a lend to delete.");
            return;
        }

        clientController.deleteLend(selectedLend);
        searchLendTableView.getItems().remove(selectedLend);
        clearSearchLends();
    }

    @FXML
    protected void onUpdateLendButtonClick() {
        Lends selectedLend = searchLendTableView.getSelectionModel().getSelectedItem();
        if (selectedLend == null) {
            showErrorDialog("Error", "No lend selected", "Please select a lend to update.");
            return;
        }

        Dialog<Lends> dialog = new Dialog<>();
        dialog.setTitle("Update Lend");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        Label customerLabel = new Label(clientController.readCustomer(selectedLend.getCustomerId()).getName());
        DatePicker returnDatePicker = new DatePicker();
        Label booktitleLabel = new Label(clientController.readBook(selectedLend.getBookId()).getTitle());

        GridPane grid = new GridPane();
        grid.add(new Label("Customer:"), 0, 0);
        grid.add(customerLabel, 1, 0);
        grid.add(new Label("Return date:"), 0, 1);
        grid.add(returnDatePicker, 1, 1);
        grid.add(new Label("Book's title:"), 0, 2);
        grid.add(booktitleLabel, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                selectedLend.setReturnDate(returnDatePicker.getValue());
                return selectedLend;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(editedLend -> {
            clientController.updateLend(editedLend);
            List<Lends> lends = new ArrayList<>();
            lends.add(clientController.readLend(editedLend));
            showLendsOnTableView(lends, searchLendTableView);
        });
        clearSearchLends();
    }

    @FXML
    protected void onAddateCustomerButtonClick() {
        Customer selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            updateCustomer(selectedCustomer);
        } else {
            addCustomer();
        }
    }

    @FXML
    protected void onDeleteCustomerButtonClick() {
        Customer selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();
        if(selectedCustomer == null) {
            showErrorDialog("Error", "No customer selected", "Please select a customer to delete.");
            return;
        }
        clientController.deleteCustomer(selectedCustomer);
        customersTableView.getSelectionModel().clearSelection();
        onCancelCustomerButtonClick();
        customersTableView.getItems().remove(selectedCustomer);
    }

    @FXML
    protected void onSearchCustomerButtonClick(){
        String name = nameCustomerTextField.getText();
        String phone = phoneCustomerTextField.getText();
        String email = emailCustomerTextField.getText();
        String address = addressCustomerTextField.getText();
        int choice;
        if(!name.isEmpty() && !phone.isEmpty() && !email.isEmpty() && !address.isEmpty()) choice = 0;
        else if(!name.isEmpty() && phone.isEmpty() && email.isEmpty() && address.isEmpty()) choice = 1;
        else if(name.isEmpty() && !phone.isEmpty() && email.isEmpty() && address.isEmpty()) choice = 2;
        else if(name.isEmpty() && phone.isEmpty() && !email.isEmpty() && address.isEmpty()) choice = 3;
        else if(name.isEmpty() && phone.isEmpty() && email.isEmpty() && !address.isEmpty()) choice = 4;
        else if(!name.isEmpty() && !phone.isEmpty() && email.isEmpty() && address.isEmpty()) choice = 5;
        else if(!name.isEmpty() && !phone.isEmpty() && !email.isEmpty() && address.isEmpty()) choice = 6;
        else if(!name.isEmpty() && phone.isEmpty() && !email.isEmpty() && address.isEmpty()) choice = 7;
        else if(!name.isEmpty() && phone.isEmpty() && email.isEmpty() && !address.isEmpty()) choice = 8;
        else if(name.isEmpty() && !phone.isEmpty() && !email.isEmpty() && address.isEmpty()) choice = 9;
        else if(name.isEmpty() && !phone.isEmpty() && email.isEmpty() && !address.isEmpty()) choice = 10;
        else if(name.isEmpty() && phone.isEmpty() && !email.isEmpty() && !address.isEmpty()) choice = 11;
        else if(!name.isEmpty() && phone.isEmpty() && !email.isEmpty() && !address.isEmpty()) choice = 12;
        else if(name.isEmpty() && !phone.isEmpty() && !email.isEmpty() && !address.isEmpty()) choice = 13;
        else if(!name.isEmpty() && !phone.isEmpty() && email.isEmpty() && !address.isEmpty()) choice = 14;
        else choice = 15;

        List<Customer> customers = clientController.searchCustomersBy(choice, new Customer(name, email, phone, address));
        if(customers==null || customers.isEmpty()) showErrorDialog("Error", "No customers found", "Customers you are looking for are not found. Try something else.");
        else {
            ObservableList<Customer> customerNames = FXCollections.observableArrayList();
            customerNames.addAll(customers);
            customersTableView.setItems(customerNames);
        }
    }

    @FXML
    protected void onCancelCustomerButtonClick() {
        clearSearchCustomers();
        addateCustomerButton.setText("\uD83D\uDE4B add");
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

    @FXML
    public void onStatsClick(){
        hideAllPanes();
        showPane(statsPane);
        updateGenreChart();
        updateCustomerLendsBarChart();
        updateLendCount();
    }

    @FXML
    public void onAlertClick(){
        Boolean tmp = clientController.sendAlerts();

        CLIUtils.clientInfo("sending mail notifications to customers with late returns");

        if (tmp) {
            showErrorDialog("Alert", "Alerts sent", "Alerts have been sent to customers with late returns.");
        } else {
            showErrorDialog("Error", "No alerts sent", "No alerts have been sent to customers with late returns.");
        }
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
        initTableView(idHColumn, titleHColumn, authorHColumn, genreHColumn, yearHColumn, copiesHColumn);
    }

    /**
     * Initialize TableView columns
     * @param idColumn the book's id column
     * @param titleColumn the book's title column
     * @param authorColumn the book's author column
     * @param genreColumn the book's genre column
     * @param yearColumn the book's year column
     * @param copiesColumn the book's copies column
     */
    private void initTableView(TableColumn<Book, Integer> idColumn, TableColumn<Book, String> titleColumn, TableColumn<Book, String> authorColumn, TableColumn<Book, Genre> genreColumn, TableColumn<Book, Integer> yearColumn, TableColumn<Book, Integer> copiesColumn) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>( "genre"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        copiesColumn.setCellValueFactory(new PropertyValueFactory<>("copies"));
    }

    /**
     * Initializes the search table view.
     */
    private void initSearchTableView() {
        initTableView(idSColumn, titleSColumn, authorSColumn, genreSColumn, yearSColumn, copiesSColumn);
    }

    /**
     * Initializes the lends table view.
     */
    private void initLendsTableView() {
        idLColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        bidLColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        cidLColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        returnLColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        returnedLColumn.setCellValueFactory(new PropertyValueFactory<>("returned"));
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
     * Updates the customer with the data from the text fields.
     * @param customer the customer to update
     */
    private void updateCustomer(Customer customer) {
        customer.setName(nameCustomerTextField.getText());
        customer.setPhone(phoneCustomerTextField.getText());
        customer.setEmail(emailCustomerTextField.getText());
        customer.setAddress(addressCustomerTextField.getText());

        clientController.updateCustomer(customer);

        List<Customer> customers = new ArrayList<>();
        customers.add(clientController.readCustomer(customer.getId()));
        showCustomersOnTableView(customers, customersTableView);
        onCancelCustomerButtonClick();

    }

    /**
     * Adds a new customer with the data from the text fields.
     */
    private void addCustomer() {
        String name = nameCustomerTextField.getText();
        String phone = phoneCustomerTextField.getText();
        String email = emailCustomerTextField.getText();
        String address = addressCustomerTextField.getText();

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || address.isEmpty()) {
            showErrorDialog("Error", "Invalid input", "Please fill all fields.");
            return;
        }

        Customer newCustomer = new Customer(name, email, phone, address);
        clientController.createCustomer(newCustomer);

        List<Customer> customers = new ArrayList<>();

        customers = clientController.searchCustomersBy(0, new Customer(name, email, phone, address));
        showCustomersOnTableView(customers, customersTableView);
        clearSearchCustomers();
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
     * Shows the lends on the given table view.
     * @param lends the lends to show
     * @param tableView the table view to show the lends on
     */
    private void showLendsOnTableView(List<Lends> lends, TableView tableView) {
        if (lends == null) {
            showErrorDialog("Error", "Critical error", "Could not connect to the server. Please try again later.");
            return;
        }

        ObservableList<Lends> observableList = FXCollections.observableArrayList(lends);
        tableView.setItems(observableList);
    }

    /**
     * Initializes choice boxes.
     */
    private void initChoiceBoxes() {
        genreABChoiceBox.getItems().addAll(Genre.values());
        genreSBChoiceBox.getItems().addAll(Genre.values());
        returnedLendChoiceBox.getItems().addAll(true, false);
    }

    /**
     * Clears the book fields.
     */
    private void clearBookFields() {
        titleABTextField.clear();
        authorABTextField.clear();
        genreABChoiceBox.setValue(null);
        yearABTextField.clear();
        copiesABTextField.clear();
    }

    /**
     * Clears the search books fields.
     */
    private void clearSearchBooksFields() {
        titleSBTextField.clear();
        authorSBTextField.clear();
        yearSBTextField.clear();
        genreSBChoiceBox.setValue(null);
    }

    /**
     * Clears the search lends fields.
     */
    private void clearSearchLends() {
        titleLendTextField.clear();
        returnLendDatePicker.setValue(null);
        phoneLendTextField.clear();
        returnedLendChoiceBox.setValue(null);
    }

    /**
     * Clears the search customers fields.
     */
    private void clearSearchCustomers() {
        nameCustomerTextField.clear();
        phoneCustomerTextField.clear();
        emailCustomerTextField.clear();
        addressCustomerTextField.clear();
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
        statsPane.toBack();
        statsPane.setVisible(false);
    }

    /**
     * Updates the genre pie chart.
     */
    public void updateGenreChart() {
        Map<Genre, Long> genreStats = clientController.calculateGenreLendingStats();

        // Calcola il totale dei prestiti per il calcolo delle percentuali
        long totalLends = genreStats.values().stream().mapToLong(Long::longValue).sum();

        // Crea i dati per il PieChart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        genreStats.forEach((genre, count) -> {
            // Calcola la percentuale
            double percentage = (count.doubleValue() / totalLends) * 100;
            // Aggiungi i dati al PieChart con la percentuale formattata
            pieChartData.add(new PieChart.Data(
                    String.format("%s (%.1f%%)", genre.toString(), percentage),
                    count
            ));
        });

        // Aggiorna il PieChart
        genrePieChart.setData(pieChartData);
        genrePieChart.setTitle("Lends Distribution by Genre");
    }

    /**
     * Initializes the genre pie chart.
     */
    private void initGenrePieChart() {
        genrePieChart.setLabelLineLength(10);
        genrePieChart.setLabelsVisible(true);
        genrePieChart.setStartAngle(90);
    }

    /**
     * Updates the customer lending chart.
     */
    public void updateCustomerLendsBarChart() {
        numusBarChart.getData().clear();

        // Crea una nuova serie per i dati
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Distribution of Lends by Customers");

        // Ottieni le statistiche
        Map<Customer, Integer> customerStats = clientController.calculateCustomerLendingStats();

        // Ordina i clienti per numero di prestiti (decrescente)
        customerStats.entrySet().stream()
                .sorted(Map.Entry.<Customer, Integer>comparingByValue().reversed())
                .forEach(entry -> {
                    Customer customer = entry.getKey();
                    Integer lendCount = entry.getValue();

                    if (lendCount > 0) {  // Mostra solo clienti con almeno un prestito
                        // Crea l'etichetta per il cliente
                        String customerLabel = String.format("ID: %d", customer.getId());

                        // Aggiungi i dati alla serie con il valore numerico corretto
                        XYChart.Data<String, Number> data = new XYChart.Data<>(customerLabel, lendCount);
                        series.getData().add(data);
                    }
                });

        // Aggiungi la serie al grafico
        numusBarChart.getData().add(series);

        // Imposta il range dell'asse Y
        NumberAxis yAxis = (NumberAxis) numusBarChart.getYAxis();
        yAxis.setAutoRanging(false);
        int maxLends = customerStats.values().stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(10);
        yAxis.setUpperBound(maxLends + 1);
        yAxis.setLowerBound(0);
        yAxis.setTickUnit(1);

        // Personalizza il grafico
        numusBarChart.setTitle("Lends per customer");
        CategoryAxis xAxis = (CategoryAxis) numusBarChart.getXAxis();
        xAxis.setLabel("Customers");
        yAxis.setLabel("# of Lends");

        // Aggiungi tooltip e stile hover
        series.getData().forEach(data -> {
            Node bar = data.getNode();
            Tooltip tooltip = new Tooltip(
                    String.format("Customer: %s%n Lends: %d",
                            data.getXValue(),
                            data.getYValue().intValue())
            );
            Tooltip.install(bar, tooltip);

            // Effetto hover
            bar.setOnMouseEntered(e -> bar.setStyle("-fx-bar-fill: #ff8c00;"));
            bar.setOnMouseExited(e -> bar.setStyle(""));
        });
    }

    /**
     * Initializes the BarChart.
     */
    private void initNumusBarChart() {
        numusBarChart.setTitle("Lends Distribution by Customers");

        //configures the x and y axis
        CategoryAxis xAxis = (CategoryAxis) numusBarChart.getXAxis();
        NumberAxis yAxis = (NumberAxis) numusBarChart.getYAxis();

        xAxis.setLabel("Customers");
        yAxis.setLabel("# of Lends");

        //forces the Y axis to use only integer numbers
        yAxis.setTickUnit(1);
        yAxis.setMinorTickVisible(false);

        //rotate the labels of the x-axis for better readability
        xAxis.setTickLabelRotation(45);

        numusBarChart.setAnimated(true);
        numusBarChart.setBarGap(8);
        numusBarChart.setCategoryGap(20);

        numusBarChart.getStyleClass().add("custom-bar-chart");
    }

    /**
     * Updates the lend count label.
     */
    private void updateLendCount() {
        int lendCount = clientController.getNotReturnedLendsCount();
        labelCount.setText(String.format("%d", lendCount));
    }
}
