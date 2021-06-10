////filtrowanie po zgłoszeniu, projekcie, użytkowniku, statusie
package bug.com.issues;

import bug.com.auth.PersonRepository;
import bug.com.auth.PersonService;
import bug.com.enums.Priority;
import bug.com.enums.Status;
import bug.com.enums.Type;
import bug.com.project.ProjectRepository;
import bug.com.project.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping("/issue")
public class IssueController {
    private IssueService issueService;

    final IssueRepository issueRepository;
    final ProjectRepository projectRepository;
    final PersonRepository personRepository;
    final ProjectService projectService;
    final PersonService personService;

    @GetMapping
    ModelAndView index(@ModelAttribute IssueFilter issueFilter) {
        ModelAndView modelAndView = new ModelAndView("issue/index");

        modelAndView.addObject("issues", issueRepository.findAll(issueFilter.buildQuery()));
        modelAndView.addObject("projects", projectService.getAllProjects());
        modelAndView.addObject("people", personService.findAllUsers());
        modelAndView.addObject("statuses", Status.values());
        modelAndView.addObject("types", Type.values());
        modelAndView.addObject("priorities", Priority.values());
        modelAndView.addObject("filter", issueFilter);

        return modelAndView;
    }

    @GetMapping("/create")
    @Secured({"ROLE_MANAGER", "ROLE_ADMIN", "ROLE_USERS"})
    ModelAndView createIssue() {
        ModelAndView modelAndView = new ModelAndView("issue/create");
        modelAndView.addObject("issue", new Issue());
        modelAndView.addObject("statuses", Status.values());
        modelAndView.addObject("types", Type.values());
        modelAndView.addObject("priorities", Priority.values());
        modelAndView.addObject("projects", projectService.getAllEnabledProjects());
        modelAndView.addObject("assignees", personService.findAllUsers());

        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    @Secured({"ROLE_MANAGER", "ROLE_ADMIN", "ROLE_USERS"})
    ModelAndView editIssue(@PathVariable Long id) {
        Issue issue = issueService.findIssue(id);
        if (issue == null) {
            return index(new IssueFilter());
        }
        ModelAndView modelAndView = new ModelAndView("issue/create");
        modelAndView.addObject("issue", issue);
        modelAndView.addObject("statuses", Status.values());
        modelAndView.addObject("types", Type.values());
        modelAndView.addObject("priorities", Priority.values());
        modelAndView.addObject("projects", projectService.getAllEnabledProjects());
        modelAndView.addObject("assignees", personService.findAllUsers());
        return modelAndView;
    }

    @PostMapping(value = "/save")
    @Secured({"ROLE_MANAGER", "ROLE_ADMIN", "ROLE_USERS"})
    ModelAndView saveIssue(@ModelAttribute @Valid Issue issue, BindingResult bindingresult) throws MessagingException {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingresult.hasErrors()) {
            modelAndView.setViewName("issue/create");
            modelAndView.addObject("issue", issue);
            modelAndView.addObject("statuses", Status.values());
            modelAndView.addObject("types", Type.values());
            modelAndView.addObject("priorities", Priority.values());
            modelAndView.addObject("projects", projectService.getAllEnabledProjects());
            modelAndView.addObject("assignees", personService.findAllUsers());
            return modelAndView;
        }
        issueService.createNewIssue(issue);
        modelAndView.setViewName("redirect:/issue");

        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    @Secured({"ROLE_MANAGER", "ROLE_ADMIN", "ROLE_USERS"})
    ModelAndView deleteIssue(@PathVariable("id") long id) {
        issueService.deleteIssue(id);
        ModelAndView modelAndView = new ModelAndView("issue/index");
        modelAndView.addObject("issues", issueService.getAllIssue());
        modelAndView.addObject("filter", new IssueFilter());

        return modelAndView;
    }
}