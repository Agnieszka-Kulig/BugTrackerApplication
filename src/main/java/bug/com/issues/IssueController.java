////filtrowanie po zgłoszeniu, projekcie, użytkowniku, statusie
package bug.com.issues;

import bug.com.auth.PersonRepository;
import bug.com.enums.Priority;
import bug.com.enums.Status;
import bug.com.enums.Type;
import bug.com.project.Project;
import bug.com.project.ProjectRepository;
import bug.com.project.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping("/issue")
public class IssueController {
    private IssueService issueService;

    final IssueRepository issueRepository;
    final ProjectRepository projectRepository;
    final PersonRepository personRepository;

    @GetMapping
    ModelAndView index(@ModelAttribute IssueFilter issueFilter) {
        ModelAndView modelAndView = new ModelAndView("issue/index");

        modelAndView.addObject("issues", issueRepository.findAll(issueFilter.buildQuery()));
        modelAndView.addObject("projects", projectRepository.findAll());
        modelAndView.addObject("people", personRepository.findAll());
        modelAndView.addObject("states", Status.values());
        modelAndView.addObject("type", Type.values());
        modelAndView.addObject("priority", Priority.values());
        modelAndView.addObject("filter", issueFilter);

        return modelAndView;
    }
}

//    @GetMapping("/create")
//    @Secured({"ROLE_MANAGER", "ROLE_ADMIN", "ROLE_USERS"})
//    ModelAndView createIssue() {
//        ModelAndView modelAndView = new ModelAndView("issue/create");
//        modelAndView.addObject("issue", new Issue());
//
//        return modelAndView;
//    }
//
//    @GetMapping("/edit/{id}")
//    @Secured({"ROLE_MANAGER", "ROLE_ADMIN",  "ROLE_USERS"})
//    ModelAndView editIssue(@PathVariable Long id) {
//        Issue issue = issueService.findIssue(id);
//        if (issue == null) {
//            return index();
//        }
//        ModelAndView modelAndView = new ModelAndView("issue/create");
//        modelAndView.addObject("issue", issue);
//        return modelAndView;
//    }
//
//    @PostMapping(value = "/issue/save")
//    @Secured({"ROLE_MANAGER", "ROLE_ADMIN", "ROLE_USERS"})
//    ModelAndView saveIssue(@ModelAttribute @Valid Issue issue, BindingResult bindingresult) {
//        ModelAndView modelAndView = new ModelAndView();
//        if (bindingresult.hasErrors()) {
//            modelAndView.setViewName("issue/create");
//            modelAndView.addObject("issue", issue);
//            return modelAndView;
//        }
//        issueService.createNewIssue(issue);
//        modelAndView.setViewName("redirect:/");
//
//        return modelAndView;
//    }
//
//    @GetMapping("/delete/{id}")
//    @Secured({"ROLE_MANAGER", "ROLE_ADMIN", "ROLE_USERS"})
//    ModelAndView deleteIssue(@PathVariable("id") long id) {
//        issueService.deleteIssue(id);
//
//        ModelAndView modelAndView = new ModelAndView("issue/index");
//        modelAndView.addObject("issue", issueService.getAllIssue());
//
//        return modelAndView;
//    }
//}
