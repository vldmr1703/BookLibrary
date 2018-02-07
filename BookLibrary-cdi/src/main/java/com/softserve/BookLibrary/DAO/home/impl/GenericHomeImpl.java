package com.softserve.BookLibrary.DAO.home.impl;

import com.softserve.BookLibrary.DAO.home.GenericHome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;

public class GenericHomeImpl<T> implements GenericHome<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericHomeImpl.class);

    @PersistenceContext(unitName = "JPA", type = PersistenceContextType.TRANSACTION)
    public EntityManager entityManager;

    private Class<T> entityClass;

    public GenericHomeImpl() {
    }

    public GenericHomeImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public Class<T> getEntityClass() {
        return entityClass;
    }

    @Override
    public TypedQuery<T> namedQuery(String queryName) {
        LOGGER.info("namedQuery({})", queryName);
        return getEntityManager().createNamedQuery(queryName, entityClass);
    }

    @Override
    public TypedQuery<T> query(String queryString) {
        LOGGER.info("query({})", queryString);
        return getEntityManager().createQuery(queryString, entityClass);
    }

    @Override
    public void add(T entity) {
        LOGGER.info("add({})", entity);
        if (entity != null) {
            getEntityManager().merge(entity);
        }
        LOGGER.info(entity.toString());
    }

    @Override
    public void update(T entity) {
        LOGGER.info("update({})", entity);
        getEntityManager().merge(entity);
    }

    @Override
    public void delete(T entity) {
        LOGGER.info("delete({})", entity);
        if (entity != null) {
            getEntityManager().remove(getEntityManager().contains(entity) ? entity : getEntityManager().merge(entity));
        }
    }
}
