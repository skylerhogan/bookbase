package com.liftoff.libraryapp.search;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("user")
public class SearchController {

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
        return "search/view";
    }

}
