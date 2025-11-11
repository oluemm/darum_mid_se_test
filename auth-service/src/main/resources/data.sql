-- Ensure the 'users' table exists
CREATE TABLE IF NOT EXISTS "users" (
                                       id       UUID         NOT NULL,
                                       email    VARCHAR(255) NOT NULL,
                                       password VARCHAR(255) NOT NULL,
                                       role     VARCHAR(255) NOT NULL,
                                       CONSTRAINT pk_users PRIMARY KEY (id));

-- Insert the user if no existing user with the same id or email exists
INSERT INTO "users" (id, email, password, role)
SELECT '9cedb86c-bf0d-11f0-8995-5e241130a268', 'admin@darum.ng',
       '$2a$10$AmNV9BnGb399xnhftVAyQObsgWTlK1txzkVS/qZQj6OMtq7u3jnnK', 'ADMIN'
WHERE NOT EXISTS (
    SELECT 1
    FROM "users"
    WHERE id = '9cedb86c-bf0d-11f0-8995-5e241130a268'
       OR email = 'admin@darum.ng'
);

INSERT INTO "users" (id, email, password, role)
SELECT 'a5f195be-bf0d-11f0-bfad-5e241130a268', 'manager@darum.ng',
       '$2a$10$AmNV9BnGb399xnhftVAyQObsgWTlK1txzkVS/qZQj6OMtq7u3jnnK', 'MANAGER'
WHERE NOT EXISTS (
    SELECT 1
    FROM "users"
    WHERE id = 'a5f195be-bf0d-11f0-bfad-5e241130a268'
       OR email = 'manager@darum.ng'
);

INSERT INTO "users" (id, email, password, role)
SELECT 'f911ce9e-bf0d-11f0-b50f-5e241130a268', 'employee1@darum.ng',
       '$2b$12$7hoRZfJrRKD2nIm2vHLs7OBETy.LWenXXMLKf99W8M4PUwO6KB7fu', 'EMPLOYEE'
WHERE NOT EXISTS (
    SELECT 1
    FROM "users"
    WHERE id = 'f911ce9e-bf0d-11f0-b50f-5e241130a268'
       OR email = 'employee1@darum.ng'
);
