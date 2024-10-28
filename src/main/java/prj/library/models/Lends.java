package prj.library.models;

import java.io.Serializable;
import java.util.Date;

public class Lends implements Serializable {
    private int id;
    private int bookId;
    private Date returnDate;
    private String surname;
    private String cellphone;


    public Lends(int bookId, Date returnDate, String surname, String cellphone) {
        this.bookId = bookId;
        this.returnDate = returnDate;
    }

    public Lends(Book book, Date returnDate) {
    }

    /**
     * Checks if the return date is before the current date
     * @return true if the return date is before the current date
     */
    public boolean isLate() {
        return returnDate.before(new Date());
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }


    @Override
    public String toString() {
        return "Lends{" +
                "id=" + id +
                ", bookId=" + bookId +
                ", returnDate='" + returnDate +
                ", surname='" + surname +
                ", cellphone='" + cellphone + '\'' +
                '}';
    }
}
