package com.softserve.BookLibrary.DAO.facade.impl;

import com.softserve.BookLibrary.DAO.facade.AuthorFacade;
import com.softserve.BookLibrary.entity.Author;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import java.util.Date;
import java.util.List;

@Stateless
public class AuthorFacadeImpl extends GenericFacadeImpl<Author> implements AuthorFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorFacadeImpl.class);

    public AuthorFacadeImpl() {
        super(Author.class);
    }

    @Override
    public List<Author> findByFirstName(String firstName) {
        LOGGER.info("findByFirstName({})", firstName);
        return namedQuery(Author.QUERY_FIND_BY_FIRST_NAME)
                .setParameter("firstName", firstName)
                .getResultList();
    }

    @Override
    public List<Author> findBySecondName(String secondName) {
        LOGGER.info("findBySecondName({})", secondName);
        return namedQuery(Author.QUERY_FIND_BY_SECOND_NAME)
                .setParameter("secondName", secondName)
                .getResultList();
    }

    @Override
    public List<Author> findByFirstNameAndSecondName(String firstName, String secondName) {
        LOGGER.info("findByFirstNameAndSecondName({},{})", firstName, secondName);
        return namedQuery(Author.QUERY_FIND_BY_FIRST_NAME_AND_SECOND_NAME)
                .setParameter("firstName", firstName)
                .setParameter("secondName", secondName)
                .getResultList();
    }

    @Override
    public List<Author> findAll() {
        LOGGER.info("findAll()");
        return namedQuery(Author.QUERY_FIND_ALL).getResultList();
    }

    @Override
    public List<Author> findByRating(int rating) {
        LOGGER.info("findByRating({})", rating);
        return namedQuery(Author.QUERY_FIND_BY_RATING)
                .setParameter("rating", rating)
                .getResultList();
    }

    @Override
    public List<Author> findByCreateDate(Date createDate) {
        LOGGER.info("findByCreateDate({})", createDate);
        return namedQuery(Author.QUERY_FIND_BY_CREATE_DATE)
                .setParameter("createDate", createDate)
                .getResultList();
    }
}
