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
	GebDat DATE NOT NULL,
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

-- ALLE INSERTS BLOCK
-- ALLE GEHEGE
INSERT INTO Gehege (Gehegeart) VALUES ('Savanne');
INSERT INTO Gehege (Gehegeart) VALUES ('Regenwald');
INSERT INTO Gehege (Gehegeart) VALUES ('Aquarium');
INSERT INTO Gehege (Gehegeart) VALUES ('Grasland');
INSERT INTO Gehege (Gehegeart) VALUES ('Arktis');

-- ALLE NAHRUNGSARTEN
INSERT INTO Nahrungsart (Bezeichnung) VALUES ('Heu');
INSERT INTO Nahrungsart (Bezeichnung) VALUES ('Fleisch');
INSERT INTO Nahrungsart (Bezeichnung) VALUES ('Fischfutter');
INSERT INTO Nahrungsart (Bezeichnung) VALUES ('Obst');
INSERT INTO Nahrungsart (Bezeichnung) VALUES ('Pflanzen');
INSERT INTO Nahrungsart (Bezeichnung) VALUES ('Insekten');
INSERT INTO Nahrungsart (Bezeichnung) VALUES ('Samen');
INSERT INTO Nahrungsart (Bezeichnung) VALUES ('Eukalyptus');
INSERT INTO Nahrungsart (Bezeichnung) VALUES ('Nüsse');

-- ALLE INVENTARITEMS
INSERT INTO Inventar (Bezeichnung) VALUES ('Schubkarre');
INSERT INTO Inventar (Bezeichnung) VALUES ('Wasserpumpe');
INSERT INTO Inventar (Bezeichnung) VALUES ('Erste-Hilfe-Kasten');
INSERT INTO Inventar (Bezeichnung) VALUES ('Heugabel');
INSERT INTO Inventar (Bezeichnung) VALUES ('Hochdruckreiniger');
INSERT INTO Inventar (Bezeichnung) VALUES ('Wüstenbuggy');
INSERT INTO Inventar (Bezeichnung) VALUES ('Schutzhandschuhe');
INSERT INTO Inventar (Bezeichnung) VALUES ('Leiter');
INSERT INTO Inventar (Bezeichnung) VALUES ('Gewehr');
INSERT INTO Inventar (Bezeichnung) VALUES ('Gabelstapler');
INSERT INTO Inventar (Bezeichnung) VALUES ('Fernglas');
INSERT INTO Inventar (Bezeichnung) VALUES ('Axt');
INSERT INTO Inventar (Bezeichnung) VALUES ('Kübel');
INSERT INTO Inventar (Bezeichnung) VALUES ('Seil');
INSERT INTO Inventar (Bezeichnung) VALUES ('Spielzeug');
INSERT INTO Inventar (Bezeichnung) VALUES ('Zange');
INSERT INTO Inventar (Bezeichnung) VALUES ('Abstellregal');
INSERT INTO Inventar (Bezeichnung) VALUES ('Schutzanzug');
INSERT INTO Inventar (Bezeichnung) VALUES ('Betäubungsgewehr');
INSERT INTO Inventar (Bezeichnung) VALUES ('Gewehrmunition');

