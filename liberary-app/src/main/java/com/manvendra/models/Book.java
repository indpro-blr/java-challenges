package com.manvendra.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private String category;
    private String author;
    @Column(name = "isbn_code", unique = true)
    private String isbn;
    private double price;
    private String publisher;
    private Integer availableQuantity;
    private boolean available = true;
    private Integer totalBook;
}
