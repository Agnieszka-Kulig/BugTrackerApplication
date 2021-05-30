package bug.com.project;

import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class ProjectController {
    private ProjectService projectService;


    @GetMapping("/")
//    @Secured("ROLE_PROJECT_TAB")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("projects/index");
        modelAndView.addObject("projects", projectService.getAllProjects());

        return modelAndView;
    }

    @GetMapping("/create")
    @Secured("ROLE_CREATE_PROJECT")
    ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("project/create");
        modelAndView.addObject("project", new Project());

        return modelAndView;
    }

    @PostMapping("/save")
    @Secured("ROLE_CREATE_PROJECT")
    ModelAndView createNewProject(@ModelAttribute @Valid Project project, BindingResult bindingresult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingresult.hasErrors()) {
            modelAndView.setViewName("projects/create");
            return modelAndView;
        }

//        projectRepository.save(project);
        modelAndView.setViewName("redirect:/projects");

        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    ModelAndView deleteProject(@PathVariable("id") long id) {
        projectService.deleteProject(id);

        ModelAndView modelAndView = new ModelAndView("projects/index");
        modelAndView.addObject("projects", projectService.getAllProjects());

        return modelAndView;
    }

    @GetMapping("/edit/{id}")
//    @Secured("ROLE_EDIT_PROJECT")
    ModelAndView edit(@PathVariable("id") long id) {

        Project project = projectService.findProject(id);

        ModelAndView modelAndView = new ModelAndView("projects/project/update");
        modelAndView.addObject("project", project);
        return modelAndView;
    }

    @PostMapping("/update/{id}")
//    @Secured("ROLE_EDIT_PROJECT")
    ModelAndView edit(@PathVariable("id") long id, @Valid Project project, BindingResult result) {
        if (result.hasErrors()) {
            project.setId(id);
            ModelAndView modelAndView = new ModelAndView("/projects/project/update");
            modelAndView.addObject("project", project);
            return modelAndView;
        }
        projectService.editProject(project);

        ModelAndView modelAndView = new ModelAndView("/");
        modelAndView.addObject("projects", projectService.getAllProjects());
        return modelAndView;
    }
}


