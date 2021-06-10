//zapis nowego użytkownika
package bug.com.auth;

import bug.com.issues.Issue;
import bug.com.issues.IssueService;
import bug.com.project.Project;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final IssueService issueService;
    private final PersonRepository personRepository;
    private final AuthorityRepository authorityRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${my.admin.username}")
    private String myAdminUsername;

    @Value("${my.admin.password}")
    private String myAdminPassword;

    public PersonService(PersonRepository personRepository, AuthorityRepository authorityRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                         IssueService issueService) {
        this.personRepository = personRepository;
        this.authorityRepository = authorityRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.issueService = issueService;
    }

    public void prepareAdminUser() {
        if (personRepository.findFirstByUsername(myAdminUsername) != null) {
            System.out.println("Administrator " + myAdminUsername + " już istnieje. Przerywamy tworzenie.");
            return;
        }
        System.out.println("Tworzymy administratora: " + myAdminUsername + "...");
        Person person = new Person(myAdminUsername, myAdminPassword, "Administrator");


        List<Authority> authorities = (List<Authority>) authorityRepository.findAll();
        person.setAuthorities(new HashSet<>(authorities));

        savePerson(person);
    }

    public void deletePerson(Long id) {
        Optional<Person> person = personRepository.findById(id);
        if(person.isPresent()) {
            personRepository.delete(person.get());
        }
    }

    public void savePerson(Person person) {
        String hashedPassword = bCryptPasswordEncoder.encode(person.getPassword());
        person.setPassword(hashedPassword);
        personRepository.save(person);

    }

    public List<Person> findAllUsers() {
        return personRepository.findAll();
    }

    public User getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();
    }

    public Person findPersonByUsername(String username) {
        return personRepository.findFirstByUsername(username);
    }

    public Person findPersonById (long id) {

        return personRepository.findById(id).orElse(null);
    }

    public boolean deleteEnable(long personId) {
        Person person = findPersonById(personId);
        if(person != null) {
            List<Issue> issueList = issueService.getAllIssuesByPerson(person);
            return issueList.isEmpty();
        }
        return false;
    }
}
