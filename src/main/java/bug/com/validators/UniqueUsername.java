package bug.com.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameUniquenessValidator.class)
public @interface UniqueUsername {
    String message() default "{username.unique.error}";//sprawdzenie unikalności nazwy użytkownika

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}