CREATE TABLE authorities
(
    authority_id INT PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(255),
    deleted      BOOLEAN
);

CREATE TABLE persons
(
    person_id    INT PRIMARY KEY AUTO_INCREMENT,
    login        VARCHAR(255),
    password     VARCHAR(255),
    email        VARCHAR(255),
    authority_id INT,
    deleted      BOOLEAN,
    FOREIGN KEY (authority_id) REFERENCES authorities (authority_id)
);

CREATE TABLE tags
(
    tag_id INT PRIMARY KEY AUTO_INCREMENT,
    name   VARCHAR(255)
);

CREATE TABLE issues
(
    issue_id      INT PRIMARY KEY AUTO_INCREMENT,
    status        VARCHAR(255),
    priority      VARCHAR(255),
    type          VARCHAR(255),
    name          VARCHAR(255),
    description   VARCHAR(255),
    code          VARCHAR(20),
    creator_id    INT,
    assignee_id   INT,
    date_created  DATE,
    last_update   DATE,
    deleted       BOOLEAN,
    FOREIGN KEY (creator_id) REFERENCES persons (person_id),
    FOREIGN KEY (assignee_id) REFERENCES persons (person_id)
);

CREATE TABLE projects
(
    project_id   INT PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(255),
    issue_id     INT,
    enabled      BOOLEAN,
    data_created DATE,
    code         VARCHAR(20),
    description  VARCHAR(255),
    FOREIGN KEY (issue_id) REFERENCES issues (issue_id)
);

CREATE TABLE comments
(
    comment_id    INT PRIMARY KEY AUTO_INCREMENT,
    date_created  DATE,
    last_update   DATE,
    content       VARCHAR(500),
    person_id     INT,
    issue_id      INT,
    deleted       BOOLEAN,
    FOREIGN KEY (person_id) REFERENCES persons (person_id),
    FOREIGN KEY (issue_id) REFERENCES issues (issue_id)
);

CREATE TABLE attachments
(
    attachment_id INT PRIMARY KEY AUTO_INCREMENT,
    file          LONGBLOB,
    date_added    DATE,
    comment_id    INT,
    deleted       BOOLEAN,
    FOREIGN KEY (comment_id) REFERENCES comments (comment_id)
);

CREATE TABLE logs
(
    log_id     INT PRIMARY KEY AUTO_INCREMENT,
    person_id  INT,
    issue_id   INT,
    old_status VARCHAR(255),
    new_status VARCHAR(255),
    date       DATE,
    FOREIGN KEY (person_id) REFERENCES persons (person_id),
    FOREIGN KEY (issue_id) REFERENCES issues (issue_id)
);

CREATE TABLE issues_tags
(
    issue_id INT,
    tag_id   INT,
    PRIMARY KEY (issue_id, tag_id),
    FOREIGN KEY (issue_id) REFERENCES issues (issue_id),
    FOREIGN KEY (tag_id) REFERENCES tags (tag_id)
);
