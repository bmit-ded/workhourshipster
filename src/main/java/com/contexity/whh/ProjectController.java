package com.contexity.whh;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProjectController {

    @RequestMapping("/stats/projects/{projectid}")
    public String getData(@PathVariable Integer projectid, Model model) {
        model.addAttribute("projectid", projectid);

        return "projects";
    }
}
