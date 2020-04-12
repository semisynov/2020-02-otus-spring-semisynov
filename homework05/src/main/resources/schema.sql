drop table if exists books;
create table books
(
    book_id identity not null primary key,
    title varchar(255)
);

drop table if exists authors;
create table authors
(
    author_id identity not null primary key,
    name varchar(255)
);

drop table if exists genres;
create table genres
(
    genre_id identity not null primary key,
    title varchar(255)
);

drop table if exists book_genre;
create table book_genre
(
    book_id long,
    genre_id long,
    foreign key (genre_id) references genres(genre_id)
);

drop table if exists book_author;
create table book_author
(
    book_id long,
    author_id long,
    foreign key (author_id) references authors(author_id)
);