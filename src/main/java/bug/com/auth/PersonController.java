
package bug.com.auth;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/people")
public class PersonController {

    private final AuthorityRepository authorityRepository;
    private final PersonService personService;
    private final PersonRepository personRepository;

    public PersonController(AuthorityRepository authorityRepository, PersonService personService, PersonRepository personRepository) {
        this.authorityRepository = authorityRepository;
        this.personService = personService;
        this.personRepository = personRepository;
    }

    @GetMapping("/")
    @Secured("ROLE_ADMIN")
    ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("people/index");
        modelAndView.addObject("people", personService.findAllUsers());
        return modelAndView;
    }

    @GetMapping("/editMyProfile")
    ModelAndView editMyProfile() {
        String username = personService.getCurrentUsername().getUsername();
        Person person = personService.findPersonByUsername(username);
        ModelAndView modelAndView = new ModelAndView("people/create");
        modelAndView.addObject("authorities", authorityRepository.findAll());
        modelAndView.addObject("person", person);
        if (person == null) {
            modelAndView.setViewName("redirect:/");
        }
        return modelAndView;
    }

    @GetMapping("/create")
    @Secured("ROLE_ADMIN")
    ModelAndView create() {
        List<Authority> authorities = authorityRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("people/create");
        modelAndView.addObject("authorities", authorities);
        modelAndView.addObject("person", new Person());

        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    @Secured("ROLE_ADMIN")
    ModelAndView edit(@PathVariable Long id) {
        Person person = personRepository.findById(id).orElse(null);
        if (person == null) {
            return index();
        }
        ModelAndView modelAndView = new ModelAndView("people/create");
        modelAndView.addObject("authorities", authorityRepository.findAll());
        modelAndView.addObject("person", person);
        return modelAndView;
    }

    @PostMapping(value = "/save")
    @Secured("ROLE_ADMIN")
    ModelAndView saveUser(@ModelAttribute @Valid Person person, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("people/create");
            modelAndView.addObject("authorities", authorityRepository.findAll());
            modelAndView.addObject("person", person);
            return modelAndView;
        }

        personService.savePerson(person);
        modelAndView.setViewName("redirect:/people/");

        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    @Secured("ROLE_ADMIN")
    ModelAndView delete(@ModelAttribute@PathVariable("id")long id){
        ModelAndView modelAndView = new ModelAndView("people/index");
        if(personService.deleteEnable(id)) {
            modelAndView.addObject("errorMessage", false);
            personService.deletePerson(id);
        } else {
            modelAndView.addObject("errorMessage", true);
        }
        modelAndView.addObject("people", personService.findAllUsers());

        return modelAndView;
    }
}