package com.softserve.BookLibrary.jsf;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "ratingAction")
@SessionScoped
public class RatingAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(RatingAction.class);

    private static final int STAR_SIZE = 5;
    private int filledStarsCount;

    public List<Integer> getFilledStars(String size) {
        LOGGER.info("getFilledStars({})", size);
        if (Strings.isNullOrEmpty(size)) {
            filledStarsCount = 0;
            return null;
        }
        int n = Integer.valueOf(size);
        filledStarsCount = n;
        List<Integer> stars = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            stars.add(i);
        }
        return stars;
    }

    public List<Integer> getEmptyStars() {
        LOGGER.info("getEmptyStars()");
        List<Integer> stars = new ArrayList<>();
        for (int i = filledStarsCount + 1; i <= STAR_SIZE; i++) {
            stars.add(i);
        }
        return stars;
    }
}
