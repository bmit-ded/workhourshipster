package com.contexity.whh;

import com.contexity.whh.repository.ProjectRepository;
import com.contexity.whh.service.ProjectService;
import com.contexity.whh.service.dto.ProjectDTO;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProjectController {

    private final ProjectService projectService;

    private final ProjectRepository projectRepository;

    public ProjectController(ProjectService projectService, ProjectRepository projectRepository) {
        this.projectService = projectService;
        this.projectRepository = projectRepository;
    }

    @RequestMapping("/stats/projects/{id}")
    public String get(@PathVariable Integer id, Model model) {
        Optional<ProjectDTO> projectDTO = projectService.findOne(id.longValue());
        model.addAttribute("project", projectDTO.get());

        return "projects";
    }
}
