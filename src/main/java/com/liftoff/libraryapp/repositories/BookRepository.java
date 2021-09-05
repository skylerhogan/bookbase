package com.liftoff.libraryapp.repositories;

import com.liftoff.libraryapp.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findAllByOrderByDateViewedDesc();

    List<Book> findByStatusOrderByDateViewedDesc(String status);

    List<Book> findByRatingOrderByDateViewedDesc(String rating);

    List<Book> findAllByOrderByAuthor();

    List<Book> findAllByOrderByTitle();

    List<Book> findAllByOrderByDateAddedDesc();






}