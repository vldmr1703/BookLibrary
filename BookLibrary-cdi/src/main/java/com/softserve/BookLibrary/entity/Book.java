package com.softserve.BookLibrary.entity;


import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

import static com.softserve.BookLibrary.validator.ValidationConstants.*;

@Entity
@Table
@NamedQueries({
        @NamedQuery(name = Book.QUERY_FIND_ALL, query = "select b from Book b"),
        @NamedQuery(name = Book.QUERY_FIND_BY_NAME, query = "select b from Book b where b.name = :name"),
        @NamedQuery(name = Book.QUERY_FIND_BY_PUBLISHER_YEAR, query = "select b from Book b where b.publishedYear = :published_year"),
        @NamedQuery(name = Book.QUERY_FIND_BY_ISBN, query = "select b from Book b where b.isbn = :isbn"),
        @NamedQuery(name = Book.QUERY_FIND_BY_PUBLISHER, query = "select b from Book b where b.publisher = :publisher"),
        @NamedQuery(name = Book.QUERY_FIND_BY_AUTHOR_ID, query = "select b from Book b inner join b.authors author where author.id = :authorId"),
        @NamedQuery(name = Book.QUERY_FIND_BY_RATING, query = "select b from Book b where b.rating =:rating"),
        @NamedQuery(name = Book.QUERY_FIND_BY_CREATE_DATE, query = "select b from Book b where b.createDate =:createDate"),
        @NamedQuery(name = Book.QUERY_GET_RATING_BY_AUTHOR_ID, query = "select avg(b.rating) from Book b inner join b.authors author where author.id =:authorId"),
        @NamedQuery(name = Book.QUERY_FIND_BY_PAGE, query = "from Book"),
        @NamedQuery(name = Book.QUERY_GET_COUNT, query = "select count(b) from Book b")
})
public class  Book extends SuperEntity {

    public static final String QUERY_FIND_ALL = "Book.findAll";
    public static final String QUERY_FIND_BY_NAME = "Book.findByName";
    public static final String QUERY_FIND_BY_PUBLISHER_YEAR = "Book.findByPublisherYear";
    public static final String QUERY_FIND_BY_ISBN = "Book.findByIsbn";
    public static final String QUERY_FIND_BY_PUBLISHER = "Book.findByPublisher";
    public static final String QUERY_FIND_BY_AUTHOR_ID = "Book.findByAuthorId";
    public static final String QUERY_FIND_BY_RATING = "Book.findByRating";
    public static final String QUERY_FIND_BY_CREATE_DATE = "Book.findByCreateDate";
    public static final String QUERY_GET_RATING_BY_AUTHOR_ID = "Book.getRatingByAuthorId";
    public static final String QUERY_FIND_BY_PAGE = "BookFacade.getByPage";
    public static final String QUERY_GET_COUNT = "BookFacade.getCount";

    @Id
    @GeneratedValue(generator = "BookSeq")
    @SequenceGenerator(name = "BookSeq", sequenceName = "BOOK_ID_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotNull
    @Size(min = NAME_MIN_SIZE, max = NAME_MAX_SIZE, message = NAME_SIZE_MESSAGE)
    private String name;

    @Column(name = "published_year")
    @Min(value = 0, message = PUBLISHED_YEAR_SIZE_MESSAGE)
    @Max(value = 2018, message = PUBLISHED_YEAR_SIZE_MESSAGE)
    private Integer publishedYear;

    @Column(name = "isbn", nullable = false, unique = true)
    @Pattern(regexp = ISBN_PATTERN, message = ISBN_PATTERN_MESSAGE)
    private String isbn;

    @Column(name = "publisher")
    private String publisher;

    @JsonIgnore
    @OneToMany(mappedBy = "book", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private List<Review> reviews;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "book_author", joinColumns = {
            @JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "author_id")})
    private List<Author> authors;

    public Book() {
    }

    public Book(Long id, String name, Integer publishedYear, String isbn, String publisher) {
        this.id = id;
        this.name = name;
        this.publishedYear = publishedYear;
        this.isbn = isbn;
        this.publisher = publisher;
    }

    public Book(String name, Integer publishedYear, String isbn, String publisher) {
        this.name = name;
        this.publishedYear = publishedYear;
        this.isbn = isbn;
        this.publisher = publisher;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(Integer publishedYear) {
        this.publishedYear = publishedYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", publishedYear=" + publishedYear +
                ", isbn='" + isbn + '\'' +
                ", publisher='" + publisher + '\'' +
                super.toString() +
                '}';
    }
}
