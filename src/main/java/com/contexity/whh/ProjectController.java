package com.contexity.whh;

import com.contexity.whh.domain.Entry;
import com.contexity.whh.domain.Project;
import com.contexity.whh.repository.CustomerRepository;
import com.contexity.whh.repository.EntryRepository;
import com.contexity.whh.repository.ProjectRepository;
import com.contexity.whh.service.CustomerService;
import com.contexity.whh.service.EntryService;
import com.contexity.whh.service.ProjectService;
import com.contexity.whh.service.WorksheetService;
import com.contexity.whh.service.dto.CustomerDTO;
import com.contexity.whh.service.dto.EntryDTO;
import com.contexity.whh.service.dto.ProjectDTO;
import com.contexity.whh.service.dto.WorksheetDTO;
import com.contexity.whh.web.rest.EntryResource;
import java.time.*;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProjectController {

    private final CustomerRepository customerRepository;
    private final CustomerService customerService;
    private final ProjectService projectService;
    private final WorksheetService worksheetService;

    private final ProjectRepository projectRepository;

    private final EntryService entryService;

    private final EntryRepository entryRepository;
    private final EntryResource entryResource;

    LocalDate currentdate = LocalDate.now();
    LocalDate weekBefore = currentdate.minusDays(7);
    int month = currentdate.getMonth().getValue();
    int year = currentdate.getYear();
    double hours, hours2, hours3 = 0;

    public ProjectController(
        CustomerRepository customerRepository,
        WorksheetService worksheetService,
        ProjectService projectService,
        ProjectRepository projectRepository,
        EntryService entryService,
        EntryResource entryResource,
        EntryRepository entryRepository,
        CustomerService customerService
    ) {
        this.projectService = projectService;
        this.projectRepository = projectRepository;
        this.entryService = entryService;
        this.entryRepository = entryRepository;
        this.entryResource = entryResource;
        this.worksheetService = worksheetService;
        this.customerRepository = customerRepository;
        this.customerService = customerService;
    }

    @RequestMapping("/stats/projects/{id}")
    public String getProjects(@PathVariable Integer id, Model model) {
        long idl = id.longValue();

        Optional<ProjectDTO> projectDTO = projectService.findOne(idl);
        model.addAttribute("project", projectDTO.get());

        List<Entry> entryList = entryResource.findAllEntriesforProject(idl);
        hours = entryResource.calculateHours(entryList);
        model.addAttribute("hours", hours);

        List<Entry> entryDateList = entryResource.findDatedEntriesforProject(idl, month, year);
        hours2 = entryResource.calculateHours(entryDateList);
        model.addAttribute("hours2", hours2);

        List<Entry> entryWeekList = entryResource.findWeeklyEntriesforProject(idl, weekBefore, currentdate);
        hours3 = entryResource.calculateHours(entryWeekList);
        model.addAttribute("hours3", hours3);
        return "projects";
    }

    @RequestMapping("/stats/users/{id}")
    public String getWorksheets(@PathVariable Integer id, Model model) {
        long idl = id.longValue();

        Optional<WorksheetDTO> worksheetDTO = worksheetService.findOne(idl);
        model.addAttribute("worksheet", worksheetDTO.get());

        List<Entry> entryList = entryResource.findAllEntriesforWorksheet(idl);
        hours = entryResource.calculateHours(entryList);
        model.addAttribute("hours", hours);

        List<Entry> entryDateList = entryResource.findDatedEntriesforWorksheet(idl, month, year);
        hours2 = entryResource.calculateHours(entryDateList);
        model.addAttribute("hours2", hours2);

        List<Entry> entryWeekList = entryResource.findWeeklyEntriesforWorksheet(idl, weekBefore, currentdate);
        hours3 = entryResource.calculateHours(entryWeekList);
        model.addAttribute("hours3", hours3);

        return "users";
    }

    @RequestMapping("/stats/customers/{id}")
    public String getCustomers(@PathVariable Integer id, Model model) {
        long idl = id.longValue();

        Optional<CustomerDTO> customerDTO = customerService.findOne(idl);
        model.addAttribute("customer", customerDTO.get());

        List<Project> projectList = customerRepository.getProjects(idl);
        for (Project project : projectList) {
            long pid = project.getId();
            List<Entry> entryList = entryResource.findAllEntriesforProject(pid);
            hours = hours + entryResource.calculateHours(entryList);
            model.addAttribute("hours", hours);

            List<Entry> entryDateList = entryResource.findDatedEntriesforProject(pid, month, year);
            hours2 = entryResource.calculateHours(entryDateList);
            model.addAttribute("hours2", hours2);

            List<Entry> entryWeekList = entryResource.findWeeklyEntriesforProject(pid, weekBefore, currentdate);
            hours3 = entryResource.calculateHours(entryWeekList);
            model.addAttribute("hours3", hours3);
        }
        return "customers";
    }
}
