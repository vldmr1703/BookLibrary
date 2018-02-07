package com.softserve.BookLibrary.validator;

import com.softserve.BookLibrary.manager.BookManager;
import com.softserve.BookLibrary.entity.Author;

import javax.ejb.EJB;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class AuthorHasBooksValidator implements ConstraintValidator<AuthorHasBooks, Author> {

    @EJB
    private BookManager bookManager;

    @Override
    public void initialize(AuthorHasBooks constraintAnnotation) {
        /**
         * Initializes the validator in preparation for isValid(Object, ConstraintValidatorContext) calls.
         * This method is guaranteed to be called before any use of this instance for validation.
         * @param constraintAnnotation
         */
    }

    @Override
    public boolean isValid(Author author, ConstraintValidatorContext context) {
        return bookManager.findByAuthorId(author.getId()).isEmpty();
    }
}
