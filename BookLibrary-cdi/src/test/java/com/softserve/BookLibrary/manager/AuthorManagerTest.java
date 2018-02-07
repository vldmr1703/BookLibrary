package com.softserve.BookLibrary.manager;

import com.softserve.BookLibrary.DAO.facade.ReviewFacade;
import com.softserve.BookLibrary.DAO.home.ReviewHome;
import com.softserve.BookLibrary.entity.Author;
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

public class AuthorManagerTest extends Arquillian {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorManagerTest.class);

    @EJB
    private AuthorManager authorManager;

    @Deployment
    public static Archive<?> createDeployment() {
        File[] files = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeDependencies().resolve()
                .withTransitivity().asFile();
        WebArchive war = ShrinkWrap.create(WebArchive.class, "AuthorManager.war")
                .addAsLibraries(files)
                .addPackages(true, Author.class.getPackage())
                .addPackages(true, AuthorManager.class.getPackage())
                .addPackages(true, ReviewFacade.class.getPackage())
                .addPackages(true, ReviewHome.class.getPackage())
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        return war;
    }

    @Test
    @UsingDataSet("datasets/author/using.xml")
    @ShouldMatchDataSet(value = "datasets/author/expected-find.xml")
    public void findById() {
        Author author = authorManager.findById(1001L);
        LOGGER.info(author.toString());
    }

    @Test
    @UsingDataSet("datasets/author/using.xml")
    @ShouldMatchDataSet(value = "datasets/author/expected-add.xml", excludeColumns = {"id", "create_date"})
    public void add() {
        Author author = new Author("Forth", "Author");
        authorManager.addAuthor(author);
        LOGGER.info(authorManager.getAllAuthors().toString());
    }

    @Test
    @UsingDataSet("datasets/author/using.xml")
    @ShouldMatchDataSet(value = "datasets/author/expected-delete.xml")
    public void delete() {
        authorManager.deleteAuthor(authorManager.findById(1002L));
        authorManager.deleteAuthor(authorManager.findById(1003L));
        LOGGER.info(authorManager.getAllAuthors().toString());
    }

    @Test
    @UsingDataSet("datasets/author/using.xml")
    @ShouldMatchDataSet(value = "datasets/author/expected-update.xml")
    public void update() {
        Author author = authorManager.findById(1003L);
        author.setFirstName("Updated");
        authorManager.updateAuthor(author);
        LOGGER.info(authorManager.findById(1003L).toString());
    }
}