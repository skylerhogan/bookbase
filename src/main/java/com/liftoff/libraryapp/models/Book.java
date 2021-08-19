package com.liftoff.libraryapp.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Book {

    @SequenceGenerator(
            name = "book_sequence",
            sequenceName = "book_sequence",
            allocationSize = 1
    )

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "user_sequence"
    )

    private Long id;
    private String title;
    private String author;
    private String isbn;
    private String pages;
    private String genre;
    private String status;
    private String rating;
    private Date date;

    public Long getId() {
        return id;
    }

    public Book() {}
    
    public Book(Long id, String title, String author, String isbn,
                String pages, String genre, String status, String rating, Date date) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.pages = pages;
        this.genre = genre;
        this.status = status;
        this.rating = rating;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRating(){
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
