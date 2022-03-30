package com.contexity.whh;

import com.contexity.whh.domain.Entry;
import com.contexity.whh.repository.EntryRepository;
import com.contexity.whh.repository.ProjectRepository;
import com.contexity.whh.service.EntryService;
import com.contexity.whh.service.ProjectService;
import com.contexity.whh.service.dto.EntryDTO;
import com.contexity.whh.service.dto.ProjectDTO;
import com.contexity.whh.web.rest.EntryResource;
import java.util.List;
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

    private final EntryService entryService;

    private final EntryRepository entryRepository;
    private final EntryResource entryResource;

    public ProjectController(
        ProjectService projectService,
        ProjectRepository projectRepository,
        EntryService entryService,
        EntryResource entryResource,
        EntryRepository entryRepository
    ) {
        this.projectService = projectService;
        this.projectRepository = projectRepository;
        this.entryService = entryService;
        this.entryRepository = entryRepository;
        this.entryResource = entryResource;
    }

    @RequestMapping("/stats/projects/{id}")
    public String get(@PathVariable Integer id, Model model) {
        long idl = id.longValue();
        double hours = 0;
        Optional<ProjectDTO> projectDTO = projectService.findOne(idl);
        model.addAttribute("project", projectDTO.get());

        List<Entry> entryList = entryResource.findAllEntriesforProject(idl);
        hours = entryResource.calculateHours(entryList);
        model.addAttribute("hours", hours);

        return "projects";
    }

    @RequestMapping("/stato/{id}")
    public String get2(@PathVariable Integer id, Model model) {
        long idl = id.longValue();
        double hours = 0;

        return "hours";
    }

    @RequestMapping("/all/{id}")
    public String showAll(@PathVariable Integer id, Model model) {
        List<Entry> entryList = entryRepository.findbyProject(id.longValue());
        model.addAttribute("entries", entryList);
        return "all";
    }
}
