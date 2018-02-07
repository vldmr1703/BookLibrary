package com.softserve.BookLibrary.manager;

import com.softserve.BookLibrary.DAO.facade.AuthorFacade;
import com.softserve.BookLibrary.DAO.home.AuthorHome;
import com.softserve.BookLibrary.entity.Author;
import com.softserve.BookLibrary.entity.Book;
import com.softserve.BookLibrary.validator.AuthorHasBooks;
import org.richfaces.model.ArrangeableState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AuthorManager implements GenericManager<Author> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorManager.class);

    @EJB
    private AuthorFacade authorFacade;

    @EJB
    private AuthorHome authorHome;

    @EJB
    private BookManager bookManager;

    public Author findById(Long id) {
        LOGGER.info("findById({})", id);
        return authorFacade.findById(id);
    }

    public List<Author> getAllAuthors() {
        LOGGER.info("getAllAuthors()");
        return authorFacade.findAll();
    }

    public void addAuthor(Author author) {
        LOGGER.info("addAuthor({})", author);
        authorHome.add(author);
    }

    public void updateAuthor(Author author) {
        LOGGER.info("updateAuthor({})", author);
        authorHome.update(author);
    }

    public void updateRating(Author author) {
        LOGGER.info("updateRating({})", author);
        Integer authorRating = bookManager.getRatingByAuthorId(author.getId());
        author.setRating(authorRating);
        updateAuthor(author);
    }

    public void updateAuthorsRatingAfterBookUpdate(Book book, List<Long> oldAuthorIds) {
        LOGGER.info("updateAuthorsRatingAfterBookUpdate({},{})", book, oldAuthorIds);
        List<Long> newAuthorIds = bookManager.findById(book.getId()).getAuthors().stream().map(Author::getId).collect(Collectors.toList());
        if (!oldAuthorIds.equals(newAuthorIds)) {
            List<Long> authorsForUpdate = new ArrayList<>();
            oldAuthorIds.forEach(author -> {
                if (!newAuthorIds.contains(author)) {
                    authorsForUpdate.add(author);
                }
            });
            newAuthorIds.forEach(author -> {
                if (!oldAuthorIds.contains(author)) {
                    authorsForUpdate.add(author);
                }
            });
            updateRating(authorsForUpdate);
        }
    }

    public void updateAuthorsRatingAfterBookDelete(List<Long> authorIds) {
        LOGGER.info("updateAuthorsRatingAfterBookDelete({})", authorIds);
        updateRating(authorIds);
    }

    private void updateRating(List<Long> ids) {
        LOGGER.info("updateRating({})", ids);
        for (Long id : ids) {
            Integer authorRating = bookManager.getRatingByAuthorId(id);
            Author author = findById(id);
            author.setRating(authorRating);
            updateAuthor(author);
        }
    }

    public void deleteAuthor(@AuthorHasBooks Author author) {
        LOGGER.info("deleteAuthor({})", author);
        authorHome.delete(author);
    }

    @Override
    public int getRowCount() {
        LOGGER.info("getRowCount()");
        return authorFacade.getRowCount();
    }

    @Override
    public List<Author> getData(int firstRow, int numberOfRows) {
        LOGGER.info("getData({},{})", firstRow, numberOfRows);
        return authorFacade.getData(firstRow, numberOfRows);
    }

    @Override
    public void setArrangeableState(ArrangeableState arrangeableState) {
        LOGGER.info("setArrangeableState({})", arrangeableState);
        authorFacade.setArrangeableState(arrangeableState);
    }
}
