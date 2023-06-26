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
public class BookResponse {
    private Integer id;
    private String title;
    private String description;
    private String category;
    private String author;
    private String isbn;
    private double price;
    private boolean available;
    private String publisher;
    private Integer availableQuantity;
}
