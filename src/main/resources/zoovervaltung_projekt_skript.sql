-- Autoren: Stefan Zuza, Patrick Kristen
-- Dieses Skript stellt die gesamte Logik des Projekts "Zooverwaltung" dar
-- Wurde besprochen am 27.05.2025

-- Geforderte Abfragen für Projekt
-- Pfleger-ID eingeben. -> VIEW: Statistiken als Übersicht 
--> Was macht der/die Plfeger:
--> Wie viele Gehege reinigt die Person? 
--> Wie viele Items (Inventar) werden verwaltet? 
--> Wie viele Führungen werden veranstaltet?
--> Welche Fütterung führt der/die Pfleger_in durch?

-- Besucher-ID eingeben. -> Auflistung welche Führungen Besucher gesehen hat. 

-- Teardown
-- Linking Tables
DROP TABLE IF EXISTS Pflegt;
DROP TABLE IF EXISTS Reinigt;
DROP TABLE IF EXISTS Fuehrt_durch;
DROP TABLE IF EXISTS Verwaltet;
DROP TABLE IF EXISTS Veranstaltet;
DROP TABLE IF EXISTS Besucht;
DROP TABLE IF EXISTS Fuetterungsplan_Nahrungsart;

-- Teardown
-- Entity Tables
DROP TABLE IF EXISTS Gesundheitsakte;
DROP TABLE IF EXISTS Tier;
DROP TABLE IF EXISTS Gehege;
DROP TABLE IF EXISTS Nahrungsart;
DROP TABLE IF EXISTS Fuetterungsplan;
DROP TABLE IF EXISTS Fuehrung;
DROP TABLE IF EXISTS Besucher;
DROP TABLE IF EXISTS Inventar;
DROP TABLE IF EXISTS Pfleger;


-- Create Entities
CREATE TABLE IF NOT EXISTS Gesundheitsakte(
    Akte_ID SERIAL PRIMARY KEY,
    Behandlungsart VARCHAR(32) NOT NULL,
    Behandlungsdatum DATE NOT NULL,
    Tier_ID INT NOT NULL
);

CREATE TABLE IF NOT EXISTS Tier(
    Tier_ID SERIAL PRIMARY KEY,
    Tierart VARCHAR(32),
    Name VARCHAR(32) NOT NULL,
    Alter INT,
    Gehege_ID INT NOT NULL
);

CREATE TABLE IF NOT EXISTS Gehege(
    Gehege_ID SERIAL PRIMARY KEY,
    Gehegeart VARCHAR(32) NOT NULL
);

CREATE TABLE IF NOT EXISTS Nahrungsart(
    Nahrung_ID SERIAL PRIMARY KEY,
    Bezeichnung VARCHAR(32) NOT NULL
);

CREATE TABLE IF NOT EXISTS Fuetterungsplan(
    Plan_ID SERIAL PRIMARY KEY,
    Uhrzeit TIME NOT NULL,
    Datum DATE NOT NULL     
);

CREATE TABLE IF NOT EXISTS Pfleger(
    Pfleger_ID SERIAL PRIMARY KEY,
    Vorname VARCHAR(32) NOT NULL,
    Nachname VARCHAR(32) NOT NULL,
    SVNR CHAR(4) NOT NULL CHECK (SVNR ~ '^[0-9]{4}$')
);

CREATE TABLE IF NOT EXISTS Inventar(
    Inventar_ID SERIAL PRIMARY KEY,
    Bezeichnung VARCHAR(32) NOT NULL
);

CREATE TABLE IF NOT EXISTS Fuehrung(
    Fuehrung_ID SERIAL PRIMARY KEY,
    Uhrzeit TIME NOT NULL,
    Datum DATE NOT NULL,
    Gehegeroute VARCHAR(32) NOT NULL    
);

CREATE TABLE IF NOT EXISTS Besucher(
    Besucher_ID SERIAL PRIMARY KEY,
    Vorname VARCHAR(32) NOT NULL,
    Nachname VARCHAR(32) NOT NULL
);

-- FK hinzufügen
ALTER TABLE Gesundheitsakte
ADD CONSTRAINT fk_gesundheitsakte_tier
FOREIGN KEY (Tier_ID)
REFERENCES Tier(Tier_ID);

ALTER TABLE Tier
ADD CONSTRAINT fk_tier_gehege
FOREIGN KEY (Gehege_ID)
REFERENCES Gehege(Gehege_ID);

-- Verbindungstabellen erstellen
CREATE TABLE IF NOT EXISTS Pflegt (
    Pfleger_ID INT NOT NULL,
    Tier_ID INT NOT NULL,
    PRIMARY KEY (Pfleger_ID, Tier_ID),
    FOREIGN KEY (Pfleger_ID) REFERENCES Pfleger(Pfleger_ID),
    FOREIGN KEY (Tier_ID) REFERENCES Tier(Tier_ID) 
);

