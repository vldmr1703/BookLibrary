package com.softserve.BookLibrary.jsf;

import com.softserve.BookLibrary.entity.Book;
import com.softserve.BookLibrary.manager.BookManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "reviewAction")
@SessionScoped
public class ReviewAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewAction.class);

    @EJB
    private BookManager bookManager;

    private Map<Integer, Integer> starMap;

    private List<Integer> stars = Arrays.asList(1, 2, 3, 4, 5);

    public void onLoad() {
        LOGGER.info("onLoad()");
        starMap = new HashMap<>();
        for (int i : stars) {
            starMap.put(i, bookManager.findByRating(i).size());
        }
    }

    public Map<Integer, Integer> getStarMap() {
        LOGGER.info("getStarMap()");
        return starMap;
    }

    public List<Integer> getStars() {
        LOGGER.info("getStars()");
        return stars;
    }

    public List<Book> getBooksByRating(int rating) {
        LOGGER.info("getBooksByRating({})", rating);
        return bookManager.findByRating(rating);
    }
}
