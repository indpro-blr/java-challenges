package com.manvendra.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookBorrowResponse {

    private Integer id;

    private BookResponse book;

    private UserResponse user;

    private LocalDate borrowDate;

    private LocalDate dueDate;

    private LocalDate returnDate;
}
