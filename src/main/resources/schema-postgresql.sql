CREATE TABLE IF NOT EXISTS filmes(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    titulo VARCHAR(150) NOT NULL,
    genero VARCHAR(50),
    nota INT CHECK (nota >= 1 AND nota <= 10)
);