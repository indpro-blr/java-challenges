package com.manvendra.services.impl;

import com.manvendra.dtos.BookRequest;
import com.manvendra.dtos.BookResponse;
import com.manvendra.exceptions.BookBorrowedException;
import com.manvendra.exceptions.ResourceNotFoundException;
import com.manvendra.models.Book;
import com.manvendra.repository.BookRepository;
import com.manvendra.services.BookService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Override
    public BookResponse createBook(BookRequest bookRequest) {
        Book book = modelMapper.map(bookRequest, Book.class);
        book.setAvailable(true);
        Book savedBook = bookRepository.save(book);
        return modelMapper.map(savedBook, BookResponse.class);
    }

    @Override
    public BookResponse updateBook(BookRequest bookRequest, Integer id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book with id: " + id + " not found"));
        book.setTitle(bookRequest.getTitle());
        book.setDescription(bookRequest.getDescription());
        book.setCategory(bookRequest.getCategory());
        book.setAuthor(bookRequest.getAuthor());
        book.setPrice(bookRequest.getPrice());
        book.setAvailable(bookRequest.isAvailable());
        book.setAvailableQuantity(bookRequest.getAvailableQuantity());
        Book updatedBook = bookRepository.save(book);
        return modelMapper.map(updatedBook, BookResponse.class);
    }

    @Override
    public BookResponse getBook(Integer id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book with id: " + id + " not found"));

        return modelMapper.map(book, BookResponse.class);
    }

    @Override
    public List<BookResponse> getAllBook(Specification<Book> specification) {
        List<Book> books = bookRepository.findAll(specification);
        return books.stream().map(book -> modelMapper.map(book, BookResponse.class)).collect(Collectors.toList());
    }

    @Override
    public boolean isBorrowed(Integer id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        return !book.isAvailable();
    }

    @Override
    public void deleteBook(Integer id) {
        if (isBorrowed(id)) {
            throw new BookBorrowedException("Cannot delete a borrowed book");
        }
        bookRepository.deleteById(id);
    }
}
