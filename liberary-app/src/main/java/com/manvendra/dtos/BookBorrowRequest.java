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
public class BookBorrowRequest {
    private Integer bookId;
    private Integer userId;
    private LocalDate dueDate;
}
