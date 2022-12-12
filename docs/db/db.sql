CREATE TABLE weather (
	id integer PRIMARY KEY AUTOINCREMENT,
	city_id integer NOT NULL REFERENCES city(id),
	tempreture integer NOT NULL,
	weather_name varchar(255) NOT NULL,
	date datetime NOT NULL,
	creator_id integer NOT NULL REFERENCES user_account(id)
);

CREATE TABLE country (
	id integer PRIMARY KEY AUTOINCREMENT,
	name varchar(255) NOT NULL
);

CREATE TABLE city (
	id integer PRIMARY KEY AUTOINCREMENT,
	name varchar(255) NOT NULL,
	country_id integer NOT NULL REFERENCES country(id)
);

CREATE TABLE user_account (
	id integer PRIMARY KEY AUTOINCREMENT,
	login varchar(255) NOT NULL,
	password varchar(255) NOT NULL
);





