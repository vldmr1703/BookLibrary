package com.softserve.BookLibrary.DAO.facade;

import com.softserve.BookLibrary.entity.Book;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;


/**
 * BookFacade interface for facade and read operations
 */
@Local
public interface BookFacade  extends GenericFacade<Book>  {

    /**
     * This method returns List of Books by name
     * @param name - book's name
     * @return
     */
    public List<Book> findByName(String name);

    /**
     * This method returns Book by isbn
     * @param isbn - book's unique isbn
     * @return
     */
    public Book findByIsbn(String isbn);

    /**
     * This method returns List of Books by publisher
     * @param publisher - book's publisher
     * @return
     */
    public List<Book> findByPublisher(String publisher);

    /**
     * This method returns List of Books by publishedYear
     * @param publisherYear - book's publishedYear
     * @return
     */
    public List<Book> findByPublisherYear(int publisherYear);

    /**
     * This method returns List of Books which belongs to Author with authorId
     * @param authorId - author's primary key
     * @return
     */
    public List<Book> findByAuthorId(Long authorId);

    /**
     * This method returns rating of Author, which calculates as average of his books ratings
     * @param authorId - author's primary key
     * @return
     */
    public Integer getRatingByAuthorId(Long authorId);

    /**
     * This method returns List of all Books
     * @return
     */
    public List<Book> findAll();

    /**
     * This method returns List of Books by rating
     * @param rating - book's rating
     * @return
     */
    public List<Book> findByRating(int rating);

    /**
     * This method returns List of Books by createDate
     * @param createDate - book's createDate
     * @return
     */
    public List<Book> findByCreateDate(Date createDate);
}
