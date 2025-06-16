CREATE TABLE users(
id uuid not null primary key,
login varchar(40) not null unique,
password varchar(300) not null,
roles varchar[]
);