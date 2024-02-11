CREATE TABLE authorities
(
    authority_id INT PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(255) NOT NULL,
    deleted      BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE logins
(
    login_id     INT PRIMARY KEY AUTO_INCREMENT,
    login        VARCHAR(255) NOT NULL,
    password     VARCHAR(255) NOT NULL,
    email        VARCHAR(255) NOT NULL,
    authority_id INT NOT NULL,
    deleted      BOOLEAN NOT NULL DEFAULT false,
    FOREIGN KEY (authority_id) REFERENCES authorities (authority_id)
);

CREATE TABLE persons
(
    person_id INT PRIMARY KEY AUTO_INCREMENT,
    name      VARCHAR(255) NOT NULL,
    surname   VARCHAR(255),
    login_id  INT NOT NULL,
    deleted   BOOLEAN NOT NULL DEFAULT false,
    FOREIGN KEY (login_id) REFERENCES logins (login_id)
);

CREATE TABLE tags
(
    tag_id INT PRIMARY KEY AUTO_INCREMENT,
    name   VARCHAR(255) NOT NULL,
    deleted BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE projects
(
    project_id   INT PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(255),
    enabled      BOOLEAN NOT NULL,
    date_created DATE NOT NULL,
    code         VARCHAR(20),
    description  VARCHAR(255)
);

CREATE TABLE issues
(
    issue_id     INT PRIMARY KEY AUTO_INCREMENT,
    project_id   INT NOT NULL,
    status       VARCHAR(255) NOT NULL,
    priority     VARCHAR(255),
    type         VARCHAR(255),
    name         VARCHAR(255),
    description  VARCHAR(255),
    code         VARCHAR(20),
    creator_id   INT NOT NULL,
    assignee_id  INT,
    date_created DATE NOT NULL,
    last_update  DATE NOT NULL,
    deleted      BOOLEAN NOT NULL DEFAULT false,
    FOREIGN KEY (creator_id) REFERENCES persons (person_id),
    FOREIGN KEY (assignee_id) REFERENCES persons (person_id),
    FOREIGN KEY (project_id) REFERENCES projects (project_id)
);

CREATE TABLE comments
(
    comment_id   INT PRIMARY KEY AUTO_INCREMENT,
    date_created DATE NOT NULL,
    last_update  DATE NOT NULL,
    content      VARCHAR(500),
    person_id    INT NOT NULL,
    issue_id     INT NOT NULL,
    deleted      BOOLEAN NOT NULL DEFAULT false,
    FOREIGN KEY (person_id) REFERENCES persons (person_id),
    FOREIGN KEY (issue_id) REFERENCES issues (issue_id)
);

CREATE TABLE attachments
(
    attachment_id INT PRIMARY KEY AUTO_INCREMENT,
    file          LONGBLOB NOT NULL,
    date_added    DATE NOT NULL,
    comment_id    INT NOT NULL,
    deleted       BOOLEAN NOT NULL DEFAULT false,
    FOREIGN KEY (comment_id) REFERENCES comments (comment_id)
);

CREATE TABLE issue_logs
(
    issue_log_id     INT PRIMARY KEY AUTO_INCREMENT,
    person_id  INT NOT NULL,
    issue_id   INT NOT NULL,
    old_status VARCHAR(255) NOT NULL,
    new_status VARCHAR(255) NOT NULL,
    log_date       DATE NOT NULL,
    FOREIGN KEY (person_id) REFERENCES persons (person_id),
    FOREIGN KEY (issue_id) REFERENCES issues (issue_id)
);

CREATE TABLE issue_tags
(
    issue_id INT NOT NULL,
    tag_id   INT NOT NULL,
    PRIMARY KEY (issue_id, tag_id),
    FOREIGN KEY (issue_id) REFERENCES issues (issue_id),
    FOREIGN KEY (tag_id) REFERENCES tags (tag_id)
);
