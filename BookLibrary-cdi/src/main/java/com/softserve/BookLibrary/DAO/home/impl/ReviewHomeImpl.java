package com.softserve.BookLibrary.DAO.home.impl;

import com.softserve.BookLibrary.DAO.home.ReviewHome;
import com.softserve.BookLibrary.entity.Review;

import javax.ejb.Stateless;

@Stateless
public class ReviewHomeImpl extends GenericHomeImpl<Review> implements ReviewHome {
    public ReviewHomeImpl() {
        super(Review.class);
    }
}
