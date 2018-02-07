package com.softserve.BookLibrary.validator;

public class ValidationConstants {

    public static final int MIN_RATING = 1;

    public static final int MAX_RATING = 5;

    public static final String RATING_SIZE_MESSAGE = "Rating rating should be between" + MIN_RATING + " and " + MAX_RATING + "5";

    public static final int NAME_MIN_SIZE = 2;

    public static final int NAME_MAX_SIZE = 255;

    public static final String NAME_SIZE_MESSAGE = "Name must have more than " + NAME_MIN_SIZE + " and less than " + NAME_MAX_SIZE + " symbols";

    public static final String ISBN_PATTERN = "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ])" +
            "{4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$";

    public static final String ISBN_PATTERN_MESSAGE = "Invalid isbn";

    public static final int COMMENT_MIN_SIZE = 2;

    public static final int COMMENT_MAX_SIZE = 500;

    public static final String COMMENT_SIZE_MESSAGE = "Comment must have more than " + COMMENT_MIN_SIZE + " and less than " + COMMENT_MAX_SIZE + " symbols";

    public static final String AUTHOR_HAS_BOOKS_MESSAGE = "Author has books yet, you can't delete it";

    public static final String UNIQUE_ISBN_MESSAGE= "Isbn is not unique";

    public static final int ISBN_MIN_SIZE = 10;

    public static final int ISBN_MAX_SIZE = 13;

    public static final String ISBN_SIZE_MESSAGE = "Isbn must have " + ISBN_MIN_SIZE + " or " + ISBN_MAX_SIZE + " symbols";

    public static final int PUBLISHED_YEAR_MIN_VALUE = 1;

    public static final int PUBLISHED_YEAR_MAX_VALUE = 2018;

    public static final String PUBLISHED_YEAR_SIZE_MESSAGE = "Value must be from " + PUBLISHED_YEAR_MIN_VALUE + " to " + PUBLISHED_YEAR_MAX_VALUE;



}
