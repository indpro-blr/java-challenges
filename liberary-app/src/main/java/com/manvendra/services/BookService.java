package com.manvendra.services;


import com.manvendra.dtos.BookRequest;
import com.manvendra.dtos.BookResponse;
import com.manvendra.models.Book;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface BookService {

    BookResponse createBook(BookRequest bookRequest);
    BookResponse updateBook(BookRequest bookRequest,Integer id);

    BookResponse getBook(Integer id);

    List<BookResponse> getAllBook(Specification<Book> specification);

    boolean isBorrowed(Integer id);

    void deleteBook(Integer id);
}
