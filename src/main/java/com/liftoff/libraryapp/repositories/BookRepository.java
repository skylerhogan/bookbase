package com.liftoff.libraryapp.repositories;

import com.liftoff.libraryapp.models.Book;
import com.liftoff.libraryapp.services.BookService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface BookRepository extends JpaRepository<Book, Integer> {
}