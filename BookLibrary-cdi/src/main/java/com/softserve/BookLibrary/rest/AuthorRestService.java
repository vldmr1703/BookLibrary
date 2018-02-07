package com.softserve.BookLibrary.rest;

import com.softserve.BookLibrary.manager.AuthorManager;
import com.softserve.BookLibrary.entity.Author;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/author")
public class AuthorRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorRestService.class);

    @EJB
    private AuthorManager authorManager;

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        LOGGER.info("getById({})", id);
        try {
            return Response.status(Response.Status.OK).entity(authorManager.findById(id)).build();
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
            return Response.status(Response.Status.OK).entity(authorManager.getAllAuthors()).build();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
    }

    @POST
    @Path("/add")
    public Response addAuthor(@Valid Author author) {
        LOGGER.info("addAuthor({})", author);
        try {
            authorManager.addAuthor(author);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
    }

    @PUT
    @Path("/update")
    public Response updateAuthor(@Valid Author author) {
        LOGGER.info("updateAuthor({})", author);
        try {
            authorManager.updateAuthor(author);
            authorManager.updateRating(author);
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
            authorManager.deleteAuthor(authorManager.findById(id));
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
    }
}
