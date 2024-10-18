package prj.library.models;

import java.util.Date;

public class Lends {
    private int id;
    private int bookId;
    private Date returnDate;

    public Lends(int id, int bookId, Date returnDate) {
        this.id = id;
        this.bookId = bookId;
        this.returnDate = returnDate;
    }

    public Lends(Book book, Date returnDate) {
    }

    public int getId() {
        return id;
    }

    public int getBookId() {
        return bookId;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "Lends{" +
                "id=" + id +
                ", bookId=" + bookId +
                ", returnDate='" + returnDate + '\'' +
                '}';
    }
}
