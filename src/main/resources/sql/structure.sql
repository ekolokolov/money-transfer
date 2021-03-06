create table if not exists PUBLIC.USER_INFO
(
  id          int auto_increment,
  login       varchar2(30) not null unique,
  name        varchar2(100),
  second_name varchar2(100)
);

create table if not exists PUBLIC.ACCOUNT
(
  id             int auto_increment,
  user_id        int,
  account_number int,
  balance        DECIMAL(100, 2)
);


ALTER TABLE PUBLIC.ACCOUNT
  add foreign key (user_id)
    references PUBLIC.USER_INFO (id);


create table if not exists PUBLIC.TRANSACTIONS
(
  id              int auto_increment,
  transaction_id  uuid,
  account_from_id int,
  account_to_id   int,
  amount          DECIMAL(100, 2),
  status          varchar2(10)
);
