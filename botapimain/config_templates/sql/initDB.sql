DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS giving_of_indicator;
DROP SEQUENCE IF EXISTS user_seq;
DROP INDEX IF EXISTS id_telegram_date;




CREATE SEQUENCE user_seq START 100000;

CREATE TABLE users (
  id        INTEGER PRIMARY KEY DEFAULT nextval('user_seq'),
  id_telegram INTEGER UNIQUE NOT NULL,
  acc INTEGER[] NOT NULL
);

CREATE TABLE giving_of_indicator (
                       id          INTEGER PRIMARY KEY DEFAULT nextval('user_seq'),
                       id_telegram INTEGER  NOT NULL,
                       date        date NOT NULL,
constraint id_telegram_date unique (id_telegram, date));

/*CREATE UNIQUE INDEX id_telegram_date ON giving_of_indicator (id_telegram, date);*/
