create table if not exists PUBLIC.USER_INFO
(
  id          int auto_increment,
  name        varchar2(100),
  second_name varchar2(100),
);

create table if not exists PUBLIC.ACCOUNT
(
  id             int auto_increment,
  user_id        int,
  account_number int,
  balance        DECIMAL(100, 2),
);

ALTER TABLE PUBLIC.ACCOUNT
  add foreign key (user_id)
    references PUBLIC.USER_INFO (id);