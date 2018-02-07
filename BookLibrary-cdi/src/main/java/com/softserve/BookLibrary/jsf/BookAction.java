package com.softserve.BookLibrary.jsf;

import com.softserve.BookLibrary.entity.Author;
import com.softserve.BookLibrary.entity.Book;
import com.softserve.BookLibrary.entity.Review;
import com.softserve.BookLibrary.manager.AuthorManager;
import com.softserve.BookLibrary.manager.BookManager;
import com.softserve.BookLibrary.manager.ReviewManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;
import java.util.stream.Collectors;

@ManagedBean(name = "bookAction")
@SessionScoped
public class BookAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookAction.class);

    @EJB
    private BookManager bookManager;

    @EJB
    private AuthorManager authorManager;

    @EJB
    private ReviewManager reviewManager;

    private Long bookId;

    private Book book;

    private Review review = new Review();

    private List<Long> authorIds;

    private boolean isEmptyReviewsList;

    public BookAction() {
        book = new Book();
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        LOGGER.info("setBookId({})", bookId);
        book = bookManager.findById(bookId);
        authorIds = book.getAuthors().stream().map(Author::getId).collect(Collectors.toList());
        this.bookId = bookId;
    }

    public Book getBook() {
        LOGGER.info("getBook()");
        return book;
    }

    public Review getReview() {
        LOGGER.info("getReview()");
        return review;
    }

    public void setReview(Review review) {
        LOGGER.info("setReview({})", review);
        this.review = review;
    }

    public List<Long> getAuthorIds() {
        LOGGER.info("getAuthorIds()");
        return authorIds;
    }

    public void setAuthorIds(List<Long> authorIds) {
        LOGGER.info("setAuthorIds({})", authorIds);
        this.authorIds = authorIds;
    }

    public boolean isEmptyReviewsList() {
        LOGGER.info("isEmptyReviewsList()");
        return isEmptyReviewsList;
    }

    public void deleteBook() {
        LOGGER.info("deleteBook()");
        List<Long> authorIds = bookManager.deleteBook(book);
        authorManager.updateAuthorsRatingAfterBookDelete(authorIds);
    }

    public void updateBook() {
        LOGGER.info("updateBook()");
        book.setAuthors(authorIds.stream().map(id -> authorManager.findById(id)).collect(Collectors.toList()));
        List<Long> authorIds = bookManager.updateBook(book);
        authorManager.updateAuthorsRatingAfterBookUpdate(book, authorIds);
    }

    public void resetValues() {
        LOGGER.info("resetValues()");
        bookId = null;
        book = new Book();
        authorIds = null;
    }

    public void resetReview() {
        LOGGER.info("resetReview()");
        review = new Review();
    }

    public void addBook() {
        LOGGER.info("addBook()");
        book.setAuthors(authorIds.stream().map(id -> authorManager.findById(id)).collect(Collectors.toList()));
        bookManager.addBook(book);
    }

    public void addReview() {
        LOGGER.info("addReview()");
        review.setBook(book);
        LOGGER.info(review.toString());
        reviewManager.addReview(review);
        book = bookManager.findById(bookId);
    }

    public List<Review> getReviews() {
        LOGGER.info("getReviews()");
        List<Review> reviews = reviewManager.findByBookId(book.getId()).stream().sorted(new EntityComparator()).collect(Collectors.toList());
        isEmptyReviewsList = reviews.isEmpty();
        return reviews;
    }

}
