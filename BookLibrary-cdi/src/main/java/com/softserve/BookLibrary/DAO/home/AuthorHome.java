package com.softserve.BookLibrary.DAO.home;

import com.softserve.BookLibrary.entity.Author;

import javax.ejb.Local;

/**
 * AuthorHome interface for Author create, update, delete operations
 */
@Local
public interface AuthorHome extends GenericHome<Author> {
}
