insert into genres(genre_id, title) values (1, 'AnyGenre1');
insert into genres(genre_id, title) values (2, 'AnyGenre2');
insert into genres(genre_id, title) values (3, 'AnyGenre3');

insert into authors(author_id, name) values (1, 'AnyAuthor1');
insert into authors(author_id, name) values (2, 'AnyAuthor2');
insert into authors(author_id, name) values (3, 'AnyAuthor3');

insert into books(book_id, title) values (1, 'AnyBook1');
insert into book_genre(book_id, genre_id) values (1, 1);
insert into book_author(book_id, author_id) values (1, 1);

insert into books(book_id, title) values (2, 'AnyBook2');
insert into book_genre(book_id, genre_id) values (2, 2);
insert into book_author(book_id, author_id) values (2, 2);

insert into books(book_id, title) values (3, 'AnyBook3');
insert into book_genre(book_id, genre_id) values (3, 1);
insert into book_genre(book_id, genre_id) values (3, 2);
insert into book_author(book_id, author_id) values (3, 1);
insert into book_author(book_id, author_id) values (3, 2);