INSERT INTO department (id, name, manager_email, created_at, updated_at)
VALUES
    ('d361596c-1123-4567-89ab-001d9f82d1c1', 'HR', 'alice.smith@example.com', NOW(), NOW()),
    ('d361596c-1123-4567-89ab-002d9f82d1c2', 'Backend', 'bob.johnson@example.com', NOW(), NOW()),
    ('d361596c-1123-4567-89ab-003d9f82d1c3', 'Frontend', 'ethan.hunt@example.com', NOW(), NOW()),
    ('d361596c-1123-4567-89ab-004d9f82d1c4', 'DevOps', 'isaac.newton@example.com', NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

INSERT INTO employee (id, first_name, last_name, email, status, created_at, department_id)
VALUES
    -- Employee 1: Admin/HR Manager
    ('0b144bd2-bd8b-11f0-b5c7-5e241130a268', 'Alice', 'Smith', 'alice.smith@example.com', 'ACTIVE', NOW(),
     'd361596c-1123-4567-89ab-001d9f82d1c1'),

    -- Employee 2: Backend Manager
    ('1898b16c-bd8b-11f0-8dac-5e241130a268', 'Bob', 'Johnson', 'bob.johnson@example.com', 'ACTIVE', NOW(),
     'd361596c-1123-4567-89ab-002d9f82d1c2'),

    -- Employee 3: Frontend Employee
    ('24781310-bd8b-11f0-bd65-5e241130a268', 'Charlie', 'Brown', 'charlie.brown@example.com', 'ACTIVE',
     NOW(), 'd361596c-1123-4567-89ab-003d9f82d1c3'),

    -- Employee 4: Backend Employee
    ('2e6c831a-bd8b-11f0-9f4d-5e241130a268', 'Diana', 'Prince', 'diana.prince@example.com', 'ACTIVE', NOW(),
     'd361596c-1123-4567-89ab-002d9f82d1c2'),

    -- Employee 5: Frontend Manager
    ('37ef8004-bd8b-11f0-ba8f-5e241130a268', 'Ethan', 'Hunt', 'ethan.hunt@example.com', 'ACTIVE', NOW(),
     'd361596c-1123-4567-89ab-003d9f82d1c3'),

    -- Employee 6: HR Employee (On leave)
    ('0b144bd2-bd8b-11f0-b5c7-5e241130a267', 'Fiona', 'Gleason', 'fiona.gleason@example.com', 'ON_LEAVE',
     NOW(), 'd361596c-1123-4567-89ab-001d9f82d1c1'),

    -- Employee 7: DevOps Employee
    ('1898b16c-bd8b-11f0-8dac-5e241130a267', 'George', 'Clark', 'george.clark@example.com', 'ACTIVE', NOW(),
     'd361596c-1123-4567-89ab-004d9f82d1c4'),

    -- Employee 8: Frontend Employee (Terminated)
    ('24781310-bd8b-11f0-bd65-5e241130a267', 'Hannah', 'Scott', 'hannah.scott@example.com', 'TERMINATED',
     NOW(), 'd361596c-1123-4567-89ab-003d9f82d1c3'),

    -- Employee 9: DevOps Manager
    ('2e6c831a-bd8b-11f0-9f4d-5e241130a267', 'Isaac', 'Newton', 'isaac.newton@example.com', 'ACTIVE', NOW(),
     'd361596c-1123-4567-89ab-004d9f82d1c4'),

    -- Employee 10: Backend Employee
    ('37ef8004-bd8b-11f0-ba8f-5e241130a267', 'Julia', 'Roberts', 'julia.roberts@example.com', 'ACTIVE', NOW(),
     'd361596c-1123-4567-89ab-002d9f82d1c2')
ON CONFLICT (id) DO NOTHING;