-- ALLE PFLEGER
INSERT INTO Pfleger (Vorname, Nachname, SVNR, GebDat) VALUES ('Felicitas', 'Dowerg', '2824', '1962-09-21');
INSERT INTO Pfleger (Vorname, Nachname, SVNR, GebDat) VALUES ('Andrei', 'Weinhage', '5506', '1982-06-13');
INSERT INTO Pfleger (Vorname, Nachname, SVNR, GebDat) VALUES ('Karl Heinz', 'Henschel', '4657', '1972-12-30');
INSERT INTO Pfleger (Vorname, Nachname, SVNR, GebDat) VALUES ('Hellmuth', 'Etzold', '2679', '1968-04-11');
INSERT INTO Pfleger (Vorname, Nachname, SVNR, GebDat) VALUES ('Jennifer', 'Dobes', '7912', '1963-05-01');
INSERT INTO Pfleger (Vorname, Nachname, SVNR, GebDat) VALUES ('Gülay', 'Weinhage', '1488', '1968-11-19');
INSERT INTO Pfleger (Vorname, Nachname, SVNR, GebDat) VALUES ('Rosalie', 'Rogge', '4582', '1981-05-08');
INSERT INTO Pfleger (Vorname, Nachname, SVNR, GebDat) VALUES ('Dieter', 'Scheel', '9279', '1962-11-10');
INSERT INTO Pfleger (Vorname, Nachname, SVNR, GebDat) VALUES ('Sigfried', 'Beckmann', '4257', '1998-02-11');
INSERT INTO Pfleger (Vorname, Nachname, SVNR, GebDat) VALUES ('Ansgar', 'Davids', '4611', '2000-10-11');
INSERT INTO Pfleger (Vorname, Nachname, SVNR, GebDat) VALUES ('Heinz-Jürgen', 'Heintze', '5557', '1961-01-22');
INSERT INTO Pfleger (Vorname, Nachname, SVNR, GebDat) VALUES ('Alwine', 'Schleich', '3615', '1998-05-24');
INSERT INTO Pfleger (Vorname, Nachname, SVNR, GebDat) VALUES ('Andrew', 'Rose', '6574', '1985-05-29');
INSERT INTO Pfleger (Vorname, Nachname, SVNR, GebDat) VALUES ('Hans-Uwe', 'Tschentscher', '3547', '1979-10-18');
INSERT INTO Pfleger (Vorname, Nachname, SVNR, GebDat) VALUES ('Friedlinde', 'Trapp', '6514', '1969-08-25');
INSERT INTO Pfleger (Vorname, Nachname, SVNR, GebDat) VALUES ('Beatrix', 'Linke', '2519', '1994-07-25');
INSERT INTO Pfleger (Vorname, Nachname, SVNR, GebDat) VALUES ('Heinz-Werner', 'Mende', '2584', '1992-09-07');
INSERT INTO Pfleger (Vorname, Nachname, SVNR, GebDat) VALUES ('Damaris', 'Holsten', '6635', '1984-03-17');
INSERT INTO Pfleger (Vorname, Nachname, SVNR, GebDat) VALUES ('Mara', 'Albers', '1711', '2001-09-11');
INSERT INTO Pfleger (Vorname, Nachname, SVNR, GebDat) VALUES ('Katy', 'Freudenberger', '9785', '1971-09-05');


-- ALLE BESUCHER
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Hilda', 'Laffler');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Milos', 'Holsten');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Gerold', 'Hande');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Vanessa', 'Wilms');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Michail', 'Dobes');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Dittmar', 'Kramer');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Dusan', 'Klemt');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Mina', 'Kaul');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Doris', 'Hettner');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Maja', 'Beyer');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Janet', 'Misicher');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Maxim', 'Ebert');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Xenia', 'Kostolzin');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Damian', 'Ruhrdanz');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Lars', 'Schuchhardt');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Elli', 'Klotz');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Celine', 'Grottner');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Ida', 'Bohnbach');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Bastian', 'Spiess');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Henry', 'Wulf');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Klaus', 'Budig');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Natascha', 'Heintze');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Nora', 'Ditschlerin');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Raimund', 'Holsten');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Traude', 'Schweitzer');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Marta', 'Knappe');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Gottfried', 'Kocher');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Norman', 'Horing');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Grete', 'Hetur');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Hiltraud', 'Striebitz');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Friederike', 'Boucsein');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Eileen', 'Schweitzer');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Günter', 'Reising');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Jane', 'Henschel');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Gottlieb', 'Mitschke');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Raik', 'Hiller');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Stephanie', 'Segebahn');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Heidi', 'Römer');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Heinz-Peter', 'Stroh');
INSERT INTO Besucher (Vorname, Nachname) VALUES ('Marten', 'Wirth');

