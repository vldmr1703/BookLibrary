package com.softserve.BookLibrary.DAO.facade;

import org.richfaces.model.ArrangeableState;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Generic interface for read operations
 *
 * @param <T> - entity
 */
public interface GenericFacade<T> {

    /**
     * This method returns EntityManager instance
     *
     * @return
     */
    EntityManager getEntityManager();

    /**
     * This method returns instance of entity class
     *
     * @return
     */
    Class<T> getEntityClass();

    /**
     * This method returns TypedQuery for entity by its named query
     *
     * @param queryName - entity's named query
     * @return
     */
    TypedQuery<T> namedQuery(String queryName);

    /**
     * This method returns TypedQuery for entity by query
     *
     * @param queryString - query
     * @return
     */
    TypedQuery<T> query(String queryString);

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
