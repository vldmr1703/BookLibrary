CREATE TABLE book(
  id INT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  published_year INT,
  isbn VARCHAR(20) NOT NULL UNIQUE,
  publisher VARCHAR(255),
  rating INT,
  create_date DATE
);

CREATE SEQUENCE book_id_seq START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER book_seq_tr BEFORE INSERT ON book
  FOR EACH ROW
  WHEN (NEW.id IS NULL)
  BEGIN
    SELECT book_id_seq.NEXTVAL INTO :NEW.id FROM dual;
  END;

CREATE TABLE author(
  id INT PRIMARY KEY,
  first_name VARCHAR(20),
  second_name VARCHAR(20),
  rating INT,
  create_date DATE
);

CREATE SEQUENCE author_id_seq START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER author_seq_tr BEFORE INSERT ON author
  FOR EACH ROW
  WHEN (NEW.id IS NULL)
  BEGIN
    SELECT author_id_seq.NEXTVAL INTO :NEW.id FROM dual;
  END;

CREATE TABLE review(
  id INT PRIMARY KEY,
  comment_text VARCHAR(500),
  commenter_name VARCHAR(20),
  rating INT,
  create_date DATE,
  book_id INT,
  FOREIGN KEY (book_id)
  REFERENCES book(id)
  ON DELETE CASCADE
);

CREATE SEQUENCE review_id_seq START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER review_seq_tr BEFORE INSERT ON review
  FOR EACH ROW
  WHEN (NEW.id IS NULL)
  BEGIN
    SELECT review_id_seq.NEXTVAL INTO :NEW.id FROM dual;
  END;

CREATE TABLE book_author(
  book_id INT,
  author_id INT,
  FOREIGN KEY (book_id)
  REFERENCES book(id)
  ON DELETE CASCADE,
  FOREIGN KEY (author_id)
  REFERENCES author(id)
  ON DELETE CASCADE
);