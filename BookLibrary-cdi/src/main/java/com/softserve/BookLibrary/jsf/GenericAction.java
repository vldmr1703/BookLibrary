package com.softserve.BookLibrary.jsf;

import com.google.common.collect.Maps;
import com.softserve.BookLibrary.entity.SuperEntity;
import org.richfaces.component.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class GenericAction<T extends SuperEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericAction.class);

    Map<String, SortOrder> sortOrders = Maps.newHashMapWithExpectedSize(1);
    private Map<String, String> filterValues = Maps.newHashMap();
    private String sortProperty;
    private Set<Long> selectedIds = new HashSet<>();
    protected boolean selectAll;

    public void toggleSort() {
        LOGGER.info("toggleSort()");
        for (Map.Entry<String, SortOrder> entry : sortOrders.entrySet()) {
            SortOrder newOrder;
            if (entry.getKey().equals(sortProperty)) {
                if (entry.getValue() == SortOrder.ascending) {
                    newOrder = SortOrder.descending;
                } else {
                    newOrder = SortOrder.ascending;
                }
            } else {
                newOrder = SortOrder.unsorted;
            }
            entry.setValue(newOrder);
        }
    }

    public Map<String, SortOrder> getSortOrders() {
        LOGGER.info("getSortOrders()");
        return sortOrders;
    }

    public Map<String, String> getFilterValues() {
        LOGGER.info("getFilterValues()");
        return filterValues;
    }

    public String getSortProperty() {
        LOGGER.info("getSortProperty()");
        return sortProperty;
    }

    public void setSortProperty(String sortProperty) {
        LOGGER.info("setSortProperty({})", sortProperty);
        this.sortProperty = sortProperty;
    }


    public Set<Long> getSelectedIds() {
        LOGGER.info("getSelectedIds()");
        return selectedIds;
    }

    public void setSelectedIds(Set<Long> selectedIds) {
        LOGGER.info("setSelectedIds({})", selectedIds);
        this.selectedIds = selectedIds;
    }

    public void select(Long id) {
        LOGGER.info("select({})", id);
        if (selectedIds.contains(id)) {
            selectedIds.remove(id);
        } else {
            selectedIds.add(id);
        }
    }

    public boolean isSelectAll() {
        LOGGER.info("isSelectAll()");
        return selectAll;
    }

    public void setSelectAll(boolean selectAll) {
        LOGGER.info("setSelectAll({})", selectAll);
        this.selectAll = selectAll;
    }

    public abstract void selectAll();
}
