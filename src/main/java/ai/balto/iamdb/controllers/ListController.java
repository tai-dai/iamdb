package ai.balto.iamdb.controllers;

import ai.balto.iamdb.models.Movie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ai.balto.iamdb.models.MovieData;

import java.util.ArrayList;
import java.util.HashMap;


@Controller
@RequestMapping(value = "list")
public class ListController {

    static HashMap<String, String> columnChoices = new HashMap<>();
    static HashMap<String, Object> tableChoices = new HashMap<>();

    public ListController() {
        columnChoices.put("all", "All");
        columnChoices.put("origin", "Origin");
        columnChoices.put("director", "Director");
        columnChoices.put("cast", "Actors");
        columnChoices.put("genre", "Genre");

        tableChoices.put("all", "All");
        tableChoices.put("origin", MovieData.getAllOrigins());
        tableChoices.put("director", MovieData.getAllDirectors());
        tableChoices.put("actor", MovieData.getAllActors());
        tableChoices.put("genre", MovieData.getAllGenres());
    }

    @RequestMapping(value = "")
    public String list(Model model) {
        model.addAttribute("columns", columnChoices);
        model.addAttribute("tableChoices", tableChoices);
        model.addAttribute("all", MovieData.findByColumnAndValue("all", "all"));
        model.addAttribute("name", MovieData.getAllNames());
        model.addAttribute("origin", MovieData.getAllOrigins());
        model.addAttribute("director", MovieData.getAllDirectors());
        model.addAttribute("cast", MovieData.getAllActors());
        model.addAttribute("genre", MovieData.getAllGenres());

        return "list";
    }

    @RequestMapping(value = "movies")
    public String listMoviesByColumnAndValue(Model model, @RequestParam String column, @RequestParam String value) {
        ArrayList<Movie> movies;
        if (column.toLowerCase().equals("all")) {
            movies = MovieData.findAll();
            model.addAttribute("title", "All Movies");
        } else {
            movies = MovieData.findByColumnAndValue(column, value);
            model.addAttribute("title", "Movies with " + columnChoices.get(column) + ": " + value);
        }
        model.addAttribute("movies", movies);

        return "movies-list";
    }
}
