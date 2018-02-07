package com.softserve.BookLibrary.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.softserve.BookLibrary.validator.ValidationConstants.*;

@Entity
@Table
@NamedQueries({
        @NamedQuery(name = Review.QUERY_FIND_ALL, query = "select r from Review r"),
        @NamedQuery(name = Review.QUERY_FIND_BY_COMMENT, query = "select r from Review r where r.commentText =:comment"),
        @NamedQuery(name = Review.QUERY_FIND_BY_COMMENTER_NAME, query = "select r from Review r where r.commenterName =:commenterName"),
        @NamedQuery(name = Review.QUERY_FIND_BY_BOOK_ID, query = "select r from Review r where r.book.id =:bookId"),
        @NamedQuery(name = Review.QUERY_FIND_BY_RATING, query = "select r from Review r where r.rating =:rating"),
        @NamedQuery(name = Review.QUERY_FIND_BY_CREATE_DATE, query = "select r from Review r where r.createDate =:createDate"),
        @NamedQuery(name = Review.QUERY_GET_RATING_BY_BOOK_ID, query = "select avg(r.rating) from Review r where r.book.id =:bookId"),
        @NamedQuery(name = Review.QUERY_FIND_BY_PAGE, query = "from Review")
})
public class Review extends SuperEntity {

    public static final String QUERY_FIND_ALL = "Review.findAll";
    public static final String QUERY_FIND_BY_COMMENT = "Review.findByComment";
    public static final String QUERY_FIND_BY_COMMENTER_NAME = "Review.findByCommenterName";
    public static final String QUERY_FIND_BY_BOOK_ID = "Review.findByBookId";
    public static final String QUERY_FIND_BY_RATING = "Review.findByRating";
    public static final String QUERY_FIND_BY_CREATE_DATE = "Review.findByCreateDate";
    public static final String QUERY_GET_RATING_BY_BOOK_ID = "Review.getRatingByBookId";
    public static final String QUERY_FIND_BY_PAGE = "ReviewFacade.getByPage";


    @Id
    @GeneratedValue(generator = "ReviewSeq")
    @SequenceGenerator(name = "ReviewSeq", sequenceName = "REVIEW_ID_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "comment_text")
    @NotNull
    @Size(min = COMMENT_MIN_SIZE, max = COMMENT_MAX_SIZE, message = COMMENT_SIZE_MESSAGE)
    private String commentText;

    @Column(name = "commenter_name")
    @NotNull
    @Size(min = NAME_MIN_SIZE, max = NAME_MAX_SIZE, message = NAME_SIZE_MESSAGE)
    private String commenterName;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    public Review() {
    }

    public Review(String commentText, String commenterName, Book book) {
        this.commentText = commentText;
        this.commenterName = commenterName;
        this.book = book;
    }

    public Long getId() {
        return id;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getCommenterName() {
        return commenterName;
    }

    public void setCommenterName(String commenterName) {
        this.commenterName = commenterName;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", comment='" + commentText + '\'' +
                ", commenterName='" + commenterName + '\'' +
                super.toString() +
                '}';
    }
}
