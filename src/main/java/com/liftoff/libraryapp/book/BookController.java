package com.liftoff.libraryapp.book;

import com.liftoff.libraryapp.models.Book;
import com.liftoff.libraryapp.repositories.BookRepository;
import com.liftoff.libraryapp.repositories.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

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
        return "redirect:delete";
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
    public String displayMainBookshelf(Model model, @RequestParam (required = false) String status,
                                       @RequestParam (required = false) String title,
                                       @RequestParam (required = false) String author,
                                       @RequestParam (required = false) String dateAdded,
                                       @RequestParam (required = false) String recentlyViewed,
                                       @RequestParam (required = false) String rating) {
        if (status != "") {
            model.addAttribute("books", bookRepository.findByStatusOrderByDateViewedDesc(status));
        } else {
            model.addAttribute("books", bookRepository.findAllByOrderByDateViewedDesc());
        }

        if (title != null) {
            model.addAttribute("books", bookRepository.findAllByOrderByTitle());
        } else if (author != null) {
            model.addAttribute("books", bookRepository.findAllByOrderByAuthor());
        } else if (dateAdded != null) {
            model.addAttribute("books", bookRepository.findAllByOrderByDateAddedDesc());
        } else if (recentlyViewed != null) {
            model.addAttribute("books", bookRepository.findAllByOrderByDateViewedDesc());
        } else if (rating != null) {
            model.addAttribute("books", bookRepository.findByRatingOrderByDateViewedDesc(rating));
        }
        return "book/shelf";
    }

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

    @GetMapping("profile")
    public String displayUserProfile(Model model) {
        Integer pagesRead = bookRepository.selectPagesRead();
        Integer pagesToRead = bookRepository.selectPagesToRead();;
        Integer totalBooks = bookRepository.selectTotalBooksInLibrary();
        Integer totalBooksRead = bookRepository.selectTotalBooksRead();
        String favoriteGenre = bookRepository.selectFavoriteGenre();
        String joinDate = bookRepository.selectDateOfFirstBookAdded();
        model.addAttribute("pagesRead", pagesRead);
        model.addAttribute("pagesToRead", pagesToRead);
        model.addAttribute("totalBooks", totalBooks);
        model.addAttribute("totalBooksRead", totalBooksRead);
        model.addAttribute("favoriteGenre", favoriteGenre);
        model.addAttribute("joinDate", joinDate);
        return "user/profile";
    }


}
