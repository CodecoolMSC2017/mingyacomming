/*
    Database initialization script that runs on every web-application redeployment.
*/

DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL,
    role TEXT NOT NULL,
	CONSTRAINT email_not_empty CHECK (name <> ''),
	CONSTRAINT password_not_empty CHECK (password <> '')
);



INSERT INTO users (name, password, role) VALUES
	('user1@user1', 'user1', 'asd'), -- 1
	('user2@user2', 'user2', 'sad'), -- 2
	('user2@user3', 'user3', 'lul'); -- 3

