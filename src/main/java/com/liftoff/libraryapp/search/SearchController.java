package com.liftoff.libraryapp.search;

import com.liftoff.libraryapp.models.Book;
import com.liftoff.libraryapp.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String processSearchForm(Model model, String searchQuery, String searchParameter, String resultsPerPage) {

        String query = "";
        String searchQueryPathVariable = searchQuery.toLowerCase().replace(' ', '+');
        if (!searchParameter.equals("all")) {
            searchParameter += ':';
            query += searchParameter + searchQueryPathVariable;
        } else {
            query += searchQueryPathVariable;
        }
        int pageNumber = 1;
        String currentPage = "&page=" + pageNumber;
        String maxResults = "&maxResults=" + resultsPerPage;

        return "redirect:search/results/q=" + query + maxResults + currentPage;
    }

    @GetMapping("search/results/q={query}&maxResults={maxResults}&page={currentPage}")
    public String displaySearchResults(Model model, @PathVariable String query, @PathVariable int currentPage, @PathVariable int maxResults) {
        String searchQuery = "";
        String searchParameter = "";
        if (query.contains(":")) {
            String[] queryArray = query.split(":");
            searchParameter = queryArray[0];
            searchQuery = queryArray[1].replaceAll("[+]", " ");
//            model.addAttribute("queryArray", queryArray);
        } else {
            searchQuery = query.replaceAll("[+]", " ");
            searchParameter = "all";
        }

        model.addAttribute("maxResults", maxResults);
        model.addAttribute("searchQuery", searchQuery);
        model.addAttribute("searchParameter", searchParameter);
        model.addAttribute("query", query);
        model.addAttribute("pageNumber", currentPage);
        model.addAttribute("startIndex", (currentPage-1) * 10);
        return "search/results";
    }

    @PostMapping("search/results/q={currentQuery}&maxResults={maxResults}&page={currentPage}")
    public String processSearchFormFromResultsPage(Model model, @PathVariable String currentQuery, @PathVariable int currentPage,
                                                   String searchQuery, String searchParameter, String resultsPerPage) {
        String newQuery = "";
        String searchQueryPathVariable = searchQuery.toLowerCase().replace(' ', '+');
        if (!searchParameter.equals("all")) {
            searchParameter += ':';
            newQuery += searchParameter + searchQueryPathVariable;
        } else {
            newQuery += searchQueryPathVariable;
        }
        int pageNumber = 1;
        String newCurrentPage = "&page=" + pageNumber;
        String newMaxResults = "&maxResults=" + resultsPerPage;

        return "redirect:" + "q=" + newQuery + newMaxResults + newCurrentPage;
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
