package com.softserve.BookLibrary.DAO.facade;

import com.softserve.BookLibrary.entity.Author;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

/**
 * AuthorFacade interface for facade and read operations
 */
@Local
public interface AuthorFacade extends GenericFacade<Author> {

    /**
     * This method returns List of Authors by firstName
     * @param firstName - author's firstName
     * @return
     */
    public List<Author> findByFirstName(String firstName);

    /**
     * This method returns List of Authors by secondName
     * @param secondName - author's secondName
     * @return
     */
    public List<Author> findBySecondName(String secondName);

    /**
     * This method returns List of Authors by firstName and secondName
     * @param firstName - author's firstName
     * @param secondName - author's secondName
     * @return
     */
    public List<Author> findByFirstNameAndSecondName(String firstName, String secondName);

    /**
     * This method returns List of all Authors
     * @return
     */

    public List<Author> findAll();

    /**
     * This method returns List of Authors by rating
     * @param rating - author's rating
     * @return
     */
    public List<Author> findByRating(int rating);

    /**
     * This method returns List of Authors by createDate
     * @param createDate - author's createDate
     * @return
     */
    public List<Author> findByCreateDate(Date createDate);
}
