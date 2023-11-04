package com.mysite.springtraining.projectdemo.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;

public class Book {
    private int id;
    private Integer personId;

    @NotEmpty(message = "Name should not be empty")
    private String name;

    @NotEmpty(message = "Author should not be empty")
    private String author;

    @Max(value = 2024, message = "Age should be less than 2024")
    private int year;

    public Book() {
    }

    public Book(Integer personId, String name, String author, int year) {
        this.personId = personId;
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
