package com.softserve.BookLibrary.DAO.facade;

import com.softserve.BookLibrary.entity.Review;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

/**
 * ReviewFacade interface for facade and read operations
 */
@Local
public interface ReviewFacade  extends GenericFacade<Review> {

    /**
     * This method returns List of Reviews by comment
     * @param comment - comment text
     * @return
     */
    public List<Review> findByComment(String comment);

    /**
     * This method returns List of Reviews by commenterName
     * @param commenterName - name of commenter
     * @return
     */
    public List<Review> findByCommenterName(String commenterName);

    /**
     * This method returns List of Reviews which belongs to Book with bookId
     * @param bookId - book's primary key
     * @return
     */
    public List<Review> findByBookId(Long bookId);

    /**
     * This method returns rating of Book, which calculates as average of his reviews ratings
     * @param bookId - book's pri,ary key
     * @return
     */
    public Integer getRatingByBookId(Long bookId);

    /**
     * This method returns List of all Reviews
     * @return
     */
    public List<Review> findAll();

    /**
     * This method returns List of Reviews by rating
     * @param rating - review's rating
     * @return
     */
    public List<Review> findByRating(int rating);

    /**
     * This method returns List of Reviews by createDate
     * @param createDate - review's createDate
     * @return
     */
    public List<Review> findByCreateDate(Date createDate);
}
