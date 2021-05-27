//zapis nowego użytkownika
package bug.com.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final AuthorityRepository authorityRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${my.admin.username}")
    private String myAdminUsername;

    @Value("${my.admin.password}")
    private String myAdminPassword;

    public PersonService(PersonRepository personRepository, AuthorityRepository authorityRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.personRepository = personRepository;
        this.authorityRepository = authorityRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void prepareAdminUser() {
        if (personRepository.findByUsername(myAdminUsername) != null) {
            System.out.println("Użytkownik " + myAdminUsername + " już istnieje. Przerywamy tworzenie.");
            return;
        }
        System.out.println("Tworzymy administratora: " + myAdminUsername + "...");
        Person person = new Person(myAdminUsername, myAdminPassword, "Administrator");


        List<Authority> authorities = (List<Authority>) authorityRepository.findAll();
        person.setAuthorities(new HashSet<>(authorities));

        savePerson(person);
    }

    public void createNewPerson(String userRealName, String username, String meil){
        Person person = new Person();
        person.setName(userRealName);
        person.setUsername(username);
        person.setEmail(meil);
        personRepository.save(person);

    }
    public void deletePerson(String username) {
        Person byUsername = personRepository.findByUsername(username);
        personRepository.delete(byUsername);
    }

        protected void savePerson(Person person) {
            String hashedPassword = bCryptPasswordEncoder.encode(person.getPassword());
            person.setPassword(hashedPassword);
            personRepository.save(person);

        }

        List<Person> findAllUsers() {

        return personRepository.findAll();
        }
    }
