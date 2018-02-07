package com.softserve.BookLibrary.jsf;

import com.softserve.BookLibrary.entity.Book;
import com.softserve.BookLibrary.manager.BookManager;
import org.richfaces.component.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@ManagedBean(name = "booksArrayAction")
@SessionScoped
public class BooksArrayAction extends GenericAction<Book> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BooksArrayAction.class);

    @EJB
    private BookManager bookManager;

    public BooksArrayAction() {
        sortOrders.put("name", SortOrder.unsorted);
        sortOrders.put("publishedYear", SortOrder.unsorted);
        sortOrders.put("isbn", SortOrder.unsorted);
        sortOrders.put("publisher", SortOrder.unsorted);
        sortOrders.put("rating", SortOrder.unsorted);
        sortOrders.put("createDate", SortOrder.unsorted);
    }

    public DataModel<Book> getDataModel() {
        LOGGER.info("getDataModel()");
        return new DataModel<>(bookManager);
    }

    public List<Book> getAllBooks() {
        LOGGER.info("getAllBooks()");
        return bookManager.getAllBooks();
    }

    public List<Book> getBooksByRating(int rating) {
        LOGGER.info("getBooksByRating({})", rating);
        return bookManager.findByRating(rating).stream().sorted(new EntityComparator()).collect(Collectors.toList());
    }

    public List<Book> getBooksByAuthorId(Long authorId) {
        LOGGER.info("getBooksByAuthorId({})", authorId);
        return bookManager.findByAuthorId(authorId);
    }

    public void deleteSelected() {
        LOGGER.info("deleteSelected()");
        getSelectedIds().forEach(id -> bookManager.deleteBook(bookManager.findById(id)));
        setSelectedIds(new HashSet<>());
    }

    @Override
    public void selectAll() {
        LOGGER.info("selectAll()");
        if (selectAll) {
            selectAll = false;
            setSelectedIds(new HashSet<>());
        } else {
            setSelectAll(true);
            setSelectedIds(bookManager.getAllBooks().stream().map(Book::getId).collect(Collectors.toSet()));
        }
    }

    public List<Book> getSelectedBooks() {
        LOGGER.info("getSelectedBooks()");
        return getSelectedIds().stream().map(id -> bookManager.findById(id)).collect(Collectors.toList());
    }
}

