package com.softserve.BookLibrary.DAO.home.impl;

import com.softserve.BookLibrary.DAO.home.AuthorHome;
import com.softserve.BookLibrary.entity.Author;

import javax.ejb.Stateless;

@Stateless
public class AuthorHomeImpl extends GenericHomeImpl<Author> implements AuthorHome {
    public AuthorHomeImpl() {
        super(Author.class);
    }
}
