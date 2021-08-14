package com.example.digitallibrary;

public class Book {

    private String title;
    private String author;
    private String year;
    private String pages;
    private String description;
    private String genres;
    private String link;
    private String imageuri;

    public Book(String title, String author, String year, String pages, String description, String genres, String link, String imageuri) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.pages = pages;
        this.description = description;
        this.genres = genres;
        this.link = link;
        this.imageuri = imageuri;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImageuri() {
        return imageuri;
    }

    public void setImageuri(String imageuri) {
        this.imageuri = imageuri;
    }
}
