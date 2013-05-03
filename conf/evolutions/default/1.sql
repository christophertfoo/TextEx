# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table book (
  primary_key               bigint not null,
  isbn                      varchar(255),
  name                      varchar(255),
  edition                   integer,
  price                     double,
  constraint uq_book_isbn unique (isbn),
  constraint pk_book primary key (primary_key))
;

create table offer (
  primary_key               bigint not null,
  offer_id                  varchar(255),
  student_primary_key       bigint,
  book_primary_key          bigint,
  condition                 varchar(1),
  price                     double,
  quantity                  integer,
  constraint ck_offer_condition check (condition in ('N','H','S')),
  constraint uq_offer_offer_id unique (offer_id),
  constraint pk_offer primary key (primary_key))
;

create table request (
  primary_key               bigint not null,
  request_id                varchar(255),
  student_primary_key       bigint,
  book_primary_key          bigint,
  condition                 varchar(1),
  price                     double,
  quantity                  integer,
  constraint ck_request_condition check (condition in ('N','H','S')),
  constraint uq_request_request_id unique (request_id),
  constraint pk_request primary key (primary_key))
;

create table student (
  primary_key               bigint not null,
  student_id                varchar(255),
  first_name                varchar(255),
  last_name                 varchar(255),
  email                     varchar(255),
  password                  varchar(255),
  constraint uq_student_student_id unique (student_id),
  constraint pk_student primary key (primary_key))
;

create sequence book_seq;

create sequence offer_seq;

create sequence request_seq;

create sequence student_seq;

alter table offer add constraint fk_offer_student_1 foreign key (student_primary_key) references student (primary_key) on delete restrict on update restrict;
create index ix_offer_student_1 on offer (student_primary_key);
alter table offer add constraint fk_offer_book_2 foreign key (book_primary_key) references book (primary_key) on delete restrict on update restrict;
create index ix_offer_book_2 on offer (book_primary_key);
alter table request add constraint fk_request_student_3 foreign key (student_primary_key) references student (primary_key) on delete restrict on update restrict;
create index ix_request_student_3 on request (student_primary_key);
alter table request add constraint fk_request_book_4 foreign key (book_primary_key) references book (primary_key) on delete restrict on update restrict;
create index ix_request_book_4 on request (book_primary_key);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists book;

drop table if exists offer;

drop table if exists request;

drop table if exists student;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists book_seq;

drop sequence if exists offer_seq;

drop sequence if exists request_seq;

drop sequence if exists student_seq;

