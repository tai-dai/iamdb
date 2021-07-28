package ai.balto.iamdb.controllers;

import ai.balto.iamdb.models.Movie;
import ai.balto.iamdb.models.MovieData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AddController {

    @RequestMapping(value = "add")
    public String displayAddMovieForm(Model model) {

        model.addAttribute("title", "Add Movie");
        return "add";
    }

    @PostMapping("add")
    public String processAddMovieForm(Model model, @RequestParam String name, @RequestParam String year,
                                      @RequestParam String origin, @RequestParam String directors,
                                      @RequestParam String cast, @RequestParam String genre,
                                      @RequestParam String wiki, @RequestParam String plot) {

        MovieData.addMovie(year, name, origin, directors, cast, genre, wiki, plot);
        return "redirect:";
    }
}
