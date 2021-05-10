//dopoprawki
package bug.com;

import bug.com.auth.Authority;
import bug.com.auth.AuthorityRepository;
import bug.com.auth.PersonService;
import bug.com.enums.AuthorityName;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service
public class Bootstrap implements InitializingBean {

    private final AuthorityRepository authorityRepository;
    private final PersonService personService;

    public Bootstrap(AuthorityRepository authorityRepository, PersonService personService) {
        this.authorityRepository = authorityRepository;
        this.personService = personService;
    }

    @Override
    public void afterPropertiesSet() {
        prepareAuthorities();

        personService.prepareAdminUser();
    }

    private void prepareAuthorities() {
        for (AuthorityName name : AuthorityName.values()) {
            Authority existingAuthority = authorityRepository.findByName(name);

            if (existingAuthority == null) {
                Authority authority = new Authority(name);
                authorityRepository.save(authority);
            }
        }
    }
}