-- ALLE TIERE
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Zebra', 'Leni', 15, 5);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Giraffe', 'Bertram', 9, 2);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Nilpferd', 'Hermann', 4, 3);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Löwe', 'Marie', 4, 4);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Eisbär', 'Anton', 7, 1);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Tiger', 'Magdalene', 8, 1);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Giraffe', 'Mahmut', 12, 2);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Zebra', 'Rolf-Dieter', 6, 4);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Elefant', 'Juan', 8, 1);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Eisbär', 'Celal', 11, 2);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Pinguin', 'Heimo', 5, 2);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Eisbär', 'Sigrid', 13, 3);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Tiger', 'Therese', 5, 2);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Eisbär', 'Caroline', 15, 5);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Löwe', 'Peggy', 9, 3);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Elefant', 'Irma', 9, 5);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Löwe', 'Magnus', 20, 2);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Löwe', 'Heini', 1, 5);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Giraffe', 'Gerdi', 10, 2);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Tiger', 'Alice', 18, 2);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Pinguin', 'Riza', 17, 5);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Giraffe', 'Reingard', 15, 5);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Tiger', 'Silja', 11, 1);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Zebra', 'Francesca', 1, 1);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Tiger', 'Valentin', 2, 3);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Krokodil', 'Friedhold', 7, 5);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Krokodil', 'Joseph', 14, 2);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Nilpferd', 'Fredo', 15, 1);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Giraffe', 'Istvan', 4, 2);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Clownfisch', 'Juliana', 9, 5);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Elefant', 'Camilla', 18, 1);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Krokodil', 'Augusta', 16, 3);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Elefant', 'Jonathan', 13, 4);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Clownfisch', 'Justine', 7, 4);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Elefant', 'Corinne', 2, 5);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Löwe', 'Stanislaw', 3, 3);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Nilpferd', 'Rosmarie', 4, 3);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Tiger', 'Corina', 14, 4);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Nilpferd', 'Roderich', 5, 4);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Krokodil', 'Othmar', 20, 1);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Pinguin', 'Heinz-Peter', 5, 4);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Krokodil', 'Frederik', 18, 4);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Tiger', 'Anett', 5, 4);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Pinguin', 'Änne', 20, 3);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Löwe', 'Dino', 16, 4);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Elefant', 'Kathi', 11, 1);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Löwe', 'Bekir', 14, 5);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Löwe', 'Natascha', 2, 3);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Clownfisch', 'Emanuel', 2, 3);
INSERT INTO Tier (Tierart, Name, Alter, Gehege_ID) VALUES ('Tiger', 'Gerhardt', 12, 1);

-- ALLE GESUNDHEITSAKTEN
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Untersuchung', '2025-06-13', 1);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Impfung', '2025-03-20', 2);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Untersuchung', '2025-01-11', 3);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Impfung', '2025-01-24', 4);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Impfung', '2024-12-27', 5);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Untersuchung', '2025-04-04', 6);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Zahnpflege', '2025-04-07', 7);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Untersuchung', '2025-04-06', 8);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Untersuchung', '2025-03-04', 9);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Zahnpflege', '2025-02-07', 10);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Zahnpflege', '2025-01-22', 11);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Impfung', '2025-05-10', 12);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Zahnpflege', '2025-02-26', 13);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Untersuchung', '2025-01-24', 14);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Impfung', '2025-03-31', 15);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Impfung', '2025-02-05', 16);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Impfung', '2025-03-11', 17);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Untersuchung', '2025-02-09', 18);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Impfung', '2025-04-14', 19);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Impfung', '2024-12-28', 20);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Untersuchung', '2025-04-21', 21);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Untersuchung', '2025-05-15', 22);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Zahnpflege', '2025-01-24', 23);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Zahnpflege', '2025-01-04', 24);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Impfung', '2025-02-03', 25);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Impfung', '2024-12-11', 26);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Impfung', '2025-05-21', 27);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Zahnpflege', '2024-12-29', 28);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Impfung', '2025-04-27', 29);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Impfung', '2025-05-26', 30);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Impfung', '2025-04-15', 31);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Untersuchung', '2025-05-30', 32);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Untersuchung', '2025-04-02', 33);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Impfung', '2025-05-31', 34);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Zahnpflege', '2025-01-30', 35);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Impfung', '2025-01-13', 36);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Zahnpflege', '2025-01-13', 37);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Untersuchung', '2025-04-23', 38);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Untersuchung', '2025-03-26', 39);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Impfung', '2025-01-28', 40);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Impfung', '2025-04-13', 41);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Zahnpflege', '2024-12-29', 42);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Impfung', '2025-05-18', 43);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Zahnpflege', '2025-03-19', 44);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Impfung', '2025-05-22', 45);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Zahnpflege', '2025-04-08', 46);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Impfung', '2025-05-17', 47);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Zahnpflege', '2025-01-24', 48);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Untersuchung', '2025-06-04', 49);
INSERT INTO Gesundheitsakte (Behandlungsart, Behandlungsdatum, Tier_ID) VALUES ('Zahnpflege', '2025-05-06', 50);

