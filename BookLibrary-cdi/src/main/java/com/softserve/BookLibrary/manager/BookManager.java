package com.softserve.BookLibrary.manager;

import com.softserve.BookLibrary.DAO.facade.BookFacade;
import com.softserve.BookLibrary.DAO.home.BookHome;
import com.softserve.BookLibrary.entity.Author;
import com.softserve.BookLibrary.entity.Book;
import org.richfaces.model.ArrangeableState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.*;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BookManager implements GenericManager<Book> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookManager.class);

    @EJB
    private BookFacade bookFacade;

    @EJB
    private BookHome bookHome;

    @EJB
    private AuthorManager authorManager;

    @Override
    public Book findById(Long id) {
        LOGGER.info("findById({})", id);
        return bookFacade.findById(id);
    }

    public Book findByIsbn(String isbn) throws NoResultException {
        LOGGER.info("findByIsbn({})", isbn);
        return bookFacade.findByIsbn(isbn);
    }

    public List<Book> findByAuthorId(Long authorId) {
        LOGGER.info("findByAuthorId({})", authorId);
        return bookFacade.findByAuthorId(authorId);
    }

    public Integer getRatingByAuthorId(Long authorId) {
        LOGGER.info("getRatingByAuthorId({})", authorId);
        return bookFacade.getRatingByAuthorId(authorId);
    }

    public List<Book> getAllBooks() {
        LOGGER.info("getAllBooks()");
        return bookFacade.findAll();
    }

    public List<Book> findByRating(int rating) {
        LOGGER.info("findByRating({})", rating);
        return bookFacade.findByRating(rating);
    }

    public void addBook(Book book) {
        LOGGER.info("addBook({})", book);
        bookHome.add(book);
    }

    public List<Long> updateBook(Book book) {
        LOGGER.info("updateBook({})", book);
        List<Long> oldAuthorIds = bookFacade.findById(book.getId()).getAuthors().stream().map(Author::getId).collect(Collectors.toList());
        bookHome.update(book);
        return oldAuthorIds;
    }

    public List<Long> deleteBook(Book book) {
        LOGGER.info("deleteBook({})", book);
        List<Long> authorIds = bookFacade.findById(book.getId()).getAuthors().stream().map(Author::getId).collect(Collectors.toList());
        bookHome.delete(book);
        return authorIds;
    }

    @Override
    public int getRowCount() {
        LOGGER.info("getRowCount()");
        return bookFacade.getRowCount();
    }

    @Override
    public List<Book> getData(int firstRow, int numberOfRows) {
        LOGGER.info("getData({},{})", firstRow, numberOfRows);
        return bookFacade.getData(firstRow, numberOfRows);
    }

    @Override
    public void setArrangeableState(ArrangeableState arrangeableState) {
        LOGGER.info("setArrangeableState({})", arrangeableState);
        bookFacade.setArrangeableState(arrangeableState);
    }
}
