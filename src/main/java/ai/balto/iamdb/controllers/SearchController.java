package ai.balto.iamdb.controllers;

import ai.balto.iamdb.models.Movie;
import ai.balto.iamdb.models.MovieData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Locale;

import static ai.balto.iamdb.controllers.ListController.columnChoices;

@Controller
@RequestMapping("search")
public class SearchController {

    @RequestMapping(value = "")
    public String search(Model model) {
        String typeSelection = "all";
        model.addAttribute("typeSelection", typeSelection);
        model.addAttribute("columns", columnChoices);
        return "search";
    }

    @PostMapping(value = "results")
    public String displaySearchResults(Model model, @RequestParam String searchType, @RequestParam String searchTerm){
        ArrayList<Movie> movies;
        String typeSelection = searchType;
        model.addAttribute("columns", columnChoices);
        if(searchType.toLowerCase(Locale.ROOT) == "all" || searchTerm.toLowerCase(Locale.ROOT) == "all"){
            movies = MovieData.findAll();
        } else {
            movies = MovieData.findByColumnAndValue(searchType, searchTerm);
        }
        model.addAttribute("movies", movies);
        model.addAttribute("typeSelection", typeSelection);
        return "search";
    }
}
