package com.manvendra.services.impl;

import com.manvendra.dtos.BookBorrowRequest;
import com.manvendra.dtos.BookBorrowResponse;
import com.manvendra.exceptions.BookBorrowedException;
import com.manvendra.exceptions.ResourceNotFoundException;
import com.manvendra.models.Book;
import com.manvendra.models.BorrowBook;
import com.manvendra.models.User;
import com.manvendra.repository.BookRepository;
import com.manvendra.repository.BorrowBookRepository;
import com.manvendra.repository.UserRepository;
import com.manvendra.services.BorrowBookService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class BorrowBookServiceImpl implements BorrowBookService {
    private final BorrowBookRepository borrowBookRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Override
    public BookBorrowResponse borrowBook(BookBorrowRequest borrowBook, String name) {
        User user = userRepository.findByUsername(name).orElseThrow(() -> new ResourceNotFoundException("User not available"));

        if(user!=null){
            LocalDate fine = user.getFine();

            if (fine != null && fine.isBefore(LocalDate.now())) {
                user.setBorrowRights(true);
                user.setFine(null);
                userRepository.save(user);
            }
        }

        if (user != null && user.isBorrowRights()) {
            Book book = bookRepository.findById(borrowBook.getBookId()).orElseThrow(() -> new ResourceNotFoundException("Book not available"));
            if (book.getAvailableQuantity() >= 1) {
                BorrowBook borrowedBook = new BorrowBook();
                borrowedBook.setBook(book);
                borrowedBook.setUser(user);
                borrowedBook.setBorrowDate(LocalDate.now());
                borrowedBook.setDueDate(borrowBook.getDueDate());
                BorrowBook savedBook = borrowBookRepository.save(borrowedBook);
                int availableBook = book.getAvailableQuantity() - 1;
                book.setAvailableQuantity(availableBook);
                book.setAvailable(book.getTotalBook() > availableBook);
                bookRepository.save(book);
                return modelMapper.map(savedBook, BookBorrowResponse.class);
            } else {
                throw new BookBorrowedException("Book is not available currently");
            }
        } else {
            throw new BookBorrowedException("User doesn't have borrow access");
        }
    }

    @Override
    public String returnBook(Integer id, String name) {
        User user = userRepository.findByUsername(name).orElseThrow(() -> new ResourceNotFoundException("User not available"));
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not available"));
        List<BorrowBook> collect = user.getBorrowBooks().stream().filter(borrowBook -> (Objects.equals(borrowBook.getBook().getId(), book.getId())) &&
                borrowBook.getReturnDate() == null).toList();
        BorrowBook borrowBook = collect.get(0);
        if (borrowBook.getDueDate().isAfter(LocalDate.now())) {
            borrowBook.setReturnDate(LocalDate.now());
            borrowBookRepository.save(borrowBook);
            return "Book submitted";
        } else {
            long days = ChronoUnit.DAYS.between(LocalDate.now(), borrowBook.getDueDate());
            borrowBook.setReturnDate(LocalDate.now());
            user.setFine(LocalDate.now().plusDays(days));
            user.setBorrowRights(false);
            userRepository.save(user);
            borrowBookRepository.save(borrowBook);
            return "Book submitted with fine of " + days + " days of ban";
        }
    }
}