-- ALLE FEUTTERUNGSPLAENE
INSERT INTO Fuetterungsplan (Uhrzeit, Datum) VALUES ('8:00', '2025-06-24');
INSERT INTO Fuetterungsplan (Uhrzeit, Datum) VALUES ('10:00', '2025-06-25');
INSERT INTO Fuetterungsplan (Uhrzeit, Datum) VALUES ('12:00', '2025-06-26');
INSERT INTO Fuetterungsplan (Uhrzeit, Datum) VALUES ('14:00', '2025-06-27');
INSERT INTO Fuetterungsplan (Uhrzeit, Datum) VALUES ('16:00', '2025-06-28');
INSERT INTO Fuetterungsplan (Uhrzeit, Datum) VALUES ('18:00', '2025-06-29');

-- ALLE FUEHRUNGEN
INSERT INTO Fuehrung (Uhrzeit, Datum, Gehegeroute) VALUES ('8:00', '2025-06-24', 'Savanne');
INSERT INTO Fuehrung (Uhrzeit, Datum, Gehegeroute) VALUES ('9:00', '2025-06-25', 'Aquarium');
INSERT INTO Fuehrung (Uhrzeit, Datum, Gehegeroute) VALUES ('10:00', '2025-06-26', 'Grasland');
INSERT INTO Fuehrung (Uhrzeit, Datum, Gehegeroute) VALUES ('11:00', '2025-06-27', 'Arktis');
INSERT INTO Fuehrung (Uhrzeit, Datum, Gehegeroute) VALUES ('12:00', '2025-06-28', 'Grasland');
INSERT INTO Fuehrung (Uhrzeit, Datum, Gehegeroute) VALUES ('13:00', '2025-06-29', 'Grasland');
INSERT INTO Fuehrung (Uhrzeit, Datum, Gehegeroute) VALUES ('14:00', '2025-06-30', 'Arktis');
INSERT INTO Fuehrung (Uhrzeit, Datum, Gehegeroute) VALUES ('15:00', '2025-07-01', 'Regenwald');
INSERT INTO Fuehrung (Uhrzeit, Datum, Gehegeroute) VALUES ('16:00', '2025-07-02', 'Aquarium');
INSERT INTO Fuehrung (Uhrzeit, Datum, Gehegeroute) VALUES ('17:00', '2025-07-03', 'Arktis');

