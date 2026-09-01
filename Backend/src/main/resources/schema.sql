CREATE TABLE membro (
    id INT PRIMARY KEY AUTO_INCREMENT,

    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) NOT NULL,
    rg VARCHAR(20) NOT NULL,
    data_nascimento DATE NOT NULL,
    cargo VARCHAR(50) NOT NULL,

    cep VARCHAR(9) NOT NULL,
    uf VARCHAR(2) NOT NULL,
    rua VARCHAR(150) NOT NULL,
    numero VARCHAR(10) NOT NULL,
    complemento VARCHAR(100),
    bairro VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,

    telefone VARCHAR(15) NOT NULL,
    genero VARCHAR(20) NOT NULL
);