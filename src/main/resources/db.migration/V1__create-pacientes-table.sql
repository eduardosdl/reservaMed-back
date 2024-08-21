CREATE TABLE pacientes (
    id UUID get_random_uuid() PRIMARY KEY,
    nome TEXT NOT NULL,
    data_nascimento DATE NOT NULL,
    cpf CHAR(14) NOT NULL UNIQUE,
    telefone VARCHAR(15),
    email TEXT NOT NULL UNIQUE
);