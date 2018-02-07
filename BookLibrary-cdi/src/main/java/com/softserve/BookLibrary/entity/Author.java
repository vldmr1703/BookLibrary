package com.softserve.BookLibrary.entity;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

import static com.softserve.BookLibrary.validator.ValidationConstants.*;

@Entity
@Table
@NamedQueries({
        @NamedQuery(name = Author.QUERY_FIND_ALL, query = "from Author"),
        @NamedQuery(name = Author.QUERY_FIND_BY_FIRST_NAME, query = "select a from Author a where a.firstName = :firstName"),
        @NamedQuery(name = Author.QUERY_FIND_BY_SECOND_NAME, query = "select a from Author a where a.secondName = :secondName"),
        @NamedQuery(name = Author.QUERY_FIND_BY_FIRST_NAME_AND_SECOND_NAME, query = "select a from Author a where a.firstName = :firstName and a.secondName = :secondName"),
        @NamedQuery(name = Author.QUERY_FIND_BY_RATING, query = "select a from Author a where a.rating =:rating"),
        @NamedQuery(name = Author.QUERY_FIND_BY_CREATE_DATE, query = "select a from Author a where a.createDate = :createDate"),
        @NamedQuery(name = Author.QUERY_FIND_BY_PAGE, query = "from Author")
})
public class Author extends SuperEntity {
    public static final String QUERY_FIND_ALL = "Author.findAll";
    public static final String QUERY_FIND_BY_FIRST_NAME = "Author.findByFirstName";
    public static final String QUERY_FIND_BY_SECOND_NAME = "Author.findBySecondName";
    public static final String QUERY_FIND_BY_FIRST_NAME_AND_SECOND_NAME = "Author.findByFirstNameAndSecondName";
    public static final String QUERY_FIND_BY_RATING = "Author.findByRating";
    public static final String QUERY_FIND_BY_CREATE_DATE = "Author.findByCreateDate";
    public static final String QUERY_FIND_BY_PAGE = "Author.getByPage";


    @Id
    @GeneratedValue(generator = "AuthorSeq")
    @SequenceGenerator(name = "AuthorSeq", sequenceName = "AUTHOR_ID_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "first_name")
    @NotNull
    @Size(min = NAME_MIN_SIZE, max = NAME_MAX_SIZE, message = NAME_SIZE_MESSAGE)
    private String firstName;

    @Column(name = "second_name")
    @NotNull
    @Size(min = NAME_MIN_SIZE, max = NAME_MAX_SIZE, message = NAME_SIZE_MESSAGE)
    private String secondName;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "book_author", joinColumns = {
            @JoinColumn(name = "author_id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id")})
    private List<Book> books;

    public Author() {
    }

    public Author(String firstName, String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                super.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(id, author.id) &&
                Objects.equals(firstName, author.firstName) &&
                Objects.equals(secondName, author.secondName) &&
                Objects.equals(books, author.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, secondName, books);
    }
}
