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
import prj.library.utils.CLIUtils;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class LibraryController {

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField authorTextField;

    @FXML
    private TextField yearTextField;

    @FXML
    private ChoiceBox<Genre> genreChoiceBox;

    @FXML
    private TextField titleSearchTextField;

    @FXML
    private ChoiceBox<Genre> genreSearchChoiceBox;

    @FXML
    private TextField authorSearchTextField;

    @FXML
    private TextField yearSearchTextField;

    @FXML
    private ListView<Book> searchedListView;

    @FXML
    private ListView<Book> bookListView;

    @FXML
    private TextField copiesTextField;

    @FXML
    private TextField titleLendTextField;

    @FXML
    private ListView<Lends> searchedLendListView;

    @FXML
    private Button addCustomerButton;

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

    @FXML
    private RadioButton returnedRadioButton;

    @FXML
    private RadioButton notReturnedRadioButton;


    private ClientController clientController;

    public LibraryController() {
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
        loadBooks();
        fillChoiceBoxes();
        //loadCustomers(); TODO remove??

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
        int year = 0;
        if (!yearTextField.getText().isEmpty()) year = Integer.parseInt(yearTextField.getText());
        Genre genre = genreChoiceBox.getValue();
        int copies=0;
        if(!copiesTextField.getText().isEmpty()) copies = Integer.parseInt(copiesTextField.getText());

        if(title.isEmpty() || author.isEmpty() || year == 0 || genre == null || copies == 0) {
            showErrorDialog("Error", "Invalid input", "Please fill all fields.");
            return;
        }

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
        Genre genre = genreSearchChoiceBox.getValue();
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
        bookCheck_N_View(books, searchedListView);

        clearBookSearch();
    }

    /**
     * Edits the selected book from the list view of searched books.
     */
    @FXML
    protected void onEditButtonClick() {

        Book selectedBook = searchedListView.getSelectionModel().getSelectedItem();
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
        Book selectedBook = searchedListView.getSelectionModel().getSelectedItem();
        if (selectedBook == null) {
            CLIUtils.clientInfo("No book selected for editing.");
            showErrorDialog("Error", "No book selected", "Please select a book to delete.");
            return;
        }
        clientController.deleteBook(selectedBook.getId());
        loadBooks();
    }

    /**
     * Deletes the selected lends from the list view of searched lends.
     */
    @FXML
    protected void onLendButtonClick() {
        Book selectedBook = searchedListView.getSelectionModel().getSelectedItem();
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
        refreshedBook.setCopies(refreshedBook.getCopies()-1);
        clientController.updateBook(refreshedBook);

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

        CLIUtils.clientDebug("editing this book " + selectedBook); //TODO: remove

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
                if(selectedBook.getCopies() == refreshedBook.getCopies()+1) CLIUtils.clientDebug("OKOK"); //TODO: remove
                return selectedBook;
            } else {
                refreshedBook.setCopies(refreshedBook.getCopies()+1);
                clientController.updateBook(refreshedBook);
                return null;
            }
        });

        dialog.showAndWait().ifPresent(editedBook -> {
            lend[0] = new Lends(selectedBook.getId(), customerChoiceBox.getValue().getId(),returnDatePicker.getValue(), false);
            clientController.createLend(lend[0]);
            clientController.updateBook(editedBook);
            loadBooks();
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
        List<Lends> lends = clientController.searchLateLends();
        lendSearchClear();
        lendCheck_N_View(lends, searchedLendListView);
    }

    /**
     * Checks if the list of books is empty and adds a message to the list view.
     * @param books the list of books to check
     * @param searchedListView the list view to add the message to
     */
    private void bookCheck_N_View(List<Book> books, ListView<Book> searchedListView) {
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
     */
    private void lendCheck_N_View(List<Lends> lends, ListView<Lends> searchedLendListView) {

        if(lends==null || lends.isEmpty()) showErrorDialog("Error", "No lends found", "Lends you are looking for are not found. Try something else.");
        else{
            ObservableList<Lends> lendTitles = FXCollections.observableArrayList();

            CLIUtils.clientInfo("CLIENT | DEBUG_INFO: " + lends);

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
        searchedLendListView.getItems().clear();

        String title = titleLendTextField.getText();
        LocalDate date = lendSearchDatePicker.getValue();
        String cell = cellLendTextField.getText();
        int b_id=0; //book id
        int c_id=0; //customer id
        boolean returned;
        boolean sentinel = false;

        if(returnedRadioButton.isSelected()) returned = true;
        else if (notReturnedRadioButton.isSelected()) {
            returned = false;
            sentinel = true;
        } else returned = false;

        //checks if book and customer exists
        if (!title.isEmpty()) {
            List<Book> books = clientController.searchBooksBy(1, new Book(0, title, "", 0, Genre.Genre, 0));
            if (books == null || books.isEmpty()) {
                showErrorDialog("Error", "Book not found", "Book you are looking for is not found. Try something else.");
                lendSearchClear();
                return;
            }
            b_id = books.get(0).getId();
        }
        if(!cell.isEmpty()) {
            List<Customer> customers = clientController.searchCustomersBy(2, new Customer("", "", cell, ""));
            if (customers == null || customers.isEmpty()) {
                showErrorDialog("Error", "Customer not found", "Customer you are looking for is not found. Try something else.");
                lendSearchClear();
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


        CLIUtils.clientDebug("Searching lends by " + choice);

        List<Lends> lends = clientController.searchLendsBy(choice, b_id, date, c_id, returned, sentinel);
        lendCheck_N_View(lends, searchedLendListView);
        lendSearchClear();
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
        Lends selectedLend = searchedLendListView.getSelectionModel().getSelectedItem();
        System.out.println("CLIENT | DEBUG INFO:  deleting this lend " + selectedLend);
        clientController.deleteLend(selectedLend);
        loadBooks();
    }

    public void onEditLendButtonClick() {
        Lends selectedLend = searchedLendListView.getSelectionModel().getSelectedItem();
        if (selectedLend == null) {
            CLIUtils.clientInfo("No lend selected for editing.");
            return;
        }

        Dialog<Lends> dialog = new Dialog<>();
        dialog.setTitle("Edit Lend");

        //set the button types.
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        //create the fields and populate with current lend data.
        Label customerLabel = new Label(clientController.readCustomer(selectedLend.getCustomerId()).getEmail());
        DatePicker returnDatePicker = new DatePicker(selectedLend.getReturnDate());
        Label booktitleLabel = new Label(clientController.readBook(selectedLend.getBookId()).getTitle());
        ChoiceBox<Boolean> returnedChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(true, false)); //fill choice box with true and false
        returnedChoiceBox.setValue(selectedLend.isReturned()); //set true if it's returned

        CLIUtils.clientDebug("editing this lend " + selectedLend); //TODO: remove

        //create a grid pane and add the fields
        GridPane grid = new GridPane();
        grid.add(new Label("Customer's email:"), 0, 0);
        grid.add(customerLabel, 1, 0);
        grid.add(new Label("Book's title:"), 0, 1);
        grid.add(booktitleLabel, 1, 1);
        grid.add(new Label("Return date:"), 0, 2);
        grid.add(returnDatePicker, 1, 2);
        grid.add(new Label("Returned:"), 0, 3);
        grid.add(returnedChoiceBox, 1, 3);


        dialog.getDialogPane().setContent(grid);

        //convert the result to a Lends object when the save button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                //selectedLend.setReturned(returnedChoiceBox.getValue());
                selectedLend.setReturnDate(returnDatePicker.getValue());
                if (!selectedLend.isReturned() && returnedChoiceBox.getValue()) {
                    Book book = clientController.readBook(selectedLend.getBookId());    //TODO: check if it works
                    if (book != null){
                        book.setCopies(book.getCopies() + 1);
                        clientController.updateBook(book);
                    }
                }
                selectedLend.setReturned(returnedChoiceBox.getValue());
                return selectedLend;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(editedLend -> {
            clientController.updateLend(editedLend);
            List<Lends> lends = new ArrayList<>();
            lends.add(clientController.readLend(editedLend));
            lendCheck_N_View(lends, searchedLendListView);
        });
    }

    public void onDeleteCustomerButtonClick() {
        Customer selectedCustomer = customerListView.getSelectionModel().getSelectedItem();
        System.out.println("CLIENT | DEBUG INFO:  deleting this customer " + selectedCustomer);
        clientController.deleteCustomer(selectedCustomer);
        loadCustomers();
    }

    public void onSearchCustomerButtonClick(){
        String name = customerNameTextField.getText();
        String phone = customerPhoneTextField.getText();
        String email = customerEmailTextField.getText();
        String address = customerAddressTextField.getText();
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
            customerListView.setItems(customerNames);
        }
    }

    public void onNotReturnedRadioButtonClick(ActionEvent actionEvent) {
        returnedRadioButton.setSelected(false);
    }

    public void onReturnedRadioButtonClick(ActionEvent actionEvent) {
        notReturnedRadioButton.setSelected(false);
    }

    /**
     * Clears the lend's search fields.
     */
    private void lendSearchClear() {
        titleLendTextField.clear();
        lendSearchDatePicker.setValue(null);
        cellLendTextField.clear();
        returnedRadioButton.setSelected(false);
        notReturnedRadioButton.setSelected(false);
    }
}