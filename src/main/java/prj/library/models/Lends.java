package prj.library.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class Lends implements Serializable {
    private int id;
    private int bookId;
    private int customerId;
    private LocalDate returnDate;


    public Lends(int bookId, int customerId, LocalDate returnDate) {
        this.bookId = bookId;
        this.customerId = customerId;
        this.returnDate = returnDate;
    }

    public Lends(Book book, Date returnDate) {
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

    @Override
    public String toString() {
        return "Lends{" +
                "id=" + id +
                ", bookId=" + bookId +
                ", customerId=" + customerId +
                ", returnDate='" + returnDate +
                '}';
    }
}
