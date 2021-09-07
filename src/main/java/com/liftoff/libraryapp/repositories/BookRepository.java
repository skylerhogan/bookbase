package com.liftoff.libraryapp.repositories;

import com.liftoff.libraryapp.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    @Query(value = "SELECT SUM(pages) FROM Book WHERE STATUS = 'Completed'", nativeQuery = true)
    Integer selectTotalPagesRead();

    @Query(value = "SELECT COUNT(*) FROM Book", nativeQuery = true)
    Integer selectTotalBooksInLibrary();

    @Query(value = "SELECT COUNT(*) FROM Book WHERE STATUS = 'Completed'", nativeQuery = true)
    Integer selectTotalBooksRead();

    @Query(value= "SELECT COUNT(`genre`) AS `value_occurrence` FROM Book GROUP BY `genre` ORDER BY `value_occurrence` DESC LIMIT 1", nativeQuery = true)
    String selectFavoriteGenre();






}