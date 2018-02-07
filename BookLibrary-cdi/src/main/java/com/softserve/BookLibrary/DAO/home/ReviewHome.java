package com.softserve.BookLibrary.DAO.home;

import com.softserve.BookLibrary.entity.Review;

import javax.ejb.Local;

/**
 * ReviewHome interface for Review create, update, delete operations
 */
@Local
public interface ReviewHome extends GenericHome<Review> {
}
