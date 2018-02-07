package com.softserve.BookLibrary.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static com.softserve.BookLibrary.validator.ValidationConstants.AUTHOR_HAS_BOOKS_MESSAGE;

/**
 * AuthorHasBooks interface for check if author has books.
 * Is used before delete Author.
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {AuthorHasBooksValidator.class})
public @interface AuthorHasBooks {
    /**
     * This method returns default error message
     *
     * @return
     */
    String message() default AUTHOR_HAS_BOOKS_MESSAGE;

    /**
     * This method returns groups for which this annotation is used
     *
     * @return
     */
    Class<?>[] groups() default {};

    /**
     * Payload can be used by clients of the Bean Validation API to assign custom payload objects to a constraint
     *
     * @return
     */
    Class<? extends Payload>[] payload() default {};
}
