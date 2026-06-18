CREATE TABLE IF NOT EXISTS filmes(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    titulo VARCHAR(150) NOT NULL,
    genero VARCHAR(50),
    nota INT CHECK (nota >= 1 AND nota <= 10),
    imagem_url TEXT,
    imagem_key TEXT
);

ALTER TABLE filmes
ADD COLUMN IF NOT EXISTS imagem_url TEXT;

ALTER TABLE filmes
ADD COLUMN IF NOT EXISTS imagem_key TEXT;

CREATE TABLE IF NOT EXISTS usuario(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

ALTER TABLE usuario
ADD COLUMN IF NOT EXISTS password VARCHAR(100);

CREATE TABLE IF NOT EXISTS perfil (
    usuarioid UUID NOT NULL,
    cargo VARCHAR(50) NOT NULL,
    CONSTRAINT fk_perfil_usuario
        FOREIGN KEY(usuarioid) REFERENCES usuario(id)
);

ALTER TABLE perfil DROP CONSTRAINT IF EXISTS perfil_unique;

ALTER TABLE perfil
ADD CONSTRAINT perfil_unique UNIQUE (usuarioid);