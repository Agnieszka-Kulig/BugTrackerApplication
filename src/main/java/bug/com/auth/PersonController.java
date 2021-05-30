
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
//    @Secured("ROLE_USERS_TAB")
    ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("people/index");
        modelAndView.addObject("people", personService.findAllUsers());
        return modelAndView;
    }

    @GetMapping("/create")
//    @Secured("ROLE_CREATE_USER")
    ModelAndView create() {
        List<Authority> authorities = authorityRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("people/create");
        modelAndView.addObject("authorities", authorityRepository.findAll());
        modelAndView.addObject("person", new Person());

        return modelAndView;
    }

    @GetMapping("/edit/{id}")
//    @Secured("ROLE_CREATE_USER")
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
//    @Secured("ROLE_CREATE_USER")
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
}
//  dorobic usuwanie
//       @GetMapping("/delete/{id}")
//       @Secured("ROLE_CREATE_USER")
//       ModelAndView modelAndView = new ModelAndView();

//        modelAndView.setViewName("redirect:/people");

//          return modelAndView;
//   }
//}

//    @PostMapping("/disable")
//    public Optional<Person> disable(@RequestParam String username) {
//        Optional<Person> person = personRepository.findByUsername(username, true);
//        person.ifPresent((value) -> {
//            value.setEnabled(false);
//            personRepository.save(value);
//        });
//        return person;
//    }
//}