-- WELCHER PFLEGER PFLEGT WELCHE TIERE
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (14, 1);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (3, 2);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (2, 3);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (15, 3);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (20, 4);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (12, 4);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (10, 5);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (12, 6);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (6, 6);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (3, 7);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (6, 7);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (18, 7);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (18, 8);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (8, 8);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (11, 8);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (3, 9);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (7, 9);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (15, 10);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (5, 10);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (9, 11);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (19, 11);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (14, 11);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (16, 12);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (3, 12);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (2, 12);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (3, 13);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (13, 13);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (19, 13);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (4, 14);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (6, 15);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (15, 15);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (10, 16);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (18, 17);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (4, 18);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (3, 19);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (20, 19);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (16, 19);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (18, 20);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (6, 20);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (7, 21);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (10, 21);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (13, 21);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (8, 22);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (19, 23);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (8, 24);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (9, 25);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (19, 26);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (16, 26);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (8, 26);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (14, 27);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (12, 27);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (20, 27);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (4, 28);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (2, 28);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (13, 28);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (15, 29);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (5, 29);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (14, 29);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (18, 30);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (4, 30);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (14, 31);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (13, 32);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (18, 33);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (16, 33);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (18, 34);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (2, 34);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (11, 34);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (2, 35);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (8, 36);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (20, 37);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (11, 38);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (8, 38);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (9, 38);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (1, 39);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (17, 40);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (10, 41);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (6, 41);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (1, 42);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (18, 42);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (10, 42);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (18, 43);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (5, 43);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (9, 43);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (9, 44);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (14, 45);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (9, 45);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (2, 45);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (15, 46);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (5, 47);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (14, 48);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (7, 49);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (8, 49);
INSERT INTO Pflegt (Pfleger_ID, Tier_ID) VALUES (6, 50);

-- WELCHER PFLEGER REINIGT WELCHES GEHEGE
INSERT INTO Reinigt (Gehege_ID, Pfleger_ID) VALUES (1, 14);
INSERT INTO Reinigt (Gehege_ID, Pfleger_ID) VALUES (2, 6);
INSERT INTO Reinigt (Gehege_ID, Pfleger_ID) VALUES (3, 11);
INSERT INTO Reinigt (Gehege_ID, Pfleger_ID) VALUES (3, 14);
INSERT INTO Reinigt (Gehege_ID, Pfleger_ID) VALUES (3, 8);
INSERT INTO Reinigt (Gehege_ID, Pfleger_ID) VALUES (4, 6);
INSERT INTO Reinigt (Gehege_ID, Pfleger_ID) VALUES (4, 4);
INSERT INTO Reinigt (Gehege_ID, Pfleger_ID) VALUES (5, 2);
INSERT INTO Reinigt (Gehege_ID, Pfleger_ID) VALUES (5, 16);

-- WELCHER PFLEGER VERWALTET WELCHE INVENTARITEMS
INSERT INTO Verwaltet (Pfleger_ID, Inventar_ID) VALUES (1, 20);
INSERT INTO Verwaltet (Pfleger_ID, Inventar_ID) VALUES (2, 2);
INSERT INTO Verwaltet (Pfleger_ID, Inventar_ID) VALUES (3, 16);
INSERT INTO Verwaltet (Pfleger_ID, Inventar_ID) VALUES (4, 5);
INSERT INTO Verwaltet (Pfleger_ID, Inventar_ID) VALUES (5, 9);
INSERT INTO Verwaltet (Pfleger_ID, Inventar_ID) VALUES (6, 11);
INSERT INTO Verwaltet (Pfleger_ID, Inventar_ID) VALUES (7, 3);
INSERT INTO Verwaltet (Pfleger_ID, Inventar_ID) VALUES (8, 6);
INSERT INTO Verwaltet (Pfleger_ID, Inventar_ID) VALUES (9, 19);
INSERT INTO Verwaltet (Pfleger_ID, Inventar_ID) VALUES (10, 17);
INSERT INTO Verwaltet (Pfleger_ID, Inventar_ID) VALUES (11, 1);
INSERT INTO Verwaltet (Pfleger_ID, Inventar_ID) VALUES (12, 18);
INSERT INTO Verwaltet (Pfleger_ID, Inventar_ID) VALUES (13, 4);
INSERT INTO Verwaltet (Pfleger_ID, Inventar_ID) VALUES (14, 13);
INSERT INTO Verwaltet (Pfleger_ID, Inventar_ID) VALUES (15, 14);
INSERT INTO Verwaltet (Pfleger_ID, Inventar_ID) VALUES (16, 10);
INSERT INTO Verwaltet (Pfleger_ID, Inventar_ID) VALUES (17, 12);
INSERT INTO Verwaltet (Pfleger_ID, Inventar_ID) VALUES (18, 15);
INSERT INTO Verwaltet (Pfleger_ID, Inventar_ID) VALUES (19, 7);
INSERT INTO Verwaltet (Pfleger_ID, Inventar_ID) VALUES (20, 8);

