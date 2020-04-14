insert into genres(genre_id, title) values (1, 'Жанр1');
insert into authors(author_id, name) values (1, 'Автор1');
insert into genres(genre_id, title) values (2, 'Жанр2');
insert into authors(author_id, name) values (2, 'Автор2');

insert into books(book_id, title) values (1, 'Книга1');
insert into book_genre(book_id, genre_id) values (1, 1);
insert into book_author(book_id, author_id) values (1, 1);

insert into books(book_id, title) values (2, 'Книга2');
insert into book_genre(book_id, genre_id) values (2, 1);
insert into book_genre(book_id, genre_id) values (2, 2);
insert into book_author(book_id, author_id) values (2, 1);
insert into book_author(book_id, author_id) values (2, 2);