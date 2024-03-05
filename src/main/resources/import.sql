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
    data          LONGBLOB NOT NULL,
    type          VARCHAR(255) NOT NULL,
    name          VARCHAR(255) NOT NULL,
    date_added    DATE NOT NULL,
    comment_id    INT NOT NULL,
    deleted       BOOLEAN NOT NULL DEFAULT false,
    FOREIGN KEY (comment_id) REFERENCES comments (comment_id)
);

CREATE TABLE issue_logs
(
    issue_log_id  INT PRIMARY KEY AUTO_INCREMENT,
    person_id     INT NOT NULL,
    issue_id      INT NOT NULL,
    old_status    VARCHAR(255) NOT NULL,
    new_status    VARCHAR(255) NOT NULL,
    log_date      DATE NOT NULL,
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

CREATE TABLE events
(
    event_id           INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    ip_address        VARCHAR(255) NOT NULL,
    request_method    VARCHAR(255) NOT NULL,
    request_uri       VARCHAR(255) NOT NULL,
    user              VARCHAR(255) NOT NULL,
    request_timestamp DATETIME NOT NULL
);

INSERT INTO authorities VALUES (1, 'ADMIN', false);
INSERT INTO authorities VALUES (2, 'USER', false);
INSERT INTO authorities VALUES (3, 'TECHNICIAN', false);
INSERT INTO logins VALUES (1, 'admin', '$2a$12$d44QFPfR3wflcIOvsn5WOOsovvvCmKwYz04PFJiKI31RkGiWUxlyC', 'admin@admin.admin', 1, false);
INSERT INTO logins VALUES (2, 'user', '$2a$12$0tJgWapBXp0emgI3b93WwOG7c7iQX1EPOkB05WgSRA0WWzoDgcGx2', 'user@user.user', 2, false);
INSERT INTO logins VALUES (3, 'technician', '$2a$12$SDU8m/X0TfQJnhShWhEzie6Yk7qPTi59rvhO6gDx.AqXeZgU6gcXa', 'technician@technician.technician', 3, false);
INSERT INTO persons VALUES (1, 'admin', 'admin', 1, false);
INSERT INTO persons VALUES (2, 'user', 'user', 2, false);
INSERT INTO persons VALUES (3, 'technician', 'technician', 3, false);
INSERT INTO tags VALUES (1, 'BUG', false);
INSERT INTO projects VALUES (1, 'BugTracker', true, '2024-02-11', 'BG', 'BugTracker Project');
INSERT INTO issues VALUES (1, 1, 'NEW', 'LOW', 'BUG', 'Not working', 'Cannot log in', 'BNWCLI', 1, null, '2024-02-11', '2024-02-11', false);
INSERT INTO comments VALUES (1, '2024-02-11', '2024-02-11', 'content', 1, 1, false);
INSERT INTO issue_logs VALUES (1, 1, 1, 'NEW', 'IN_PROGRESS', '2024-02-11');
INSERT INTO issue_tags VALUES (1, 1);