CREATE TABLE patient (
    id UUID get_random_uuid() PRIMARY KEY,
    name TEXT NOT NULL,
    birthDate DATE NOT NULL,
    cpf CHAR(14) NOT NULL UNIQUE,
    cellPhone VARCHAR(15),
    email TEXT NOT NULL UNIQUE,
    city TEXT NOT NULL,
    cep TEXT NOT NULL,
    street TEXT NOT NULL,
    state TEXT NOT NULL
);