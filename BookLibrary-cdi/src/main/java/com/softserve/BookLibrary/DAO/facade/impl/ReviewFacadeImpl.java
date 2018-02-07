package com.softserve.BookLibrary.DAO.facade.impl;

import com.softserve.BookLibrary.DAO.facade.ReviewFacade;
import com.softserve.BookLibrary.entity.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import java.util.Date;
import java.util.List;

@Stateless
public class ReviewFacadeImpl extends GenericFacadeImpl<Review> implements ReviewFacade {


    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewFacadeImpl.class);

    public ReviewFacadeImpl() {
        super(Review.class);
    }

    @Override
    public List<Review> findByComment(String comment) {
        LOGGER.info("findByComment({})", comment);
        return namedQuery(Review.QUERY_FIND_BY_COMMENT)
                .setParameter("comment", comment)
                .getResultList();
    }

    @Override
    public List<Review> findByCommenterName(String commenterName) {
        LOGGER.info("findByCommenterName({})", commenterName);
        return namedQuery(Review.QUERY_FIND_BY_COMMENTER_NAME)
                .setParameter("commenterName", commenterName)
                .getResultList();
    }

    @Override
    public List<Review> findByBookId(Long bookId) {
        LOGGER.info("findByBookId({})", bookId);
        return namedQuery(Review.QUERY_FIND_BY_BOOK_ID)
                .setParameter("bookId", bookId)
                .getResultList();
    }

    @Override
    public Integer getRatingByBookId(Long bookId) {
        LOGGER.info("getRatingByBookId({})", bookId);
        Object obj = entityManager.createNamedQuery(Review.QUERY_GET_RATING_BY_BOOK_ID)
                .setParameter("bookId", bookId).getResultList().get(0);
        Integer rating;
        if (obj == null) {
            rating = null;
        } else {
            rating = ((Number) obj).intValue();
        }
        return rating;
    }

    @Override
    public List<Review> findAll() {
        LOGGER.info("findAll()");
        return namedQuery(Review.QUERY_FIND_ALL).getResultList();
    }

    @Override
    public List<Review> findByRating(int rating) {
        LOGGER.info("findByRating({})", rating);
        return namedQuery(Review.QUERY_FIND_BY_RATING)
                .setParameter("rating", rating)
                .getResultList();
    }

    @Override
    public List<Review> findByCreateDate(Date createDate) {
        LOGGER.info("findByCreateDate({})", createDate);
        return namedQuery(Review.QUERY_FIND_BY_CREATE_DATE)
                .setParameter("createDate", createDate)
                .getResultList();
    }
}
