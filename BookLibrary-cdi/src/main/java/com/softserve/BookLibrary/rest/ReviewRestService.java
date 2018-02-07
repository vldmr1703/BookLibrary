package com.softserve.BookLibrary.rest;

import com.softserve.BookLibrary.manager.ReviewManager;
import com.softserve.BookLibrary.entity.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/review")
public class ReviewRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewRestService.class);

    @EJB
    private ReviewManager reviewManager;

    @GET
    @Path("/all")
    public Response getAll() {
        LOGGER.info("getAll()");
        try {
            return Response.status(Response.Status.OK).entity(reviewManager.getAllReviews()).build();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("/all/book/{bookId}")
    public Response getReviewByBookId(@PathParam("bookId") Long bookId) {
        LOGGER.info("getReviewByBookId({})", bookId);
        try {
            return Response.status(Response.Status.OK).entity(reviewManager.findByBookId(bookId)).build();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
    }

    @POST
    @Path("/add")
    public Response addReview(@Valid Review review) {
        LOGGER.info("addReview({})", review);
        try {
            reviewManager.addReview(review);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
    }
}
