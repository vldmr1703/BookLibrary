package com.softserve.BookLibrary.jsf;

import com.softserve.BookLibrary.manager.BookManager;
import com.softserve.BookLibrary.entity.Author;
import com.softserve.BookLibrary.manager.AuthorManager;
import org.richfaces.component.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ManagedBean(name = "authorsArrayAction")
@SessionScoped
public class AuthorArraysAction extends GenericAction<Author> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorArraysAction.class);


    private UIComponent deleteCheckedButton;

    @EJB
    private AuthorManager authorManager;

    @EJB
    private BookManager bookManager;

    public AuthorArraysAction() {
        sortOrders.put("firstName", SortOrder.unsorted);
        sortOrders.put("secondName", SortOrder.unsorted);
        sortOrders.put("rating", SortOrder.unsorted);
        sortOrders.put("createDate", SortOrder.unsorted);
    }

    public DataModel<Author> getDataModel() {
        LOGGER.info("getDataModel()");
        return new DataModel<>(authorManager);
    }

    public List<Author> getAllAuthors() {
        LOGGER.info("getAllAuthors()");
        return authorManager.getAllAuthors();
    }

    public UIComponent getDeleteCheckedButton() {
        LOGGER.info("getDeleteCheckedButton()");
        return deleteCheckedButton;
    }

    public void setDeleteCheckedButton(UIComponent deleteCheckedButton) {
        LOGGER.info("setDeleteCheckedButton({})", deleteCheckedButton);
        this.deleteCheckedButton = deleteCheckedButton;
    }

    public void deleteSelected() {
        LOGGER.info("deleteSelected()");
        Set<Long> notValidIds = new HashSet<>();
        getSelectedIds().forEach(id -> {
            Author author = authorManager.findById(id);
            if (bookManager.findByAuthorId(id).isEmpty()) {
                authorManager.deleteAuthor(author);
                LOGGER.info(author.toString());
            } else {
                notValidIds.add(id);
                FacesMessage message = new FacesMessage(String.format("Author %s %s has books yet, you can't deleteBook it", author.getFirstName(), author.getSecondName()));
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(deleteCheckedButton.getClientId(context), message);
            }
        });
        setSelectedIds(notValidIds);
    }

    @Override
    public void selectAll() {
        LOGGER.info("selectAll()");
        if (selectAll) {
            selectAll = false;
            setSelectedIds(new HashSet<>());
        } else {
            setSelectAll(true);
            setSelectedIds(authorManager.getAllAuthors().stream().map(Author::getId).collect(Collectors.toSet()));
        }
    }

    public List<Author> getSelectedAuthors() {
        LOGGER.info("getSelectedAuthors({})");
        return getSelectedIds().stream().map(id -> authorManager.findById(id)).collect(Collectors.toList());
    }
}
