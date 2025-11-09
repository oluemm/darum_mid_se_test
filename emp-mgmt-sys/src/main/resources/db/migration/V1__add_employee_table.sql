CREATE TABLE employee
(
    id         UUID         NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    status     VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_employee PRIMARY KEY (id)
);

ALTER TABLE employee
    ADD CONSTRAINT uc_employee_email UNIQUE (email);