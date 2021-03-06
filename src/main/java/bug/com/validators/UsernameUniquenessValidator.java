package bug.com.validators;

import bug.com.auth.Person;
import bug.com.auth.PersonRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameUniquenessValidator
        implements ConstraintValidator<UniqueUsername, Person> {


    private final PersonRepository personRepository;

    public UsernameUniquenessValidator(PersonRepository personRepository) {

        this.personRepository = personRepository;
    }

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);

    }

    @Override
    public boolean isValid(Person person, ConstraintValidatorContext ctx) {
        Person foundPerson = personRepository.findFirstByUsername(person.getUsername());

        if (foundPerson == null) {
            return true;
        }

        boolean usernameIsUnique = person.getId() != null && foundPerson.getId().equals(person.getId());

        if (!usernameIsUnique) {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("username")
                    .addConstraintViolation();
        }

        return usernameIsUnique;
    }
}