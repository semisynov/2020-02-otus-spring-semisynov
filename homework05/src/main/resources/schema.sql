drop table if exists books;
create table books
(
    id bigint primary key,
    name varchar(255),
    author_id bigint
);

drop table if exists authors;
create table authors
(
    id bigint primary key,
    last_name varchar(255),
    first_name varchar(255),
    genreId bigint
);

drop table if exists genres;
create table genres
(
    id bigint primary key,
    name varchar(255)
);
