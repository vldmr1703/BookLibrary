package com.softserve.BookLibrary.DAO.home;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Generic interface for create, update and delete operations
 *
 * @param <T> - entity
 */
public interface GenericHome<T> {

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
     * This method adds new entity to database
     *
     * @param entity - entity instance
     */
    void add(T entity);

    /**
     * This method updates entity
     *
     * @param entity - entity instance
     */
    void update(T entity);

    /**
     * This method deletes entity from database
     *
     * @param entity - entity instance
     */
    void delete(T entity);
}
