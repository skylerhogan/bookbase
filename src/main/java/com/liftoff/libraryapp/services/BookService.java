package com.liftoff.libraryapp.services;
import com.liftoff.libraryapp.models.Book;
import com.liftoff.libraryapp.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository repo;

    @Transactional
    public Optional<Book> update(Integer bookId, Book source) {
        return repo.findById(bookId).map(target -> {
            target.setTitle(source.getTitle());
            target.setIsbn(source.getIsbn());
            target.setRating(source.getRating());
            // update other props...
            return target;
        });
    }
}