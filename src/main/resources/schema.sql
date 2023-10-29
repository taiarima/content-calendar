CREATE TABLE IF NOT EXISTS Content (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    title varchar(255) NOT NULL,
    descr text,
    status VARCHAR(20) NOT NULL,
    content_type VARCHAR(50) NOT NULL,
    date_created TIMESTAMP NOT NULL,
    date_updated TIMESTAMP,
    url VARCHAR(255)
);

INSERT INTO Content(title, descr, status, content_type, date_created)
VALUES ('Blog post', 'A blog post I wrote', 'IDEA', 'ARTICLE', CURRENT_TIMESTAMP);