CREATE TABLE IF NOT EXISTS Reinigt (
    Gehege_ID INT NOT NULL,
    Pfleger_ID INT NOT NULL,
    PRIMARY KEY (Gehege_ID, Pfleger_ID),
    FOREIGN KEY (Gehege_ID) REFERENCES Gehege(Gehege_ID),
    FOREIGN KEY (Pfleger_ID) REFERENCES Pfleger(Pfleger_ID)
);

CREATE TABLE IF NOT EXISTS Verwaltet (
    Pfleger_ID INT NOT NULL,
    Inventar_ID INT NOT NULL,
    PRIMARY KEY (Pfleger_ID, Inventar_ID),
    FOREIGN KEY (Pfleger_ID) REFERENCES Pfleger(Pfleger_ID),
    FOREIGN KEY (Inventar_ID) REFERENCES Inventar(Inventar_ID)
);

CREATE TABLE IF NOT EXISTS Fuehrt_durch (
    Plan_ID INT NOT NULL,
    Pfleger_ID INT NOT NULL,
    PRIMARY KEY (Plan_ID, Pfleger_ID),
    FOREIGN KEY (Plan_ID) REFERENCES Fuetterungsplan(Plan_ID),
    FOREIGN KEY (Pfleger_ID) REFERENCES Pfleger(Pfleger_ID)
);

CREATE TABLE IF NOT EXISTS Fuetterungsplan_Nahrungsart (
    Plan_ID INT NOT NULL,
    Nahrung_ID INT NOT NULL,
    PRIMARY KEY (Plan_ID, Nahrung_ID),
    FOREIGN KEY (Plan_ID) REFERENCES Fuetterungsplan(Plan_ID),
    FOREIGN KEY (Nahrung_ID) REFERENCES Nahrungsart(Nahrung_ID)
);

CREATE TABLE IF NOT EXISTS Veranstaltet (
    Pfleger_ID INT NOT NULL,
    Fuehrung_ID INT NOT NULL,
    PRIMARY KEY (Pfleger_ID, Fuehrung_ID),
    FOREIGN KEY (Pfleger_ID) REFERENCES Pfleger(Pfleger_ID),
    FOREIGN KEY (Fuehrung_ID) REFERENCES Fuehrung(Fuehrung_ID)
);

CREATE TABLE IF NOT EXISTS Besucht (
    Besucher_ID INT NOT NULL,
    Fuehrung_ID INT NOT NULL,
    PRIMARY KEY (Besucher_ID, Fuehrung_ID),
    FOREIGN KEY (Besucher_ID) REFERENCES Besucher(Besucher_ID),
    FOREIGN KEY (Fuehrung_ID) REFERENCES Fuehrung(Fuehrung_ID)
);

-- Gehege
INSERT INTO Gehege (Gehegeart) VALUES ('Savanne'), ('Regenwald'), ('Aquarium');

-- Tier
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) 
VALUES 
('Elefant', 'Ella', 12, 1),
('Tiger', 'Shere Khan', 7, 2),
('Clownfisch', 'Nemo', 2, 3);

-- Gesundheitsakte
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID)
VALUES 
('Impfung', '2025-05-01', 1),
('Untersuchung', '2025-04-15', 2);

-- Nahrungsart
INSERT INTO Nahrungsart (Bezeichnung) VALUES ('Heu'), ('Fleisch'), ('Fischfutter');

-- Fütterungsplan
INSERT INTO Fuetterungsplan (Uhrzeit, Datum) 
VALUES 
('08:00', '2025-05-10'),
('12:00', '2025-05-10'),
('16:00', '2025-05-10');

-- Pfleger
INSERT INTO Pfleger (Vorname, Nachname, SVNR)
VALUES 
('Anna', 'Müller', '1234'),
('Ben', 'Schmidt', '5678');

-- Inventar
INSERT INTO Inventar (Bezeichnung)
VALUES ('Schubkarre'), ('Wasserpumpe'), ('Erste-Hilfe-Kasten');

-- Führung
INSERT INTO Fuehrung (Uhrzeit, Datum, Gehegeroute)
VALUES 
('10:00', '2025-05-11', 'Savanne -> Regenwald'),
('14:00', '2025-05-11', 'Aquarium');

-- Besucher
INSERT INTO Besucher (Vorname, Nachname)
VALUES 
('Clara', 'Weber'),
('David', 'Becker');


-- Pflegt
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (1, 1), (2, 2);

-- Reinigt
INSERT INTO Reinigt (Gehege_ID, Pfleger_ID) VALUES (1, 1), (2, 2);

-- Verwaltet
INSERT INTO Verwaltet (Pfleger_ID, Inventar_ID) VALUES (1, 1), (1, 3), (2, 2);

-- Fuehrt_durch
INSERT INTO Fuehrt_durch (Plan_ID, Pfleger_ID) VALUES (1, 1), (2, 2);

-- Fuetterungsplan_Nahrungsart
INSERT INTO Fuetterungsplan_Nahrungsart (Plan_ID, Nahrung_ID) VALUES (1, 1), (2, 2), (3, 3);

-- Veranstaltet
INSERT INTO Veranstaltet (Pfleger_ID, Fuehrung_ID) VALUES (1, 1), (2, 2);

-- Besucht
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (1, 1), (2, 2);
