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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("user")
public class SearchController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("search")
    public String displaySearchForm() { return "search/search"; }


    @PostMapping("search")
    public String processSearchForm(Model model, String searchQuery, String searchParameter, String resultsPerPage) {

        String newQuery = "";
        String queryToPathVariable = searchQuery.toLowerCase().replace(' ', '+');

        if(searchParameter == null || searchParameter.equals("all")) {
            newQuery += queryToPathVariable;
        } else {
            searchParameter += ":";
            newQuery += searchParameter + queryToPathVariable;
        }

        int pageNumber = 1;
        String currentPage = "&page=" + pageNumber;
        if (resultsPerPage == null) {
            resultsPerPage = "10";
        }
        String maxResults = "&maxResults=" + resultsPerPage;
        String searchOptions = maxResults + currentPage;

        return "redirect:search/results/q=" + newQuery + searchOptions;
    }

    @GetMapping("search/results/q={query}&maxResults={maxResults}&page={currentPage}")
    public String displaySearchResults(Model model, @PathVariable String query, @PathVariable int currentPage, @PathVariable int maxResults) {
        String searchQuery;
        String displayQuery;

        String searchParameter;
        String displayParameter;


        List<String> pathParameters = new ArrayList<>(Arrays.asList("intitle", "inauthor", "subject", "isbn"));
        List<String> displayParameters = new ArrayList<>(Arrays.asList("title: ", "author: ", "genre: ", "isbn: "));

        if (query.contains(":")) {
            String[] queryArray = query.split(":", 0);
            searchParameter = queryArray[0];
            displayParameter = displayParameters.get(pathParameters.indexOf(queryArray[0]));

            searchQuery = queryArray[1].replaceAll("[+]", " ");
            displayQuery = displayParameter += searchQuery;
        } else {
            searchParameter = null;
            searchQuery = query.replaceAll("[+]", " ");
            displayQuery = "all: " + searchQuery;
        }

        model.addAttribute("searchParameter", searchParameter);
        model.addAttribute("displayQuery", displayQuery);
        model.addAttribute("maxResults", maxResults);
        model.addAttribute("searchQuery", searchQuery);
        model.addAttribute("query", query);
        model.addAttribute("pageNumber", currentPage);
        model.addAttribute("startIndex", (currentPage-1) * maxResults);
        return "search/results";
    }

    @PostMapping("search/results/q={currentQuery}&maxResults={maxResults}&page={currentPage}")
    public String processSearchFormFromResultsPage(Model model, @PathVariable String currentQuery, @PathVariable int currentPage,
                                                   String searchQuery, String searchParameter, String resultsPerPage) {

        String newQuery = "";
        String queryToPathVariable = searchQuery.toLowerCase().replace(' ', '+');

        if(searchParameter == null || searchParameter.equals("all")) {
            newQuery += queryToPathVariable;
        } else {
            searchParameter += ":";
            newQuery += searchParameter + queryToPathVariable;
        }

        int pageNumber = 1;
        String newCurrentPage = "&page=" + pageNumber;
        if (resultsPerPage == null) {
            resultsPerPage = "10";
        }
        String newMaxResults = "&maxResults=" + resultsPerPage;
        String searchOptions = newMaxResults + newCurrentPage;

        return "redirect:" + "q=" + newQuery + searchOptions;
    }

    @GetMapping("search/results/view/{bookId}")
    public String displayViewBook(Model model, @PathVariable String bookId) {
        model.addAttribute("bookId", bookId);
        model.addAttribute(new Book());

        List<Book> allBooksInRepo = new ArrayList<>();
        List<String> bookRepositoryIsbns;
        List<Integer> bookRepositoryIds;

        bookRepository.findAll().forEach(allBooksInRepo::add);
        bookRepositoryIsbns = allBooksInRepo.stream().map(Book::getIsbn).collect(Collectors.toList());
        bookRepositoryIds = allBooksInRepo.stream().map(Book::getId).collect(Collectors.toList());

        model.addAttribute("bookRepositoryIds", bookRepositoryIds);
        model.addAttribute("bookRepositoryIsbns", bookRepositoryIsbns);

        return "search/view";
    }

    @PostMapping("search/results/view/{bookId}")
    public String processAddBook(@ModelAttribute @Valid Book newBook, Errors errors, Model model, @RequestParam String title,
                                     @RequestParam String author, @RequestParam String isbn, @RequestParam String pages,
                                     @RequestParam String genre, @RequestParam String status, @RequestParam String rating, @PathVariable String bookId) {
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
//        model.addAttribute("thumbnail", thumbnail);


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
        newBook.setDateAdded(currentDate);
//        newBook.setThumbnail(thumbnail);

        bookRepository.save(newBook);

        return "redirect:{bookId}";
    }

}
