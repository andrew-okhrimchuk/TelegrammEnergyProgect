DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS user_seq;
DROP TYPE IF EXISTS user_flag;

CREATE TYPE user_flag AS ENUM ('active', 'deleted', 'superuser');

CREATE SEQUENCE user_seq START 100000;

CREATE TABLE users (
  id        INTEGER PRIMARY KEY DEFAULT nextval('user_seq'),
  full_name TEXT NOT NULL,
  email     TEXT NOT NULL UNIQUE,
  flag      user_flag NOT NULL,
  status    text DEFAULT ('inserted')
);
/*CREATE UNIQUE INDEX USERS_UNIQUE_EMAIL_IDX ON USERS (EMAIL);*/

