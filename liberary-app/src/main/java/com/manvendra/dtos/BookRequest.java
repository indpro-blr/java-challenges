package com.manvendra.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {
    private String title;
    private String description;
    private String category;
    private String author;
    private String isbn;
    private boolean available;
    private double price;
    private String publisher;
    private Integer availableQuantity;
    private Integer totalBook;
}
