package com.softserve.BookLibrary.manager;

import org.richfaces.model.ArrangeableState;

import java.util.List;

/**
 * GenericManager for pagination and filter operations
 *
 * @param <T> - entity
 */
public interface GenericManager<T> {
    /**
     * This method returns entity by its id
     *
     * @param id - entity's primary key
     * @return
     */
    public T findById(Long id);

    /**
     * This method returns number of entities
     *
     * @return
     */
    public int getRowCount();

    /**
     * This method returns List of entities by range
     *
     * @param firstRow     - first row to select
     * @param numberOfRows - number of rows to select
     * @return
     */
    public List<T> getData(int firstRow, int numberOfRows);

    /**
     * This method returns ArrangeableState of entity
     *
     * @param arrangeableState - ArrangeableState of entity
     */
    public void setArrangeableState(ArrangeableState arrangeableState);
}
