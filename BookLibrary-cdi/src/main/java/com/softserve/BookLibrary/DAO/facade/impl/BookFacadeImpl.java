package com.softserve.BookLibrary.DAO.facade.impl;

import com.softserve.BookLibrary.DAO.facade.BookFacade;
import com.softserve.BookLibrary.entity.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.util.Date;
import java.util.List;

@Stateless
public class BookFacadeImpl extends GenericFacadeImpl<Book> implements BookFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookFacadeImpl.class);

    public BookFacadeImpl() {
        super(Book.class);
    }

    @Override
    public List<Book> findByName(String name) {
        LOGGER.info("findByName({})", name);
        return namedQuery(Book.QUERY_FIND_BY_NAME)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public Book findByIsbn(String isbn) throws NoResultException {
        LOGGER.info("findByIsbn({})", isbn);
        Book book;
        try {
            book = namedQuery(Book.QUERY_FIND_BY_ISBN)
                    .setParameter("isbn", isbn)
                    .getSingleResult();
        } catch (NoResultException ex) {
            book = null;
        }
        return book;
    }

    @Override
    public List<Book> findByPublisher(String publisher) {
        LOGGER.info("findByPublisher({})", publisher);
        return namedQuery(Book.QUERY_FIND_BY_PUBLISHER)
                .setParameter("publisher", publisher)
                .getResultList();
    }

    @Override
    public List<Book> findByPublisherYear(int publisherYear) {
        LOGGER.info("findByPublisherYear({})", publisherYear);
        return namedQuery(Book.QUERY_FIND_BY_PUBLISHER_YEAR)
                .setParameter("publisherYear", publisherYear)
                .getResultList();
    }

    @Override
    public List<Book> findByAuthorId(Long authorId) {
        LOGGER.info("findByAuthorId({})", authorId);
        return namedQuery(Book.QUERY_FIND_BY_AUTHOR_ID)
                .setParameter("authorId", authorId)
                .getResultList();
    }

    @Override
    public Integer getRatingByAuthorId(Long authorId) {
        LOGGER.info("getRatingByAuthorId({})", authorId);
        Object obj = entityManager.createNamedQuery(Book.QUERY_GET_RATING_BY_AUTHOR_ID)
                .setParameter("authorId", authorId).getResultList().get(0);
        Integer rating;
        if (obj == null) {
            rating = null;
        } else {
            rating = ((Number) obj).intValue();
        }
        return rating;
    }

    @Override
    public List<Book> findAll() {
        LOGGER.info("findAll()");
        return namedQuery(Book.QUERY_FIND_ALL).getResultList();
    }

    @Override
    public List<Book> findByRating(int rating) {
        LOGGER.info("findByRating({})", rating);
        return namedQuery(Book.QUERY_FIND_BY_RATING)
                .setParameter("rating", rating)
                .getResultList();
    }

    @Override
    public List<Book> findByCreateDate(Date createDate) {
        LOGGER.info("findByCreateDate({})", createDate);
        return namedQuery(Book.QUERY_FIND_BY_CREATE_DATE)
                .setParameter("createDate", createDate)
                .getResultList();
    }
}
