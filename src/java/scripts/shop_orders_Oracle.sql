drop table order_details;
drop table orders;

--Oracle Syntax for Sequence
DROP SEQUENCE SEQ_ORDERDETAILS ;
CREATE SEQUENCE SEQ_ORDERDETAILS START WITH 1000 INCREMENT BY 1;

create table order_details (
  id double precision not null,
  book_id integer,
  title varchar(70),
  author varchar(70),
  quantity integer,
  price double precision,
  order_id double precision,
  primary key (id)
  );
create table orders (
  order_id double precision not null,
  delivery_name varchar(70),
  delivery_address varchar(70),
  cc_name varchar(70),
  cc_number varchar(32),
  cc_expiry varchar(20),
  primary key (order_id)
  );
alter table order_details add constraint order_id foreign key (order_id) references orders (order_id) ;

