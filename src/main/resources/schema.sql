CREATE SEQUENCE ergebnis1vs1_seq INCREMENT BY 50 START WITH 1;

CREATE SEQUENCE ergebnis2vs2_seq INCREMENT BY 50 START WITH 1;

CREATE SEQUENCE spieler_seq INCREMENT BY 50 START WITH 1;

CREATE TABLE ergebnis1vs1
(
    id             BIGINT   NOT NULL,
    gewinner       INT      NOT NULL,
    verlierer      INT      NOT NULL,
    tore_verlierer SMALLINT NOT NULL,
    zeitpunkt      datetime NULL,
    CONSTRAINT pk_ergebnis1vs1 PRIMARY KEY (id)
);

CREATE TABLE ergebnis2vs2
(
    id               BIGINT   NOT NULL,
    gewinner_vorn    INT      NOT NULL,
    gewinner_hinten  INT      NOT NULL,
    verlierer_vorn   INT      NOT NULL,
    verlierer_hinten INT      NOT NULL,
    tore_verlierer   SMALLINT NOT NULL,
    zeitpunkt        datetime NOT NULL,
    CONSTRAINT pk_ergebnis2vs2 PRIMARY KEY (id)
);

CREATE TABLE spieler
(
    id      INT          NOT NULL,
    name    VARCHAR(255) NOT NULL,
    elo1vs1 FLOAT        NOT NULL,
    elo2vs2 FLOAT        NOT NULL,
    elo_alt FLOAT NULL,
    CONSTRAINT pk_spieler PRIMARY KEY (id)
);

ALTER TABLE spieler
    ADD CONSTRAINT uc_spieler_name UNIQUE (name);

ALTER TABLE ergebnis1vs1
    ADD CONSTRAINT FK_ERGEBNIS1VS1_ON_GEWINNER FOREIGN KEY (gewinner) REFERENCES spieler (id);

ALTER TABLE ergebnis1vs1
    ADD CONSTRAINT FK_ERGEBNIS1VS1_ON_VERLIERER FOREIGN KEY (verlierer) REFERENCES spieler (id);

ALTER TABLE ergebnis2vs2
    ADD CONSTRAINT FK_ERGEBNIS2VS2_ON_GEWINNER_HINTEN FOREIGN KEY (gewinner_hinten) REFERENCES spieler (id);

ALTER TABLE ergebnis2vs2
    ADD CONSTRAINT FK_ERGEBNIS2VS2_ON_GEWINNER_VORN FOREIGN KEY (gewinner_vorn) REFERENCES spieler (id);

ALTER TABLE ergebnis2vs2
    ADD CONSTRAINT FK_ERGEBNIS2VS2_ON_VERLIERER_HINTEN FOREIGN KEY (verlierer_hinten) REFERENCES spieler (id);

ALTER TABLE ergebnis2vs2
    ADD CONSTRAINT FK_ERGEBNIS2VS2_ON_VERLIERER_VORN FOREIGN KEY (verlierer_vorn) REFERENCES spieler (id);