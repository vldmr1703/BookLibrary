package com.softserve.BookLibrary.DAO.facade.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.softserve.BookLibrary.DAO.facade.GenericFacade;
import com.softserve.BookLibrary.entity.Author;
import com.softserve.BookLibrary.entity.Book;
import com.softserve.BookLibrary.entity.SuperEntity;
import org.richfaces.component.SortOrder;
import org.richfaces.model.ArrangeableState;
import org.richfaces.model.FilterField;
import org.richfaces.model.SortField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

public class GenericFacadeImpl<T extends SuperEntity> implements GenericFacade<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericFacadeImpl.class);

    private ArrangeableState arrangeableState;

    @PersistenceContext(unitName = "JPA")
    public EntityManager entityManager;

    public GenericFacadeImpl() {
    }

    public GenericFacadeImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    private Class<T> entityClass;

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
    public T findById(Long id) {
        LOGGER.info("findById({})", id);
        return getEntityManager().find(entityClass, id);
    }

    private CriteriaQuery<Long> createCountCriteriaQuery() {
        LOGGER.info("createCountCriteriaQuery()");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<T> root = criteriaQuery.from(entityClass);
        Expression<Boolean> filterCriteria = createFilterCriteria(criteriaBuilder, root);
        if (filterCriteria != null) {
            criteriaQuery.where(filterCriteria);
        }
        Expression<Long> count = criteriaBuilder.count(root);
        criteriaQuery.select(count);
        return criteriaQuery;
    }


    private CriteriaQuery<T> createSelectCriteriaQuery() {
        LOGGER.info("createSelectCriteriaQuery()");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);
        if (arrangeableState != null) {
            List<Order> orders = createOrders(criteriaBuilder, root);
            if (!orders.isEmpty()) {
                criteriaQuery.orderBy(orders);
            }
            Expression<Boolean> filterCriteria = createFilterCriteria(criteriaBuilder, root);
            if (filterCriteria != null) {
                criteriaQuery.where(filterCriteria);
            }
        }
        return criteriaQuery;
    }

    private List<Order> createOrders(CriteriaBuilder criteriaBuilder, Root<T> root) {
        LOGGER.info("createOrders({},{})", criteriaBuilder, root);
        List<Order> orders = Lists.newArrayList();
        List<SortField> sortFields = arrangeableState.getSortFields();
        if (sortFields != null && !sortFields.isEmpty()) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            for (SortField sortField : sortFields) {
                String propertyName = (String) sortField.getSortBy().getValue(facesContext.getELContext());
                Path<Object> expression = root.get(propertyName);
                Order jpaOrder;
                SortOrder sortOrder = sortField.getSortOrder();
                if (sortOrder == SortOrder.ascending) {
                    jpaOrder = criteriaBuilder.asc(expression);
                } else if (sortOrder == SortOrder.descending) {
                    jpaOrder = criteriaBuilder.desc(expression);
                } else {
                    throw new IllegalArgumentException(sortOrder.toString());
                }
                orders.add(jpaOrder);
            }
        }
        return orders;
    }

    private Expression<Boolean> createFilterCriteria(CriteriaBuilder criteriaBuilder, Root<T> root) {
        LOGGER.info("createFilterCriteria({},{})", criteriaBuilder, root);
        Expression<Boolean> filterCriteria = null;
        List<FilterField> filterFields = arrangeableState.getFilterFields();
        if (filterFields != null && !filterFields.isEmpty()) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            for (FilterField filterField : filterFields) {
                String propertyName = (String) filterField.getFilterExpression().getValue(facesContext.getELContext());
                Object filterValue = filterField.getFilterValue();
                Expression<Boolean> predicate;
                predicate = createFilterCriteriaForField(propertyName, filterValue, root, criteriaBuilder);
                if (predicate == null) {
                    continue;
                }
                if (filterCriteria == null) {
                    filterCriteria = predicate.as(Boolean.class);
                } else {
                    filterCriteria = criteriaBuilder.and(filterCriteria, predicate.as(Boolean.class));
                }
            }
        }
        return filterCriteria;
    }

    private Expression<Boolean> createFilterCriteriaForField(String propertyName, Object filterValue, Root<T> root,
                                                             CriteriaBuilder criteriaBuilder) {
        LOGGER.info("createFilterCriteriaForField({},{},{},{})", propertyName, filterValue, root, criteriaBuilder);
        String stringFilterValue = (String) filterValue;
        if (Strings.isNullOrEmpty(stringFilterValue)) {
            return null;
        }
        stringFilterValue = stringFilterValue.toLowerCase(arrangeableState.getLocale());
        Path<String> expression;
        Expression<Integer> locator;
        javax.persistence.criteria.Predicate predicate;
        switch (propertyName) {
            case "authors":
                Join<Book, Author> joinBookAuthors = root.join("authors");
                Path<String> expressionFirstName = joinBookAuthors.get("firstName");
                Expression<Integer> locatorFirstName = criteriaBuilder.locate(criteriaBuilder.lower(expressionFirstName), stringFilterValue, 1);
                Path<String> expressionSecondName = joinBookAuthors.get("secondName");
                Expression<Integer> locatorSecondName = criteriaBuilder.locate(criteriaBuilder.lower(expressionSecondName), stringFilterValue, 1);
                predicate = criteriaBuilder.or(criteriaBuilder.gt(locatorFirstName, 0), criteriaBuilder.gt(locatorSecondName, 0));
                break;
            case "books":
                Join<Author, Book> joinAuthorBooks = root.join("books");
                expression = joinAuthorBooks.get("name");
                locator = criteriaBuilder.locate(criteriaBuilder.lower(expression), stringFilterValue, 1);
                predicate = criteriaBuilder.gt(locator, 0);
                break;
            default:
                expression = root.get(propertyName);
                locator = criteriaBuilder.locate(criteriaBuilder.lower(expression), stringFilterValue, 1);
                predicate = criteriaBuilder.gt(locator, 0);
                break;
        }
        return predicate;
    }

    @Override
    public int getRowCount() {
        LOGGER.info("getRowCount()");
        CriteriaQuery<Long> criteriaQuery = createCountCriteriaQuery();
        return entityManager.createQuery(criteriaQuery).getSingleResult().intValue();
    }

    @Override
    public List<T> getData(int firstRow, int numberOfRows) {
        LOGGER.info("getData({},{})", firstRow, numberOfRows);
        CriteriaQuery<T> criteriaQuery = createSelectCriteriaQuery();
        TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(firstRow);
        query.setMaxResults(numberOfRows);
        return query.getResultList();
    }

    @Override
    public void setArrangeableState(ArrangeableState arrangeableState) {
        LOGGER.info("setArrangeableState({})", arrangeableState);
        this.arrangeableState = arrangeableState;
    }
}