-- WELCHER PFLEGER FUEHRT WELCHE FUETTERUNGSPLAN DURCH
INSERT INTO Fuehrt_durch (Plan_ID, Pfleger_ID) VALUES (1, 11);
INSERT INTO Fuehrt_durch (Plan_ID, Pfleger_ID) VALUES (1, 1);
INSERT INTO Fuehrt_durch (Plan_ID, Pfleger_ID) VALUES (1, 4);
INSERT INTO Fuehrt_durch (Plan_ID, Pfleger_ID) VALUES (1, 9);
INSERT INTO Fuehrt_durch (Plan_ID, Pfleger_ID) VALUES (1, 6);
INSERT INTO Fuehrt_durch (Plan_ID, Pfleger_ID) VALUES (2, 14);
INSERT INTO Fuehrt_durch (Plan_ID, Pfleger_ID) VALUES (2, 12);
INSERT INTO Fuehrt_durch (Plan_ID, Pfleger_ID) VALUES (2, 11);
INSERT INTO Fuehrt_durch (Plan_ID, Pfleger_ID) VALUES (2, 20);
INSERT INTO Fuehrt_durch (Plan_ID, Pfleger_ID) VALUES (2, 4);
INSERT INTO Fuehrt_durch (Plan_ID, Pfleger_ID) VALUES (3, 14);
INSERT INTO Fuehrt_durch (Plan_ID, Pfleger_ID) VALUES (4, 7);
INSERT INTO Fuehrt_durch (Plan_ID, Pfleger_ID) VALUES (4, 12);
INSERT INTO Fuehrt_durch (Plan_ID, Pfleger_ID) VALUES (4, 14);
INSERT INTO Fuehrt_durch (Plan_ID, Pfleger_ID) VALUES (4, 3);
INSERT INTO Fuehrt_durch (Plan_ID, Pfleger_ID) VALUES (4, 11);
INSERT INTO Fuehrt_durch (Plan_ID, Pfleger_ID) VALUES (5, 17);
INSERT INTO Fuehrt_durch (Plan_ID, Pfleger_ID) VALUES (5, 10);
INSERT INTO Fuehrt_durch (Plan_ID, Pfleger_ID) VALUES (5, 14);
INSERT INTO Fuehrt_durch (Plan_ID, Pfleger_ID) VALUES (6, 5);
INSERT INTO Fuehrt_durch (Plan_ID, Pfleger_ID) VALUES (6, 7);
INSERT INTO Fuehrt_durch (Plan_ID, Pfleger_ID) VALUES (6, 14);
INSERT INTO Fuehrt_durch (Plan_ID, Pfleger_ID) VALUES (6, 13);
INSERT INTO Fuehrt_durch (Plan_ID, Pfleger_ID) VALUES (6, 6);

