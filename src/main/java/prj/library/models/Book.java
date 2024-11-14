package prj.library.models;

import java.io.Serializable;

public class Book implements Serializable {

    private int id;
    private String title;
    private String author;
    private int year;
    private Genre genre;
    private int copies;

    public Book() {
    }

    public Book(String title, String author, int year, Genre genre, int copies) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.genre = genre;
        this.copies = copies;
    }

    public Book(int id, String title, String author, int year, Genre genre, int copies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.genre = genre;
        this.copies = copies;
    }

    // Constructor without copies used for searching purposes
    public Book(String title, String author, int year, Genre genre) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public void setAvailable(){
        copies++;
    }

    @Override
    public String toString() {
        if (genre == null) genre = Genre.Genre;

        return " " + "ID:" + id + ", " + title + ", " + author + ", " + year + ", " + genre.toString() + ", copies: " + copies + " ";
    }

}
