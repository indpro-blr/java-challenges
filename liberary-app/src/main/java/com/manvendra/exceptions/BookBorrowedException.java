package com.manvendra.exceptions;

public class BookBorrowedException extends RuntimeException {

    public BookBorrowedException() {
        super("Book can't be deleted it borrowed");
    }

    public BookBorrowedException(String message) {
        super(message);
    }
}
