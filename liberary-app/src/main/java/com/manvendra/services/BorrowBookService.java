package com.manvendra.services;

import com.manvendra.dtos.BookBorrowRequest;
import com.manvendra.dtos.BookBorrowResponse;

public interface BorrowBookService {
    BookBorrowResponse borrowBook(BookBorrowRequest borrowBook, String name);

    String returnBook(Integer id, String name);
}
