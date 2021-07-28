package ai.balto.iamdb.controllers;

import ai.balto.iamdb.models.Movie;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

public class AddController {

    @RequestMapping(value = "add")
    public String displayAddMovieForm(Model model) {

        model.addAttribute("title", "Add Movie");
        return "movies-add";
    }

//    @PostMapping("add")
//    public String processAddMovieForm(Model model) {


//            @PostMapping("add")
//    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
//                                    Errors errors, Model model, @RequestParam int employerId, @RequestParam List<Integer> skills) {
//
//        Optional optEmployer = employerRepository.findById(employerId);
//        if (optEmployer.isPresent()) {
//            Employer selectedEmployer = (Employer) optEmployer.get();
//            newJob.setEmployer(selectedEmployer);
//        }
//
//        for (int skillId : skills){
//            Optional optSkill = skillRepository.findById(skillId);
//            if (optSkill.isPresent()) {
//                Skill skill = (Skill) optSkill.get();
//                newJob.addSkill(skill);
//            }
//        }
//
//        jobRepository.save(newJob);
//
//        return "redirect:";
//    }
//    }

}
