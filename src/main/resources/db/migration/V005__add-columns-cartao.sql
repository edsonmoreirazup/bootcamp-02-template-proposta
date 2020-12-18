ALTER TABLE cartao
    ADD COLUMN data_criacao DATETIME NOT NULL,
    ADD COLUMN status_cartao VARCHAR(45) NOT NULL,
    ADD COLUMN titular_cartao VARCHAR(60) NOT NULL;
