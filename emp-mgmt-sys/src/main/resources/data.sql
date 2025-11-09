-- Insert 10 sample employee records with VALID UUIDs
INSERT INTO employee (id, first_name, last_name, email, status, created_at)
VALUES
    -- Employee 1: Admin Role
    ('0b144bd2-bd8b-11f0-b5c7-5e241130a268', 'Alice', 'Smith', 'alice.smith@example.com', 'ACTIVE', NOW()),

    -- Employee 2: Manager Role
    ('1898b16c-bd8b-11f0-8dac-5e241130a268', 'Bob', 'Johnson', 'bob.johnson@example.com', 'ACTIVE', NOW()),

    -- Employee 3: Regular Employee
    ('24781310-bd8b-11f0-bd65-5e241130a268', 'Charlie', 'Brown', 'charlie.brown@example.com', 'ACTIVE',
     NOW()),

    -- Employee 4: Regular Employee
    ('2e6c831a-bd8b-11f0-9f4d-5e241130a268', 'Diana', 'Prince', 'diana.prince@example.com', 'ACTIVE', NOW()),

    -- Employee 5: Manager Role
    ('37ef8004-bd8b-11f0-ba8f-5e241130a268', 'Ethan', 'Hunt', 'ethan.hunt@example.com', 'ACTIVE', NOW()),

    -- Employee 6: Regular Employee (On leave)
    ('0b144bd2-bd8b-11f0-b5c7-5e241130a267', 'Fiona', 'Gleason', 'fiona.gleason@example.com', 'ON_LEAVE',
     NOW()),

    -- Employee 7: Regular Employee
    ('1898b16c-bd8b-11f0-8dac-5e241130a267', 'George', 'Clark', 'george.clark@example.com', 'ACTIVE', NOW()),

    -- Employee 8: Regular Employee (Recently terminated)
    ('24781310-bd8b-11f0-bd65-5e241130a267', 'Hannah', 'Scott', 'hannah.scott@example.com', 'TERMINATED',
     NOW()),

    -- Employee 9: Regular Employee
    ('2e6c831a-bd8b-11f0-9f4d-5e241130a267', 'Isaac', 'Newton', 'isaac.newton@example.com', 'ACTIVE', NOW()),

    -- Employee 10: Regular Employee
    ('37ef8004-bd8b-11f0-ba8f-5e241130a267', 'Julia', 'Roberts', 'julia.roberts@example.com', 'ACTIVE', NOW
                                                                                                        ())
ON CONFLICT (id) DO NOTHING;