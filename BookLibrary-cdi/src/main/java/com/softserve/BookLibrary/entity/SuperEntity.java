package com.softserve.BookLibrary.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;

import static com.softserve.BookLibrary.validator.ValidationConstants.*;

@MappedSuperclass
public abstract class SuperEntity implements Serializable {

    @Column(name = "rating")
    @Min(value = MIN_RATING, message = RATING_SIZE_MESSAGE)
    @Max(value = MAX_RATING, message = RATING_SIZE_MESSAGE)
    private Integer rating;

    @Column(name = "create_date")
    @Temporal(TemporalType.DATE)
    private Date createDate;

    public abstract Long getId();

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @PrePersist
    public void prePersist() {
        setCreateDate(new Date());
    }

    @Override
    public String toString() {
        return  ", rating=" + rating +
                ", createDate=" + createDate;
    }
}
