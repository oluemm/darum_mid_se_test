CREATE TABLE IF NOT EXISTS "users" (
                                       id       UUID         NOT NULL,
                                       email    VARCHAR(255) NOT NULL,
                                       password VARCHAR(255) NOT NULL,
                                       role     VARCHAR(255) NOT NULL,
                                       CONSTRAINT pk_users PRIMARY KEY (id));


--------------------------------------------------------------------------------
-- 1. ADMIN USER (Alice Smith - HR Manager)
--------------------------------------------------------------------------------
INSERT INTO "users" (id, email, password, role)
SELECT '0b144bd2-bd8b-11f0-b5c7-5e241130a268', 'alice.smith@example.com',
       '$2b$12$7hoRZfJrRKD2nIm2vHLs7OBETy.LWenXXMLKf99W8M4PUwO6KB7fu', 'ADMIN'
WHERE NOT EXISTS (
    SELECT 1
    FROM "users"
    WHERE id = '0b144bd2-bd8b-11f0-b5c7-5e241130a268'
       OR email = 'alice.smith@example.com'
);

--------------------------------------------------------------------------------
-- 2. MANAGER USERS (Bob, Ethan, Isaac)
--------------------------------------------------------------------------------

-- Bob Johnson (Backend Manager)
INSERT INTO "users" (id, email, password, role)
SELECT '1898b16c-bd8b-11f0-8dac-5e241130a268', 'bob.johnson@example.com',
       '$2b$12$7hoRZfJrRKD2nIm2vHLs7OBETy.LWenXXMLKf99W8M4PUwO6KB7fu', 'MANAGER'
WHERE NOT EXISTS (
    SELECT 1
    FROM "users"
    WHERE id = '1898b16c-bd8b-11f0-8dac-5e241130a268'
       OR email = 'bob.johnson@example.com'
);

-- Ethan Hunt (Frontend Manager)
INSERT INTO "users" (id, email, password, role)
SELECT '37ef8004-bd8b-11f0-ba8f-5e241130a268', 'ethan.hunt@example.com',
       '$2b$12$7hoRZfJrRKD2nIm2vHLs7OBETy.LWenXXMLKf99W8M4PUwO6KB7fu', 'MANAGER'
WHERE NOT EXISTS (
    SELECT 1
    FROM "users"
    WHERE id = '37ef8004-bd8b-11f0-ba8f-5e241130a268'
       OR email = 'ethan.hunt@example.com'
);

-- Isaac Newton (DevOps Manager)
INSERT INTO "users" (id, email, password, role)
SELECT '2e6c831a-bd8b-11f0-9f4d-5e241130a267', 'isaac.newton@example.com',
       '$2b$12$7hoRZfJrRKD2nIm2vHLs7OBETy.LWenXXMLKf99W8M4PUwO6KB7fu', 'MANAGER'
WHERE NOT EXISTS (
    SELECT 1
    FROM "users"
    WHERE id = '2e6c831a-bd8b-11f0-9f4d-5e241130a267'
       OR email = 'isaac.newton@example.com'
);

--------------------------------------------------------------------------------
-- 3. EMPLOYEE USERS (Charlie, Diana, Fiona, George, Hannah, Julia)
--------------------------------------------------------------------------------

-- Charlie Brown (Frontend Employee)
INSERT INTO "users" (id, email, password, role)
SELECT '24781310-bd8b-11f0-bd65-5e241130a268', 'charlie.brown@example.com',
       '$2b$12$7hoRZfJrRKD2nIm2vHLs7OBETy.LWenXXMLKf99W8M4PUwO6KB7fu', 'EMPLOYEE'
WHERE NOT EXISTS (
    SELECT 1
    FROM "users"
    WHERE id = '24781310-bd8b-11f0-bd65-5e241130a268'
       OR email = 'charlie.brown@example.com'
);

-- Diana Prince (Backend Employee)
INSERT INTO "users" (id, email, password, role)
SELECT '2e6c831a-bd8b-11f0-9f4d-5e241130a268', 'diana.prince@example.com',
       '$2b$12$7hoRZfJrRKD2nIm2vHLs7OBETy.LWenXXMLKf99W8M4PUwO6KB7fu', 'EMPLOYEE'
WHERE NOT EXISTS (
    SELECT 1
    FROM "users"
    WHERE id = '2e6c831a-bd8b-11f0-9f4d-5e241130a268'
       OR email = 'diana.prince@example.com'
);

-- Fiona Gleason (HR Employee - ON_LEAVE)
INSERT INTO "users" (id, email, password, role)
SELECT '0b144bd2-bd8b-11f0-b5c7-5e241130a267', 'fiona.gleason@example.com',
       '$2b$12$7hoRZfJrRKD2nIm2vHLs7OBETy.LWenXXMLKf99W8M4PUwO6KB7fu', 'EMPLOYEE'
WHERE NOT EXISTS (
    SELECT 1
    FROM "users"
    WHERE id = '0b144bd2-bd8b-11f0-b5c7-5e241130a267'
       OR email = 'fiona.gleason@example.com'
);

-- George Clark (DevOps Employee)
INSERT INTO "users" (id, email, password, role)
SELECT '1898b16c-bd8b-11f0-8dac-5e241130a267', 'george.clark@example.com',
       '$2b$12$7hoRZfJrRKD2nIm2vHLs7OBETy.LWenXXMLKf99W8M4PUwO6KB7fu', 'EMPLOYEE'
WHERE NOT EXISTS (
    SELECT 1
    FROM "users"
    WHERE id = '1898b16c-bd8b-11f0-8dac-5e241130a267'
       OR email = 'george.clark@example.com'
);

-- Hannah Scott (Frontend Employee - TERMINATED)
INSERT INTO "users" (id, email, password, role)
SELECT '24781310-bd8b-11f0-bd65-5e241130a267', 'hannah.scott@example.com',
       '$2b$12$7hoRZfJrRKD2nIm2vHLs7OBETy.LWenXXMLKf99W8M4PUwO6KB7fu', 'EMPLOYEE'
WHERE NOT EXISTS (
    SELECT 1
    FROM "users"
    WHERE id = '24781310-bd8b-11f0-bd65-5e241130a267'
       OR email = 'hannah.scott@example.com'
);

-- Julia Roberts (Backend Employee)
INSERT INTO "users" (id, email, password, role)
SELECT '37ef8004-bd8b-11f0-ba8f-5e241130a267', 'julia.roberts@example.com',
       '$2b$12$7hoRZfJrRKD2nIm2vHLs7OBETy.LWenXXMLKf99W8M4PUwO6KB7fu', 'EMPLOYEE'
WHERE NOT EXISTS (
    SELECT 1
    FROM "users"
    WHERE id = '37ef8004-bd8b-11f0-ba8f-5e241130a267'
       OR email = 'julia.roberts@example.com'
);
