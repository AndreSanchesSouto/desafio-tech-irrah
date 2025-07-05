CREATE TABLE plans(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    price NUMERIC(10,2) NOT NULL
)