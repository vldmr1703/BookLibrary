package com.softserve.BookLibrary.manager;

import com.softserve.BookLibrary.DAO.facade.ReviewFacade;
import com.softserve.BookLibrary.DAO.home.ReviewHome;
import com.softserve.BookLibrary.entity.Author;
import com.softserve.BookLibrary.entity.Book;
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
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.ejb.EJB;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookManagerTest extends Arquillian {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookManagerTest.class);

    @EJB
    private BookManager bookManager;

    @EJB
    private AuthorManager authorManager;

    @Deployment
    public static Archive<?> createDeployment() {
        File[] files = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeDependencies().resolve()
                .withTransitivity().asFile();
        WebArchive war = ShrinkWrap.create(WebArchive.class, "BookManager.war")
                .addAsLibraries(files)
                .addPackages(true, Book.class.getPackage())
                .addPackages(true, Author.class.getPackage())
                .addPackages(true, AuthorManager.class.getPackage())
                .addPackages(true, BookManager.class.getPackage())
                .addPackages(true, ReviewFacade.class.getPackage())
                .addPackages(true, ReviewHome.class.getPackage())
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        return war;
    }

    @Test
    @UsingDataSet("datasets/book/using.xml")
    @ShouldMatchDataSet(value = "datasets/book/expected-find.xml")
    public void findById() {
        Book book = bookManager.findById(1001L);
        LOGGER.info(book.toString());
    }

    @Test
    @UsingDataSet("datasets/book/using.xml")
    @ShouldMatchDataSet(value = "datasets/book/expected-find.xml")
    public void findByIsbn() {
        Book book = bookManager.findByIsbn("978-0-590-52068-4");
        LOGGER.info(book.toString());
    }

    @Test
    @UsingDataSet("datasets/book/using.xml")
    @ShouldMatchDataSet(value = "datasets/book/expected-find.xml")
    public void findByRating() {
        List<Book> books = bookManager.findByRating(1);
        Assert.assertEquals(books.size(), 2);
        LOGGER.info(books.toString());
    }

    @Test
    @UsingDataSet("datasets/book/using.xml")
    @ShouldMatchDataSet(value = "datasets/book/expected-add.xml", excludeColumns = {"id", "create_date"})
    public void add() {
        Book book = new Book("Forth Book", 2015, "978-0-590-22018-4", "Pablo");
        bookManager.addBook(book);
        LOGGER.info(bookManager.getAllBooks().toString());
    }

    @Test
    @UsingDataSet("datasets/book/using.xml")
    @ShouldMatchDataSet(value = "datasets/book/expected-delete.xml")
    public void delete() {
        bookManager.deleteBook(bookManager.findById(1002L));
        bookManager.deleteBook(bookManager.findById(1003L));
        LOGGER.info(bookManager.getAllBooks().toString());
    }

    @Test
    @UsingDataSet("datasets/book/using.xml")
    @ShouldMatchDataSet(value = "datasets/book/expected-update.xml")
    public void update() {
        Book book = bookManager.findById(1003L);
        book.setName("Updated Book");
        bookManager.updateBook(book);
        LOGGER.info(bookManager.findById(1003L).toString());
    }

    @Test
    @UsingDataSet("datasets/book_author/using-updateAuthorsRatingAfterBookUpdate.xml")
    @ShouldMatchDataSet(value = "datasets/book_author/expected-updateAuthorsRatingAfterBookUpdate.xml")
    public void updateAuthorsRatingAfterBookUpdate() {
        Book book = bookManager.findById(1001L);
        List<Long> authorIds = book.getAuthors().stream().map(Author::getId).collect(Collectors.toList());
        book.setAuthors(new ArrayList<>());
        authorManager.updateAuthorsRatingAfterBookUpdate(book, authorIds);
        LOGGER.error(authorManager.findById(1001L).toString());
    }
}
