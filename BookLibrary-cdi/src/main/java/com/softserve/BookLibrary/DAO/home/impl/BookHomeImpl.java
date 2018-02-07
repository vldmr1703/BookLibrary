package com.softserve.BookLibrary.DAO.home.impl;

import com.softserve.BookLibrary.DAO.home.BookHome;
import com.softserve.BookLibrary.entity.Book;

import javax.ejb.Stateless;

@Stateless
public class BookHomeImpl extends GenericHomeImpl<Book> implements BookHome {
    public BookHomeImpl() {
        super(Book.class);
    }
}
