package com.softserve.BookLibrary.jsf.validator;

import com.softserve.BookLibrary.entity.Book;
import com.softserve.BookLibrary.manager.BookManager;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@ManagedBean(name = "isbnValidator")
@SessionScoped
public class IsbnValidator implements Validator {

    @EJB
    private BookManager bookManager;

    @ManagedProperty(value = "#{bookAction.book}")
    private Book book;

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Book bookFromDb = bookManager.findByIsbn((String) value);
        if ((bookFromDb != null && !bookFromDb.getId().equals(book.getId()) && !bookFromDb.getIsbn().equals(book.getIsbn()))) {
            FacesMessage message = new FacesMessage("Isbn is not unique");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
}
