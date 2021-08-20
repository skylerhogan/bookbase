package com.liftoff.libraryapp.book;

import com.liftoff.libraryapp.models.Book;
import com.liftoff.libraryapp.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;


@Controller
@RequestMapping("user")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("add")
    public String displayAddBookForm(Model model) {
        model.addAttribute(new Book());
        return "/add";
    }

    @PostMapping("add")
    public String processAddBookForm(@ModelAttribute @Valid Book newBook,
                                    Errors errors, Model model, @RequestParam String title, @RequestParam String author, @RequestParam String isbn,
                                     @RequestParam String pages, @RequestParam String genre, @RequestParam String status, @RequestParam String rating) {
        if (errors.hasErrors()) {
            return "/add";
        }

        model.addAttribute("title", title);
        model.addAttribute("author", author);
        model.addAttribute("isbn", isbn);
        model.addAttribute("pages", pages);
        model.addAttribute("genre", genre);
        model.addAttribute("status", status);
        model.addAttribute("rating", rating);

        DateFormat Date = DateFormat.getDateInstance();
        Calendar cals = Calendar.getInstance();
        String currentDate = Date.format(cals.getTime());
        model.addAttribute("date", currentDate);

        newBook.setTitle(title);
        newBook.setAuthor(author);
        newBook.setIsbn(isbn);
        newBook.setPages(pages);
        newBook.setGenre(genre);
        newBook.setStatus(status);
        newBook.setRating(rating);
        newBook.setDate(currentDate);

        bookRepository.save(newBook);

        return "redirect:add";
    }

    @RequestMapping("shelf")
    public String displayBookshelf(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "/shelf";
    }

    @GetMapping("view/{bookId}")
    public String displayViewBook(Model model, @PathVariable int bookId) {
        Optional<Book> optBook = bookRepository.findById(bookId);
        if (optBook.isPresent()) {
            Book book = (Book) optBook.get();
            model.addAttribute("book", book);
            return "/view";
        } else {
            return "redirect:../shelf";
        }
    }
}
