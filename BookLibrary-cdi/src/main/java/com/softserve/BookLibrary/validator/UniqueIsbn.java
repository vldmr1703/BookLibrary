package com.softserve.BookLibrary.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static com.softserve.BookLibrary.validator.ValidationConstants.UNIQUE_ISBN_MESSAGE;

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {UniqueIsbnValidator.class})
public @interface UniqueIsbn {
    long bookId();

    String message() default UNIQUE_ISBN_MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

