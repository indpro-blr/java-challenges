package com.manvendra.controllers;

import com.manvendra.dtos.BookBorrowRequest;
import com.manvendra.dtos.BookBorrowResponse;
import com.manvendra.dtos.BookRequest;
import com.manvendra.dtos.BookResponse;
import com.manvendra.models.Book;
import com.manvendra.models.BorrowBook;
import com.manvendra.services.BookService;
import com.manvendra.services.BorrowBookService;
import com.manvendra.utils.BookSpecifications;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BorrowBookService borrowBookService;

    @PostMapping("/book")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookResponse> createBook(@RequestBody BookRequest bookRequest) {
        BookResponse book = bookService.createBook(bookRequest);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }


    @PutMapping("/book/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookResponse> updateBook(@RequestBody BookRequest bookRequest, @PathVariable Integer id) {
        BookResponse book = bookService.updateBook(bookRequest, id);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @GetMapping("book/{id}")
    public ResponseEntity<BookResponse> getBook(@PathVariable Integer id) {
        BookResponse book = bookService.getBook(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping("/books")
    public ResponseEntity<List<BookResponse>> getAllBook(@RequestParam(required = false) String category,
                                                         @RequestParam(required = false) String author,
                                                         @RequestParam(required = false) String isbn,
                                                         @RequestParam(required = false) String title,
                                                         @RequestParam(required = false) String publisher) {

        Specification<Book> specification = Specification.where(null);

        if (category != null) {
            specification = specification.and(BookSpecifications.hasCategory(category));
        }
        if (author != null) {
            specification = specification.and(BookSpecifications.hasAuthor(author));
        }
        if (isbn != null) {
            specification = specification.and(BookSpecifications.hasIsbn(isbn));
        }
        if (title != null) {
            specification = specification.and(BookSpecifications.hasTitle(title));
        }
        if (publisher != null) {
            specification = specification.and(BookSpecifications.hasPublisher(publisher));
        }

        List<BookResponse> books = bookService.getAllBook(specification);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @DeleteMapping("/book/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteBook(@PathVariable Integer id) {
        bookService.deleteBook(id);
    }


//    borrow book

    @PostMapping("/book/borrow")
    public ResponseEntity<BookBorrowResponse> borrowBook(@RequestBody BookBorrowRequest borrowBook, Principal principal) {
        BookBorrowResponse borrowedBook = borrowBookService.borrowBook(borrowBook, principal.getName());

        return new ResponseEntity<>(borrowedBook, HttpStatus.CREATED);
    }

    @PostMapping("/book/return/{id}")
    public ResponseEntity<String> returnBooK(@PathVariable Integer id,Principal principal){
      String message =  borrowBookService.returnBook(id,principal.getName());
      return new ResponseEntity<>(message,HttpStatus.OK);
    }

}
