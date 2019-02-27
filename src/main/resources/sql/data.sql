ALTER TABLE PUBLIC.TRANSACTIONS
  ALTER COLUMN transaction_id set default random_uuid();

INSERT INTO PUBLIC.USER_INFO (login, name, second_name)
VALUES ('iivanov', 'Ivan', 'Ivanov'),
       ('petro88', 'Petr', 'Petrov'),
       ('google', 'Sergey', 'Brin'),
       ('vano23', 'Иван', 'Иванов');


INSERT INTO PUBLIC.ACCOUNT (account_number, user_id, balance)
values (654124124, 1, 12765.32),
       (778999007, 2, 9798162.00),
       (869866778, 2, 1000.10),
       (990066778, 2, 2000.10);
