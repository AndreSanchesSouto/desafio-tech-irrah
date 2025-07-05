CREATE TABLE users(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    document VARCHAR(14) NOT NULL UNIQUE,
    role VARCHAR(50) NOT NULL,
    status BOOLEAN NOT NULL,
    plan_type VARCHAR(50) NOT NULL UNIQUE,
    balance NUMERIC(10,2) DEFAULT 0.00,
    monthly_limit NUMERIC(10,2) DEFAULT 0.00,
    created_dt TIMESTAMP NOT NULL,
    removed_dt TIMESTAMP NULL
)