package br.edu.ifpr.foz.ifprstore.models;

import java.time.LocalDate;

public class Book {

    private Integer id;
    private String name;
    private LocalDate date;
    private Author author;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {return date;}

    public void setDate(LocalDate date) {this.date = date;}

    public Author getAuthor() {return author;}

    public void setAuthor(Author author) {this.author = author;}

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name +
                ", date=" + date + '\'' +
        '}';
    }

}
