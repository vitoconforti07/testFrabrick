
CREATE TABLE persona_fisica (
    id_persona_fisica SERIAL PRIMARY KEY,
    nome_persona_fisica VARCHAR(50) ,
    cognome_persona_fisica VARCHAR(50) ,
    codice_fiscale_persona_fisica VARCHAR(16)
);

CREATE TABLE conto_corrente (
    id_conto_corrente SERIAL PRIMARY KEY,
    id_account INT UNIQUE,
    saldo NUMERIC(12, 2),
    data_ultima_aggiornamento DATE
);


CREATE TABLE persona_conto_corrente (
    id_persona_fisica INT,
    id_conto_corrente INT,
    PRIMARY KEY (id_persona_fisica, id_conto_corrente),
    FOREIGN KEY (id_persona_fisica) REFERENCES persona_fisica(id_persona_fisica),
    FOREIGN KEY (id_conto_corrente) REFERENCES conto_corrente(id_conto_corrente)
);

CREATE TABLE type_enum (
    id SERIAL PRIMARY KEY,
    enumeration VARCHAR(100) ,
    value_s VARCHAR(100)
);

CREATE TABLE operazione_bancaria (
    id SERIAL PRIMARY KEY,
    transaction_id VARCHAR(255),
    operation_id VARCHAR(255),
    accounting_date DATE,
    value_date DATE,
    type_enum_id INT,
    amount NUMERIC(15, 2),
    currency VARCHAR(10),
    description TEXT,
    transaction_status VARCHAR(50),
    is_debit BOOLEAN,
    fk_id_conto_corrente INT,

    FOREIGN KEY (fk_id_conto_corrente) REFERENCES conto_corrente(id_conto_corrente),
    FOREIGN KEY (type_enum_id) REFERENCES type_enum(id)
);