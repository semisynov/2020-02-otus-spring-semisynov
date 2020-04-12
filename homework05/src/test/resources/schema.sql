drop table if exists books;
create table books
(
    book_id IDENTITY NOT NULL PRIMARY KEY,
    title varchar(255)
);

drop table if exists authors;
create table authors
(
    author_id IDENTITY NOT NULL PRIMARY KEY,
    name varchar(255)
);

drop table if exists genres;
create table genres
(
    genre_id IDENTITY NOT NULL PRIMARY KEY,
    title varchar(255)
);

drop table if exists book_genre;
create table book_genre
(
    book_id long,
    genre_id long
);

drop table if exists book_author;
create table book_author
(
    book_id long,
    author_id long
);