DROP TABLE IF EXISTS url_checks;
DROP TABLE IF EXISTS urls;

CREATE TABLE urls (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    name varchar(255) NOT NULL UNIQUE,
    created_at timestamp NOT NULL
);

CREATE TABLE url_checks (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    url_id bigint REFERENCES urls(id) NOT NULL,
    status_code integer,
    title varchar(255),
    h1 varchar(255),
    description text,
    created_at timestamp NOT NULL
);
