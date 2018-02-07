package com.softserve.BookLibrary.jsf;

import com.softserve.BookLibrary.entity.SuperEntity;

import java.util.Comparator;

class EntityComparator implements Comparator<SuperEntity> {

    public int compare(SuperEntity a, SuperEntity b){
        return a.getCreateDate().compareTo(b.getCreateDate());
    }
}
