package com.manvendra.repository;

import com.manvendra.models.BorrowBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowBookRepository extends JpaRepository<BorrowBook,Integer> {
}
