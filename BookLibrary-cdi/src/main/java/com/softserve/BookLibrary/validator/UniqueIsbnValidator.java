package com.softserve.BookLibrary.validator;

import com.softserve.BookLibrary.entity.Book;
import com.softserve.BookLibrary.manager.BookManager;

import javax.ejb.EJB;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueIsbnValidator implements ConstraintValidator<UniqueIsbn, String> {

    private Long bookId;

    @EJB
    private BookManager bookManager;

    @Override
    public void initialize(UniqueIsbn constraintAnnotation) {
        bookId = constraintAnnotation.bookId();
        /**
         * Initializes the validator in preparation for isValid(Object, ConstraintValidatorContext) calls.
         * This method is guaranteed to be called before any use of this instance for validation.
         * @param constraintAnnotation
         */
    }

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext context) {
        boolean valid;
        Book book = bookManager.findByIsbn(isbn);
        valid = (book == null) || (book.getId().equals(bookId));
        return valid;
    }
}