-- WELCHER FUETTERUNGSPLAN ENTHAELT WELCHE NAHRUNGSART
INSERT INTO Fuetterungsplan_Nahrungsart (Plan_ID, Nahrung_ID) VALUES (1, 5);
INSERT INTO Fuetterungsplan_Nahrungsart (Plan_ID, Nahrung_ID) VALUES (1, 1);
INSERT INTO Fuetterungsplan_Nahrungsart (Plan_ID, Nahrung_ID) VALUES (1, 8);
INSERT INTO Fuetterungsplan_Nahrungsart (Plan_ID, Nahrung_ID) VALUES (2, 4);
INSERT INTO Fuetterungsplan_Nahrungsart (Plan_ID, Nahrung_ID) VALUES (2, 5);
INSERT INTO Fuetterungsplan_Nahrungsart (Plan_ID, Nahrung_ID) VALUES (3, 9);
INSERT INTO Fuetterungsplan_Nahrungsart (Plan_ID, Nahrung_ID) VALUES (4, 6);
INSERT INTO Fuetterungsplan_Nahrungsart (Plan_ID, Nahrung_ID) VALUES (4, 2);
INSERT INTO Fuetterungsplan_Nahrungsart (Plan_ID, Nahrung_ID) VALUES (4, 9);
INSERT INTO Fuetterungsplan_Nahrungsart (Plan_ID, Nahrung_ID) VALUES (5, 7);
INSERT INTO Fuetterungsplan_Nahrungsart (Plan_ID, Nahrung_ID) VALUES (5, 5);
INSERT INTO Fuetterungsplan_Nahrungsart (Plan_ID, Nahrung_ID) VALUES (6, 5);
INSERT INTO Fuetterungsplan_Nahrungsart (Plan_ID, Nahrung_ID) VALUES (6, 7);
INSERT INTO Fuetterungsplan_Nahrungsart (Plan_ID, Nahrung_ID) VALUES (6, 9);

-- WELCHER PFLEGER VERANSTALTET WELCHE FUEHRUNGEN
INSERT INTO Veranstaltet (Pfleger_ID, Fuehrung_ID) VALUES (10, 1);
INSERT INTO Veranstaltet (Pfleger_ID, Fuehrung_ID) VALUES (7, 2);
INSERT INTO Veranstaltet (Pfleger_ID, Fuehrung_ID) VALUES (19, 3);
INSERT INTO Veranstaltet (Pfleger_ID, Fuehrung_ID) VALUES (11, 4);
INSERT INTO Veranstaltet (Pfleger_ID, Fuehrung_ID) VALUES (15, 5);
INSERT INTO Veranstaltet (Pfleger_ID, Fuehrung_ID) VALUES (7, 6);
INSERT INTO Veranstaltet (Pfleger_ID, Fuehrung_ID) VALUES (16, 7);
INSERT INTO Veranstaltet (Pfleger_ID, Fuehrung_ID) VALUES (3, 8);
INSERT INTO Veranstaltet (Pfleger_ID, Fuehrung_ID) VALUES (17, 9);
INSERT INTO Veranstaltet (Pfleger_ID, Fuehrung_ID) VALUES (11, 10);

-- WELCHER BESUCHER BESUCHT WELCHE FUEHRUNG
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (1, 4);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (2, 5);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (2, 4);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (2, 9);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (3, 1);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (4, 4);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (5, 10);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (5, 2);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (6, 7);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (6, 4);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (7, 7);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (7, 8);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (7, 10);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (8, 3);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (9, 1);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (9, 2);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (9, 7);


INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (10, 3);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (11, 9);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (11, 8);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (11, 1);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (12, 4);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (12, 2);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (12, 8);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (13, 8);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (14, 9);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (14, 10);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (14, 6);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (15, 10);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (15, 9);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (16, 9);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (16, 8);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (17, 8);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (18, 5);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (18, 4);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (19, 5);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (19, 9);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (19, 8);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (20, 4);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (20, 5);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (20, 8);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (21, 5);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (22, 5);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (23, 6);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (23, 9);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (24, 3);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (25, 4);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (26, 3);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (26, 4);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (27, 7);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (28, 6);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (28, 9);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (29, 7);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (29, 1);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (30, 7);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (31, 10);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (31, 1);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (32, 7);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (32, 8);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (32, 1);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (33, 5);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (33, 7);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (34, 9);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (34, 10);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (35, 4);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (35, 8);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (35, 10);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (36, 7);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (36, 8);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (37, 7);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (38, 7);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (38, 3);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (39, 3);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (39, 9);
INSERT INTO Besucht (Besucher_ID, Fuehrung_ID) VALUES (40, 7);