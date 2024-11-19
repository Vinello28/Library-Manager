package prj.library.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * Represents a lend of a book to a customer
 */
public class Lends implements Serializable {
    private int id;
    private int bookId;
    private int customerId;
    private LocalDate returnDate;
    private boolean returned;


    public Lends(int bookId, int customerId, LocalDate returnDate, boolean returned) {
        this.bookId = bookId;
        this.customerId = customerId;
        this.returnDate = returnDate;
        this.returned = returned;
    }


    /**
     * Checks if the return date is before the current date
     * @return true if the return date is before the current date
     */
    public boolean isLate() {
        return LocalDate.now().isAfter(returnDate);
    }

    public int getId() {
        return id;
    }

    public int getBookId() {
        return bookId;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    @Override
    public String toString() {
        return " " +
                "ID: " + id +
                " | book Id: " + bookId +
                " | customer Id: " + customerId +
                " | return date: " + returnDate +
                " | book returned: " + returned;
    }
}
