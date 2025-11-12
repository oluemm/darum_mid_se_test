CREATE TABLE department
(
    id            UUID         NOT NULL,
    name          VARCHAR(255) NOT NULL,
    manager_email VARCHAR(255),
    created_at    TIMESTAMP WITHOUT TIME ZONE,
    updated_at    TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_department PRIMARY KEY (id)
);

ALTER TABLE employee
    ADD department_id UUID;

ALTER TABLE employee
    ALTER COLUMN department_id SET NOT NULL;

ALTER TABLE department
    ADD CONSTRAINT uc_department_name UNIQUE (name);

ALTER TABLE employee
    ADD CONSTRAINT FK_EMPLOYEE_ON_DEPARTMENT FOREIGN KEY (department_id) REFERENCES department (id);
