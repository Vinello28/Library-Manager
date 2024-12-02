package prj.library.notification;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Class to represent a virtual lend
 */
public class VirtualLend implements Serializable{

    private String bookTitle;
    private String customerEmail;
    private String customerName;
    private LocalDate returnDate;

    /**
     * Constructor
     * @param bookTitle the title of the book
     * @param customerEmail the email of the customer
     * @param customerName the name of the customer
     * @param returnDate the due date of the book
     */
    public VirtualLend(String bookTitle, String customerEmail, String customerName, LocalDate returnDate) {
        this.bookTitle = bookTitle;
        this.customerEmail = customerEmail;
        this.customerName = customerName;
        this.returnDate = returnDate;
    }

    /**
     * get the title of the book
     * @return the title of the book
     */
    public String getBookTitle() {
        return bookTitle;
    }

    /**
     * get the email of the customer
     * @return the email of the customer
     */
    public String getCustomerEmail() {
        return customerEmail;
    }

    /**
     * get the name of the customer
     * @return the name of the customer
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * get the return date of the book
     * @return the return date of the book
     */
    public LocalDate getReturnDate() {
        return returnDate;
    }

    /**
     * set the title of the book
     * @param bookTitle the title of the book
     */
    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    /**
     * set the email of the customer
     * @param customerEmail the email of the customer
     */
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    /**
     * set the name of the customer
     * @param customerName the name of the customer
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * set the return date of the book
     * @param returnDate the return date of the book
     */
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "VirtualLend{" +
                "bookTitle='" + bookTitle + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", customerName='" + customerName + '\'' +
                ", dueDate='" + returnDate + '\'' +
                '}';
    }
}
