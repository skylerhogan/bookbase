package com.liftoff.libraryapp.book;

import com.liftoff.libraryapp.models.Book;
import com.liftoff.libraryapp.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
        return "book/add";
    }

    @PostMapping("add")
    public String processAddBookForm(@ModelAttribute @Valid Book newBook,
                                    Errors errors, Model model, @RequestParam String title, @RequestParam String author, @RequestParam String isbn,
                                     @RequestParam String pages, @RequestParam String genre, @RequestParam String status, @RequestParam String rating,
                                     @RequestParam String description, @RequestParam String userReview) {
        if (errors.hasErrors()) {
            return "/add";
        }

        DateFormat Date = DateFormat.getDateInstance();
        Calendar cals = Calendar.getInstance();
        String currentDate = Date.format(cals.getTime());
        model.addAttribute("date", currentDate);

        newBook.setDateAdded(currentDate);

        bookRepository.save(newBook);

        return "redirect:shelf";
    }

    @GetMapping("delete")
    public String displayDeleteBooksForm(Model model) {
        model.addAttribute("books", bookRepository.findAllByOrderByDateViewedDesc());
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
                                  String pages, String genre, String status, String rating, String description, String userReview) {
        Optional optBook = bookRepository.findById(bookId);
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
            book.setDescription(description);
            book.setUserReview(userReview);
            book.setDateAdded(book.getDateAdded());
            bookRepository.save(book);
            return "redirect:shelf";
        } else {
            return "redirect:";
        }
    }

    @RequestMapping("shelf")
    public String displayMainBookshelf(Model model) {
        model.addAttribute("books", bookRepository.findAllByOrderByDateViewedDesc());
        return "book/shelf";
    }

    @PostMapping("shelf")
    public String displayMainBookshelf(Model model, @RequestParam String status) {
        if (status == "") {
            model.addAttribute("books", bookRepository.findAllByOrderByDateViewedDesc());
        } else {
            model.addAttribute("books", bookRepository.findByStatus(status));
        }
        return "book/shelf";
    }

   /* @RequestMapping("shelf/want-to-read")
    public String displayWantToReadBookshelf(Model model) {
        model.addAttribute("books", bookRepository.findByStatusIgnoreCaseOrderByDateViewedDesc("want to read"));
        *//*model.addAttribute("books", bookRepository.findAllByOrderByDateViewedDesc());*//*
        return "book/shelf";
    }

    @RequestMapping("shelf/currently-reading")
    public String displayCurrentlyReadingBookshelf(Model model) {
        model.addAttribute("books", bookRepository.findByStatusIgnoreCaseOrderByDateViewedDesc("currently reading"));
        *//*model.addAttribute("books", bookRepository.findAllByOrderByDateViewedDesc());*//*
        return "book/shelf";
    }

    @RequestMapping("shelf/completed")
    public String displayCompletedBookshelf(Model model) {
        model.addAttribute("books", bookRepository.findByStatusIgnoreCaseOrderByDateViewedDesc("completed"));
        *//*model.addAttribute("books", bookRepository.findAllByOrderByDateViewedDesc());*//*
        return "book/shelf";
    }*/

    @GetMapping("view/{bookId}")
    public String displayViewBook(Model model, @PathVariable (required = false) Integer bookId) {
        Optional<Book> optBook = bookRepository.findById(bookId);
        if (optBook.isPresent()) {
            Book book = (Book) optBook.get();
            model.addAttribute("book", book);
            Date date = new Date();
            Timestamp ts = new Timestamp(date.getTime());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH::mm:ss");
            String dateViewed = formatter.format(ts);
            model.addAttribute("dateViewed", dateViewed);
            book.setDateViewed(dateViewed);
            bookRepository.save(book);
            return "book/view";
        } else {
            return "redirect:../shelf";
        }
    }


}
