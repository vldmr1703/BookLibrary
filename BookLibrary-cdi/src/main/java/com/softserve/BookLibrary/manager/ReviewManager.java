package com.softserve.BookLibrary.manager;

import com.softserve.BookLibrary.DAO.facade.ReviewFacade;
import com.softserve.BookLibrary.DAO.home.ReviewHome;
import com.softserve.BookLibrary.entity.Author;
import com.softserve.BookLibrary.entity.Book;
import com.softserve.BookLibrary.entity.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.*;
import java.util.List;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ReviewManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewManager.class);

    @EJB
    private ReviewFacade reviewFacade;

    @EJB
    private ReviewHome reviewHome;

    @EJB
    private BookManager bookManager;

    @EJB
    private AuthorManager authorManager;

    public List<Review> findByBookId(Long bookId) {
        LOGGER.info("findByBookId({})", bookId);
        return reviewFacade.findByBookId(bookId);
    }

    public List<Review> getAllReviews() {
        LOGGER.info("getAllReviews()");
        return reviewFacade.findAll();
    }

    public List<Review> findByRating(int rating) {
        LOGGER.info("findByRating({})", rating);
        return reviewFacade.findByRating(rating);
    }

    public void addReview(Review review) {
        LOGGER.info("addReview({})", review);
        reviewHome.add(review);
        Book book = bookManager.findById(review.getBook().getId());
        Integer bookRating = reviewFacade.getRatingByBookId(book.getId());
        book.setRating(bookRating);
        bookManager.updateBook(book);
        List<Author> authors = book.getAuthors();
        for (Author author : authors) {
            Integer authorRating = bookManager.getRatingByAuthorId(author.getId());
            author.setRating(authorRating);
            authorManager.updateAuthor(author);
        }
    }
}
