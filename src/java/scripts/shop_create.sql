drop table books;
drop table categories;

create table categories (
  category_id integer not null primary key,
  category_name varchar(70)
  );

create table books (
  book_id integer not null primary key ,
  title varchar(70),
  author varchar(70),
  price float,
  category_id integer
  );

alter table books  add constraint category_id foreign key (category_id) references categories (category_id) ;
