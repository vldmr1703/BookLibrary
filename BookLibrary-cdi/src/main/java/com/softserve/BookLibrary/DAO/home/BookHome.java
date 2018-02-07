package com.softserve.BookLibrary.DAO.home;

import com.softserve.BookLibrary.entity.Book;

import javax.ejb.Local;

/**
 * BookHome interface for Book create, update, delete operations
 */
@Local
public interface BookHome extends GenericHome<Book> {
}
