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
import java.util.Optional;


@Controller
@RequestMapping("user")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("add")
    public String displayAddBookForm(Model model) {
        model.addAttribute(new Book());
        return "book/add";
    }

    @PostMapping("add")
    public String processAddBookForm(@ModelAttribute @Valid Book newBook,
                                    Errors errors, Model model, @RequestParam String title, @RequestParam String author, @RequestParam String isbn,
                                     @RequestParam String pages, @RequestParam String genre, @RequestParam String status, @RequestParam String rating, @RequestParam String description) {
        if (errors.hasErrors()) {
            return "/add";
        }

        DateFormat Date = DateFormat.getDateInstance();
        Calendar cals = Calendar.getInstance();
        String currentDate = Date.format(cals.getTime());
        model.addAttribute("date", currentDate);

        newBook.setDate(currentDate);

        bookRepository.save(newBook);

        return "redirect:shelf";
    }

    @GetMapping("delete")
    public String displayDeleteBooksForm(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "book/delete";
    }

    @PostMapping("delete")
    public String processDeleteBooksForm(@RequestParam(required = false) int[] bookIds) {
        if (bookIds != null) {
            for (int id : bookIds) {
                bookRepository.deleteById(id);
            }
        }
        return "redirect:shelf";
    }

    @GetMapping("edit/{bookId}")
    public String displayEditForm(Model model, @PathVariable Integer bookId) {
        Optional optBook = bookRepository.findById(bookId);
        if (optBook.isPresent()) {
            Book book = (Book) optBook.get();
            model.addAttribute("book", book);
            return "book/edit";
        } else {
            return "redirect:../";
        }
    }

    @PostMapping("edit")
    public String processEditForm(Model model, @RequestParam Integer bookId, String title, String author, String isbn,
                                  String pages, String genre, String status, String rating) {
        Optional optBook = bookRepository.findById(bookId);
        System.out.println(optBook);
        if (optBook.isPresent()) {
            Book book = (Book) optBook.get();
            model.addAttribute("book", book);
            book.setTitle(title);
            book.setAuthor(author);
            book.setIsbn(isbn);
            book.setPages(pages);
            book.setGenre(genre);
            book.setStatus(status);
            book.setRating(rating);
            book.setDate(book.getDate());
            bookRepository.save(book);
            return "redirect:shelf";
        } else {
            return "redirect:";
        }
    }

    @RequestMapping("shelf")
    public String displayBookshelf(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "book/shelf";
    }

    @GetMapping("view/{bookId}")
    public String displayViewBook(Model model, @PathVariable (required = false) Integer bookId) {
        Optional<Book> optBook = bookRepository.findById(bookId);
        if (optBook.isPresent()) {
            Book book = (Book) optBook.get();
            model.addAttribute("book", book);
            return "book/view";
        } else {
            return "redirect:../shelf";
        }
    }


}
