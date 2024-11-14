package prj.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import prj.library.models.Book;
import prj.library.models.Customer;
import prj.library.models.Genre;
import prj.library.models.Lends;
import javafx.scene.layout.GridPane;
import prj.library.networking.ClientController;
import prj.library.networking.messages.Operation;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


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
    private TextField titleLendTextField;

    @FXML
    private ListView<Lends> searchedLendListView;

    @FXML
    private Tab lendopsTab;

    @FXML
    private Button addCustomerButton;

    @FXML
    private Button revertCustomerButton;

    @FXML
    private TextField customerNameTextField;

    @FXML
    private TextField customerPhoneTextField;

    @FXML
    private TextField customerEmailTextField;

    @FXML
    private TextField customerAddressTextField;

    @FXML
    private ListView<Customer> customerListView;

    @FXML
    private DatePicker lendSearchDatePicker;

    @FXML
    private TextField cellLendTextField;



    private ClientController clientController;

    public LibraryController() {
        try {
            clientController = new ClientController();
        } catch (IOException e) {
            clientController.close();
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void initialize() {
        loadBooks();
        //loadLends();  TODO: remove this.
        fillChoiceBoxes();
        loadCustomers();

        //Add listener to the ListView
        customerListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                //Update TextFields with selected customer data
                customerNameTextField.setText(newValue.getName());
                customerPhoneTextField.setText(newValue.getPhone());
                customerEmailTextField.setText(newValue.getEmail());
                customerAddressTextField.setText(newValue.getAddress());

                //Change button text to "Update"
                addCustomerButton.setText("Update");
            }
        });
    }

    @FXML
    protected void onAddButtonClick() {
        String title = titleTextField.getText();
        String author = authorTextField.getText();
        int year = Integer.parseInt(yearTextField.getText());
        Genre genre = (Genre) genreChoiceBox.getValue();
        int copies = Integer.parseInt(copiesTextField.getText());

        Book book = new Book(title, author, year, genre, copies);
        clientController.createBook(book);
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

        System.out.println("CLIENT | DEBUG INFO:  searching by " + choice);
        Book tmp = new Book(title, author, year, genre);
        books = clientController.searchBooksBy(choice, tmp);
        bookCheck_N_View(books, searchedListView);

        clearBookSearch();
    }

    /**
     * Edits the selected book from the list view of searched books.
     */
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
            clientController.updateBook(editedBook);
            loadBooks();
        });
    }

    /**
     * Deletes the selected books from the list view of searched books.
     */
    @FXML
    protected void onDeleteButtonClick() {
        Book selectedBook = (Book) searchedListView.getSelectionModel().getSelectedItem();
        clientController.deleteBook(selectedBook.getId());
        loadBooks();
    }

    /**
     * Deletes the selected lends from the list view of searched lends.
     */
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
        ChoiceBox<Customer> customerChoiceBox = new ChoiceBox<>();
        customerChoiceBox.getItems().addAll(clientController.getCustomers());
        DatePicker returnDatePicker = new DatePicker();
        Label booktitleLabel = new Label(selectedBook.getTitle());

        System.out.println("CLIENT | DEBUG INFO:  editing this book " + selectedBook);

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

                selectedBook.setCopies(selectedBook.getCopies()-1);
                return selectedBook;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(editedBook -> {
            lend[0] = new Lends(selectedBook.getId(), customerChoiceBox.getValue().getId(),returnDatePicker.getValue());
            clientController.createLend(lend[0]);
            clientController.updateBook(editedBook);
            loadBooks();
            //loadLends();
        });
    }

    /**
     * Loads the books from the server and displays them in the list view.
     */
    private void loadBooks() {
        List<Book> books = clientController.getBooks();
        bookCheck_N_View(books, bookListView);
    }

    /**
     * Loads the lends from the server and displays them in the list view.
     */
    private void loadLends() {
        List<Lends> lends = clientController.getLends();
        lendCheck_N_View(lends, searchedLendListView);
    }


    @FXML
    private void onLateSearchButtonClick() {
        List<Lends> lends = clientController.getLends();
        List<Lends> lateLends = lends.stream().filter(Lends::isLate).collect(Collectors.toList());
        lendCheck_N_View(lateLends, searchedLendListView);
    }


    /**
     * Checks if the list of books is empty and adds a message to the list view.
     * @param books the list of books to check
     * @param searchedListView the list view to add the message to
     * @return true if the list is empty, false otherwise
     */
    private void bookCheck_N_View(List<Book> books, ListView searchedListView) {
        if(books==null || books.isEmpty()) showErrorDialog("Error", "No books found", "Books you are looking for are not found. Try something else.");
        else {
            ObservableList<Book> bookTitles = FXCollections.observableArrayList();
            bookTitles.addAll(books);
            searchedListView.setItems(bookTitles);
        }
    }

    /**
     * Checks if the list of lends is empty and adds a message to the list view.
     * @param lends the list of lends to check
     * @param searchedLendListView the list view to add the message to
     * @return true if the list is empty, false otherwise
     */
    private void lendCheck_N_View(List<Lends> lends, ListView searchedLendListView) {

        if(lends==null || lends.isEmpty()) showErrorDialog("Error", "No lends found", "Lends you are looking for are not found. Try something else.");
        else{
            ObservableList<Lends> lendTitles = FXCollections.observableArrayList();

            System.out.println("CLIENT | DEBUG_INFO: " + lends);

            lendTitles.addAll(lends);
            searchedLendListView.setItems(lendTitles);
        }
    }

    /**
     * Clears the search fields.
     */
    private void clearBookSearch() {
        titleSearchTextField.clear();
        authorSearchTextField.clear();
        yearSearchTextField.clear();
        copiesTextField.clear();
        genreSearchChoiceBox.setValue(null);
    }

    /**
     * Fills the choice boxes with the available genres.
     */
    private void fillChoiceBoxes() {
        genreChoiceBox.getItems().addAll(Genre.values());
        genreSearchChoiceBox.getItems().addAll(Genre.values());
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
     * When the search lend button is clicked, the lends are searched and displayed in the list view.
     */
    @FXML
    public void onSearchLendButtonClick(){
        //TODO: implement search lends, that can bu performed using books title or customer email or between dates
        String title = titleLendTextField.getText();
        LocalDate date = lendSearchDatePicker.getValue();
        String cell = cellLendTextField.getText();
        int choice;
        if(!title.isEmpty() && date != null && !cell.isEmpty()) choice = 0;
        else if(!title.isEmpty() && date != null && cell.isEmpty()) choice = 1;
        else if(title.isEmpty() && date != null && cell.isEmpty()) choice = 2;
        else if(title.isEmpty() && date != null && !cell.isEmpty()) choice = 3;
        else choice = 4;

        List<Lends> lends = clientController.searchLendsBy(choice, title, date, cell);
        lendCheck_N_View(lends, searchedLendListView);
    }

    @FXML
    public void onAddCustomerButtonClick() {
        Customer selectedCustomer = customerListView.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            updateCustomer(selectedCustomer);
        } else {
            addCustomer();
        }
        onRevertCustomerButtonClick();
        loadCustomers();
    }

    private void updateCustomer(Customer customer) {
        customer.setName(customerNameTextField.getText());
        customer.setPhone(customerPhoneTextField.getText());
        customer.setEmail(customerEmailTextField.getText());
        customer.setAddress(customerAddressTextField.getText());

        clientController.updateCustomer(customer);
    }

    private void addCustomer() {
        Customer newCustomer = new Customer(
                customerNameTextField.getText(),
                customerEmailTextField.getText(),
                customerPhoneTextField.getText(),
                customerAddressTextField.getText()
        );
        clientController.createCustomer(newCustomer);
    }

    private void loadCustomers() {
        List<Customer> customers = clientController.getCustomers();
        if (customers == null || customers.isEmpty()) {
            showErrorDialog("Error", "No customers found", "Customers you are looking for are not found. Try something else.");
            return;
        }
        ObservableList<Customer> customerNames = FXCollections.observableArrayList();
        customerNames.addAll(customers);
        customerListView.setItems(customerNames);
    }

    public void onRevertCustomerButtonClick() {
        customerNameTextField.clear();
        customerPhoneTextField.clear();
        customerEmailTextField.clear();
        customerAddressTextField.clear();
        addCustomerButton.setText("Add");
    }

    public void onDeleteLendButtonClick() {
        Lends selectedLend = (Lends) searchedLendListView.getSelectionModel().getSelectedItem();
        System.out.println("CLIENT | DEBUG INFO:  deleting this lend " + selectedLend);
        clientController.deleteLend(selectedLend);
        loadBooks();
        loadLends(); //TODO: necessary?
    }

    public void onEditLendButtonClick() {
        Lends selectedLend = (Lends) searchedLendListView.getSelectionModel().getSelectedItem();
        if (selectedLend == null) {
            System.out.println("No lend selected for editing.");
            return;
        }

        Dialog<Lends> dialog = new Dialog<>();
        dialog.setTitle("Edit Lend");

        // Set the button types.
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        //Create the fields and populate with current lend data.
        Label customerLabel = new Label(clientController.readCustomer(selectedLend.getCustomerId()).getEmail());
        DatePicker returnDatePicker = new DatePicker(selectedLend.getReturnDate());
        Label booktitleLabel = new Label(clientController.readBook(selectedLend.getBookId()).getTitle());

        System.out.println("CLIENT | DEBUG INFO:  editing this lend " + selectedLend);

        // Create a grid pane and add the fields
        GridPane grid = new GridPane();
        grid.add(new Label("Customer's email:"), 0, 0);
        grid.add(customerLabel, 1, 0);
        grid.add(new Label("Book's title:"), 0, 1);
        grid.add(booktitleLabel, 1, 1);
        grid.add(new Label("Return date:"), 0, 2);
        grid.add(returnDatePicker, 1, 2);

        dialog.getDialogPane().setContent(grid);

        // Convert the result to a Lends object when the save button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                selectedLend.setReturnDate(returnDatePicker.getValue());
                return selectedLend;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(editedLend -> {
            clientController.updateLend(editedLend);
            //loadBooks(); //TODO: development purposes
            loadLends(); //TODO: necessary?
        });
    }
}