
-- DROP ALL OBJECTS;

-- CREATE TABLE utente (
--     id INT PRIMARY KEY AUTO_INCREMENT,
--     nome VARCHAR(100) NOT NULL,
--     cognome VARCHAR(100) NOT NULL,
--     data_nascita DATE,
--     indirizzo VARCHAR(255),
--     cellulare VARCHAR(20),
--     email VARCHAR(100) UNIQUE,
--     username VARCHAR(50) UNIQUE,
--     password VARCHAR(50),
--     ruolo VARCHAR(20) CHECK (ruolo IN ('TUTOR', 'TUTORATO')),
--     id_tutor INT,
--     CONSTRAINT fk_tutor FOREIGN KEY (id_tutor) REFERENCES utente(id)
-- );

-- CREATE TABLE giustificativo (
--     id INT PRIMARY KEY AUTO_INCREMENT,
--     descrizione VARCHAR(255),
--     accettata BOOLEAN DEFAULT FALSE
-- );

-- CREATE TABLE mese (
--     id INT PRIMARY KEY AUTO_INCREMENT,
--     mese_chiuso BOOLEAN DEFAULT FALSE,
--     numero_mese INT,  
--     anno INT,         
--     id_utente INT,
--     CONSTRAINT fk_utente FOREIGN KEY (id_utente) REFERENCES utente(id) ON DELETE SET NULL
-- );

-- CREATE TABLE presenza (
--     id INT PRIMARY KEY AUTO_INCREMENT,
--     data DATE NOT NULL,
--     stato BOOLEAN, -- TRUE = presente, FALSE = assente
--     approvato BOOLEAN DEFAULT FALSE,
--     id_utente INT NOT NULL,
--     id_giustificativo INT,
--     id_mese INT,
--     CONSTRAINT fk_presenza_utente FOREIGN KEY (id_utente) REFERENCES utente(id),
--     CONSTRAINT fk_presenza_giustificativo FOREIGN KEY (id_giustificativo) REFERENCES giustificativo(id),
--     CONSTRAINT fk_presenza_mese FOREIGN KEY (id_mese) REFERENCES mese(id)
-- );


-- /* CREATE TABLE utente (
--     id INT PRIMARY KEY AUTO_INCREMENT,
--     nome VARCHAR(100) NOT NULL,
--     cognome VARCHAR(100) NOT NULL,
--     data_nascita DATE,
--     indirizzo VARCHAR(255),
--     cellulare VARCHAR(20),
--     email VARCHAR(100) UNIQUE,
--     username VARCHAR(50) UNIQUE,
--     password VARCHAR(50),
--     ruolo VARCHAR(20) CHECK (ruolo IN ('TUTOR', 'TUTORATO')),
--     id_tutor INT, -- self join
--     data_creazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--     data_modifica TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--     CONSTRAINT fk_tutor FOREIGN KEY (id_tutor) REFERENCES utente(id)
-- );

-- CREATE TABLE giustificativo (
--     id INT PRIMARY KEY AUTO_INCREMENT,
--     descrizione VARCHAR(255),
--     accettata BOOLEAN DEFAULT FALSE,
--     id_utente INT NOT NULL,
--     data_creazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--     data_modifica TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--     CONSTRAINT fk_giustificativo_utente FOREIGN KEY (id_utente) REFERENCES utente(id)
-- );

-- CREATE TABLE mese (
--     id INT PRIMARY KEY AUTO_INCREMENT,
--     mese_chiuso BOOLEAN DEFAULT FALSE,
--     giorni_totali INT,
--     presenze INT DEFAULT 0,
--     assenze INT DEFAULT 0,
--     data_creazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--     data_modifica TIMESTAMP DEFAULT CURRENT_TIMESTAMP
-- );


-- CREATE TABLE presenza (
--     id INT PRIMARY KEY AUTO_INCREMENT,
--     data DATE NOT NULL,
--     stato VARCHAR(10) CHECK (stato IN ('PRESENTE', 'ASSENTE')) NOT NULL,
--     approvato BOOLEAN DEFAULT FALSE,
--     id_utente INT NOT NULL,
--     id_giustificativo INT,
--     id_mese INT,
--     data_creazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--     data_modifica TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--     CONSTRAINT fk_presenza_utente FOREIGN KEY (id_utente) REFERENCES utente(id),
--     CONSTRAINT fk_presenza_giustificativo FOREIGN KEY (id_giustificativo) REFERENCES giustificativo(id),
--     CONSTRAINT fk_presenza_mese FOREIGN KEY (id_mese) REFERENCES mese(id)
-- );*/


-- -- ALTER TABLE mese ADD COLUMN id_utente INT;
-- -- ALTER TABLE mese ADD CONSTRAINT fk_utente FOREIGN KEY (id_utente) REFERENCES utente(id) ON DELETE SET NULL;

-- --ALTER TABLE mese DROP COLUMN id_utente;

-- --ALTER TABLE mese ADD COLUMN nome VARCHAR(30);
-- --ALTER TABLE mese DROP COLUMN giorni_totali;
-- --ALTER TABLE mese DROP COLUMN presenze;
-- --ALTER TABLE mese DROP COLUMN assenze;
-- --ALTER TABLE mese DROP COLUMN data_creazione;
-- --ALTER TABLE mese DROP COLUMN data_modifica;
-- --ALTER TABLE mese ADD COLUMN mese_chiuso BOOLEAN DEFAULT FALSE;


-- --ALTER TABLE giustificativo DROP COLUMN id_utente;

-- --ALTER TABLE presenza DROP COLUMN stato;

-- --ALTER TABLE presenza ADD COLUMN stato BOOLEAN;

-- --ALTER TABLE mese DROP COLUMN nome;

-- --ALTER TABLE mese ADD COLUMN numero_mese INT;
-- --ALTER TABLE mese ADD COLUMN anno INT;
-- --ALTER TABLE mese ADD COLUMN id_utente INT;
-- --ALTER TABLE mese ADD CONSTRAINT fk_utente FOREIGN KEY (id_utente) REFERENCES utente(id) ON DELETE SET NULL;

