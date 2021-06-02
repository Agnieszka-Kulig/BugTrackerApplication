package bug.com.project;


import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class ProjectController {
    private ProjectService projectService;


    @GetMapping("/")
    @Secured({"ROLE_USERS", "ROLE_MANAGER", "ROLE_ADMIN"})
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("projects/index");
        modelAndView.addObject("projects", projectService.getAllProjects());

        return modelAndView;
    }

    @GetMapping("/create")
    @Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
    ModelAndView createProject() {
        ModelAndView modelAndView = new ModelAndView("projects/create");
        modelAndView.addObject("project", new Project());

        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    @Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
    ModelAndView editProject(@PathVariable Long id) {
        Project project = projectService.findProject(id);
        if (project == null) {
            return index();
        }
        ModelAndView modelAndView = new ModelAndView("projects/create");
        modelAndView.addObject("project", project);
        return modelAndView;
    }

    @PostMapping(value = "/projects/save")
    @Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
    ModelAndView saveProject(@ModelAttribute @Valid Project project, BindingResult bindingresult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingresult.hasErrors()) {
            modelAndView.setViewName("projects/create");
            modelAndView.addObject("project", project);
            return modelAndView;
        }
        projectService.createNewProject(project);
        modelAndView.setViewName("redirect:/");

        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    @Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
    ModelAndView deleteProject(@PathVariable("id") long id) {
        projectService.deleteProject(id);

        ModelAndView modelAndView = new ModelAndView("projects/index");
        modelAndView.addObject("projects", projectService.getAllProjects());

        return modelAndView;
    }
}
