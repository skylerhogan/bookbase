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

    List<Book> findAllByOrderByIdAsc();

    List<Book> findAllByOrderByDateViewedDesc();

   /* @Query(value = "SELECT * FROM BOOK WHERE STATUS = ?1", nativeQuery = true)
    List<Book> findByStatus(String status);
*/
    @Query(value = "SELECT * FROM BOOK WHERE STATUS = ?1", nativeQuery = true)
    List<Book> findByStatus(String status);


    List<Book> findByStatusIgnoreCaseOrderByDateViewedDesc(String status);


}