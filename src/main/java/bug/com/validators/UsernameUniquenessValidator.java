package bug.com.validators;

import bug.com.auth.Person;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameUniquenessValidator implements ConstraintValidator<UniqueUsername, String> {
    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);

    }

    @Override
    public boolean isValid(Person person, ConstraintValidatorContext ctx) {
        Person foundPerson = personRepository.find
        return false;
    }
}
