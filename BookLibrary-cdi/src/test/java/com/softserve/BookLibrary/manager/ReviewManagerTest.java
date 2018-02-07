package com.softserve.BookLibrary.manager;

import com.softserve.BookLibrary.DAO.facade.ReviewFacade;
import com.softserve.BookLibrary.DAO.home.ReviewHome;
import com.softserve.BookLibrary.entity.Review;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import javax.ejb.EJB;
import java.io.File;
import java.util.List;

public class ReviewManagerTest extends Arquillian {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewManagerTest.class);

    @EJB
    private ReviewManager reviewManager;

    @EJB
    private BookManager bookManager;

    @Deployment
    public static Archive<?> createDeployment() {
        File[] files = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeDependencies().resolve()
                .withTransitivity().asFile();
        WebArchive war = ShrinkWrap.create(WebArchive.class, "ReviewManager.war")
                .addAsLibraries(files)
                .addPackages(true, Review.class.getPackage())
                .addPackages(true, ReviewManager.class.getPackage())
                .addPackages(true, ReviewFacade.class.getPackage())
                .addPackages(true, ReviewHome.class.getPackage())
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        return war;
    }

    @Test
    @UsingDataSet("datasets/review/using.xml")
    @ShouldMatchDataSet(value = "datasets/review/expected-find.xml")
    public void findByBookId() {
        List<Review> reviews = reviewManager.findByBookId(1001L);
        LOGGER.info(reviews.toString());
    }

    @Test
    @UsingDataSet("datasets/review/using.xml")
    @ShouldMatchDataSet(value = "datasets/review/expected-add.xml", excludeColumns = {"id", "create_date"})
    public void add() {
        Review review = new Review("New", "reviewer_4", bookManager.findById(1001L));
        reviewManager.addReview(review);
        LOGGER.info(reviewManager.getAllReviews().toString());
    }
}
