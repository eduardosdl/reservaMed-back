CREATE TABLE pacientes (
    id UUID get_random_uuid() PRIMARY KEY,
    name TEXT NOT NULL,
    birthDate DATE NOT NULL,
    cpf CHAR(14) NOT NULL UNIQUE,
    cellPhone VARCHAR(15),
    email TEXT NOT NULL UNIQUE
);