package bug.com.project;

import bug.com.auth.Person;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class ProjectController {
     final ProjectRepository projectRepository;

    public ProjectController(ProjectRepository projectRepository) {

        this.projectRepository = projectRepository;
    }
    @GetMapping("/")
    @Secured("ROLE_PROJECT_TAB")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("projects/index");
        modelAndView.addObject("projects", projectRepository.findAll());

        return modelAndView;
    }

    @GetMapping("/create")
    @Secured("ROLE_CREATE_PROJECT")
    ModelAndView create(){
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

        projectRepository.save(project);
        modelAndView.setViewName("redirect:/projects");

        return modelAndView;
    }
        @GetMapping("/edit/{id}")
        @Secured("ROLE_EDIT_PROJECT")
        ModelAndView edit(@PathVariable Long id) {
            Project project = projectRepository.findById(id).orElse(null);
            if (project == null) {
                return index();
            }
            ModelAndView modelAndView = new ModelAndView("project/create");
            modelAndView.addObject("project", project);
            return modelAndView;
        }

    }
//    usuwanie projektu