package com.manvendra.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "borrow_books")
public class BorrowBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Book book;

    @ManyToOne
    private User user;

    @JsonFormat(pattern = "dd-MM-yyy")
    private LocalDate borrowDate;

    @JsonFormat(pattern = "dd-MM-yyy")
    private LocalDate dueDate;

    @JsonFormat(pattern = "dd-MM-yyy")
    private LocalDate returnDate;
}
