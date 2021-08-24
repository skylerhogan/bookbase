package com.liftoff.libraryapp.search;

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

@Controller
@RequestMapping("user")
public class SearchController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("search")
    public String displaySearchForm() {
        return "search/search";
    }

    @PostMapping("search")
    public String processSearchForm(Model model, String searchQuery, String searchParameter) {
        String query = "";
        searchQuery = searchQuery.toLowerCase().replace(' ', '+');
        if (!searchParameter.equals("all")) {
            searchParameter += ':';
            query += "q=" + searchParameter + searchQuery;
        } else {
            query += "q=" + searchQuery;
        }
        int pageNumber = 1;
        String currentPage = "&page=" + pageNumber;

        return "redirect:search/results/" + query + currentPage;
    }

    @GetMapping("search/results/{query}&page={currentPage}")
    public String displaySearchResults(Model model, @PathVariable String query, @PathVariable int currentPage) {
        model.addAttribute("query", query);
        model.addAttribute("pageNumber", currentPage);
        model.addAttribute("startIndex", (currentPage-1) * 10);
        return "search/results";
    }

    @GetMapping("search/results/view/{bookId}")
    public String displayViewBook(Model model, @PathVariable String bookId) {
        model.addAttribute("bookId", bookId);
        model.addAttribute(new Book());
        return "search/view";
    }

    @PostMapping("search/results/view/{bookId}")
    public String processAddBook(@ModelAttribute @Valid Book newBook, Errors errors, Model model, @RequestParam String title,
                                     @RequestParam String author, @RequestParam String isbn, @RequestParam String pages,
                                     @RequestParam String genre, @RequestParam String status, @RequestParam String rating,@PathVariable String bookId) {
        if(errors.hasErrors()) {
            return "search/view/{bookId}";
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

        return "redirect:{bookId}";
    }

}
