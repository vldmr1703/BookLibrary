package com.softserve.BookLibrary.rest;

import com.softserve.BookLibrary.manager.AuthorManager;
import com.softserve.BookLibrary.manager.BookManager;
import com.softserve.BookLibrary.entity.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/book")
public class BookRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookRestService.class);

    @EJB
    private BookManager bookManager;

    @EJB
    private AuthorManager authorManager;

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        LOGGER.info("getById({})", id);
        try {
            return Response.status(Response.Status.OK).entity(bookManager.findById(id)).build();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("/all")
    public Response getAll() {
        LOGGER.info("getAll()");
        try {
            return Response.status(Response.Status.OK).entity(bookManager.getAllBooks()).build();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
    }

    @POST
    @Path("/add")
    public Response addBook(@Valid Book book) {
        LOGGER.info("addBook({})", book);
        try {
            bookManager.addBook(book);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
    }

    @PUT
    @Path("/update")
    public Response updateBook(@Valid Book book) {
        LOGGER.info("updateBook({})", book);
        try {
            authorManager.updateAuthorsRatingAfterBookUpdate(book, bookManager.updateBook(book));
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteAuthor(@PathParam("id") Long id) {
        LOGGER.info("deleteAuthor({})", id);
        try {
            authorManager.updateAuthorsRatingAfterBookDelete(bookManager.deleteBook(bookManager.findById(id)));
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
    }
}
