DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS giving_of_indicator;
DROP SEQUENCE IF EXISTS user_seq;
DROP SEQUENCE IF EXISTS givin_seq;
DROP SEQUENCE IF EXISTS conver_seq;


CREATE SEQUENCE user_seq START 100000;
CREATE SEQUENCE givin_seq START 100000;
CREATE SEQUENCE conver_seq START 100000;


CREATE TABLE users (
  id        INTEGER PRIMARY KEY DEFAULT nextval('user_seq'),
  id_telegram INTEGER UNIQUE NOT NULL,
  acc INTEGER[] NOT NULL
);


CREATE TABLE giving_of_indicator (
  id          INTEGER PRIMARY KEY DEFAULT nextval('givin_seq'),
  id_telegram INTEGER  NOT NULL,
  date        date NOT NULL,
constraint id_telegram_date unique (id_telegram, date));


CREATE TABLE conversation_with_the_operator (
  id          INTEGER PRIMARY KEY DEFAULT nextval('conver_seq'),
  id_telegram INTEGER  NOT NULL,
  date        date NOT NULL,
constraint id_telegram_date_2 unique (id_telegram, date));


