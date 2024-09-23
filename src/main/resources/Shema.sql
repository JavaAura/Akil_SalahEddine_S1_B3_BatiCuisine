CREATE TYPE etat AS ENUM ('Encours','Terminé','Annulé');
CREATE TYPE laborType as ENUM ('Ouvrier','Spécialiste');
CREATE TABLE client (
id SERIAL PRIMARY KEY,
nom VARCHAR (40),
adresse VARCHAR(50),
telephone VARCHAR(30),
estProfessionnel BOOLEAN
);

CREATE TABLE projet (
 id SERIAL PRIMARY KEY,
 nomProjet VARCHAR(40),
 margeBeneficiaire DOUBLE PRECISION,
 coutTotal DOUBLE PRECISION,
 etatProjet etat,
 idClient INT,
 CONSTRAINT fk_client FOREIGN KEY (idClient) REFERENCES client(id)
);

CREATE TABLE devis (
id SERIAL PRIMARY KEY,
montantEstime DOUBLE PRECISION,
dateEmission date,
dateValidite date,
accepte BOOLEAN,
idProjet INT,
CONSTRAINT fk_projet FOREIGN KEY (idProjet) REFERENCES projet(id)

);

CREATE TABLE composants(
id SERIAL PRIMARY KEY,
nom VARCHAR(40),
typeComposant VARCHAR(40),
tauxTVA DOUBLE PRECISION,
projetId INT,
CONSTRAINT fk_projet FOREIGN KEY (projetId) REFERENCES Projet(id)
);

CREATE TABLE materiel(
coutUnitaire DOUBLE PRECISION,
quantite DOUBLE PRECISION,
coutTransport DOUBLE PRECISION,
coefficientQualite DOUBLE PRECISION
)INHERITS (composants) ;

CREATE TABLE mainDoeuvre(
tauxHoraire DOUBLE PRECISION,
heuresTravail DOUBLE PRECISION,
productiviteOuvrier DOUBLE PRECISION,
mainType laborType
)INHERITS (composants) ;



