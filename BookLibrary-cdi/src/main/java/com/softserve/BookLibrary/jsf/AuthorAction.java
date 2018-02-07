package com.softserve.BookLibrary.jsf;

import com.softserve.BookLibrary.entity.Author;
import com.softserve.BookLibrary.manager.AuthorManager;
import com.softserve.BookLibrary.manager.BookManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.util.List;
import java.util.stream.Collectors;

@ManagedBean(name = "authorAction")
@SessionScoped
public class AuthorAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorAction.class);

    @EJB
    private AuthorManager authorManager;

    @EJB
    private BookManager bookManager;

    private Long authorId;

    private Author author;

    private UIComponent deleteButton;

    private List<Long> bookIds;

    public AuthorAction() {
        author = new Author();
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        LOGGER.info("setAuthorId({})", authorId);
        author = authorManager.findById(authorId);
        bookIds = bookManager.findByAuthorId(authorId).stream().map(book -> book.getId()).collect(Collectors.toList());
        this.authorId = authorId;
    }

    public Author getAuthor() {
        LOGGER.info("getAuthor()");
        return author;
    }

    public UIComponent getDeleteButton() {
        LOGGER.info("getDeleteButton()");
        return deleteButton;
    }

    public List<Long> getBookIds() {
        LOGGER.info("getBookId()");
        return bookIds;
    }

    public void setBookIds(List<Long> bookIds) {
        LOGGER.info("setBookIds({})", bookIds);
        this.bookIds = bookIds;
    }

    public void setDeleteButton(UIComponent deleteButton) {
        LOGGER.info("setDeleteButton({})", deleteButton);
        this.deleteButton = deleteButton;
    }

    public void deleteAuthor() {
        LOGGER.info("deleteAuthor()");
        if (bookManager.findByAuthorId(authorId).isEmpty()) {
            authorManager.deleteAuthor(author);
        } else {
            FacesMessage message = new FacesMessage("Author has books yet, you can't deleteBook it");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(deleteButton.getClientId(context), message);
        }
    }

    public void resetValues() {
        LOGGER.info("resetValues()");
        authorId = null;
        author = new Author();
        bookIds = null;
    }

    public void updateAuthor() {
        LOGGER.info("updateAuthor()");
        author.setBooks(bookIds.stream().map(id -> bookManager.findById(id)).collect(Collectors.toList()));
        authorManager.updateAuthor(author);
        authorManager.updateRating(author);
    }

    public void addAuthor() {
        LOGGER.info("addAuthor()");
        author.setBooks(bookIds.stream().map(id -> bookManager.findById(id)).collect(Collectors.toList()));
        authorManager.addAuthor(author);
    }
}
