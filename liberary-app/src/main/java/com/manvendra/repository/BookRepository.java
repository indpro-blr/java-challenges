package com.manvendra.repository;

import com.manvendra.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookRepository extends JpaRepository<Book,Integer>, JpaSpecificationExecutor<Book> {
}
