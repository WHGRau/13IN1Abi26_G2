-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Erstellungszeit: 02. Okt 2025 um 14:28
-- Server-Version: 10.4.28-MariaDB
-- PHP-Version: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `restaurant_db`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `benutzer`
--

CREATE TABLE `benutzer` (
  `id` int(11) NOT NULL,
  `nutzername` varchar(30) NOT NULL,
  `email` varchar(50) NOT NULL,
  `passwort` varchar(50) NOT NULL,
  `vorname` varchar(50) NOT NULL,
  `nachname` varchar(50) NOT NULL,
  `geburtsdatum` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `benutzer`
--

INSERT INTO `benutzer` (`id`, `nutzername`, `email`, `passwort`, `vorname`, `nachname`, `geburtsdatum`) VALUES
(1, 'maxmustermann', 'max@beispiel.de', 'pass123', 'Max', 'Mustermann', '1985-05-15'),
(2, 'lisalange', 'lisa@beispiel.de', 'pass456', 'Lisa', 'Lange', '1990-08-22'),
(3, 'tomschmidt', 'tom@beispiel.de', 'pass789', 'Tom', 'Schmidt', '1978-11-10'),
(4, 'sarawolf', 'sara@beispiel.de', 'pass101', 'Sara', 'Wolf', '1995-03-12'),
(5, 'felixmeier', 'felix@beispiel.de', 'pass202', 'Felix', 'Meier', '1988-09-07'),
(6, 'annaklein', 'anna@beispiel.de', 'pass303', 'Anna', 'Klein', '1992-06-30'),
(7, 'jensmuller', 'jens@beispiel.de', 'pass404', 'Jens', 'Müller', '1983-12-05'),
(8, 'claudiaschmitt', 'claudia@beispiel.de', 'pass505', 'Claudia', 'Schmitt', '1991-04-18'),
(9, 'markusbraun', 'markus@beispiel.de', 'pass606', 'Markus', 'Braun', '1980-07-22'),
(10, 'heidihofer', 'heidi@beispiel.de', 'pass707', 'Heidi', 'Hofer', '1994-11-11');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `gast`
--

CREATE TABLE `gast` (
  `id` int(11) NOT NULL,
  `nutzerId` int(11) NOT NULL,
  `telefonnummer` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `gast`
--

INSERT INTO `gast` (`id`, `nutzerId`, `telefonnummer`) VALUES
(1, 1, '0151-1234567'),
(2, 2, '0160-7654321'),
(3, 4, '0171-5554321'),
(4, 5, '0152-9876543'),
(5, 6, '0163-1122334');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `mitarbeiter`
--

CREATE TABLE `mitarbeiter` (
  `id` int(11) NOT NULL,
  `nutzerId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `mitarbeiter`
--

INSERT INTO `mitarbeiter` (`id`, `nutzerId`) VALUES
(1, 3),
(2, 7),
(3, 8);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `reservierung`
--

CREATE TABLE `reservierung` (
  `id` int(11) NOT NULL,
  `tischId` int(11) NOT NULL,
  `personenzahl` int(11) NOT NULL,
  `gastId` int(11) NOT NULL,
  `zeitpunkt` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `reservierung`
--

INSERT INTO `reservierung` (`id`, `tischId`, `personenzahl`, `gastId`, `zeitpunkt`) VALUES
(21, 2, 2, 1, '2025-09-16 19:00:00'),
(22, 3, 4, 2, '2025-09-16 20:30:00'),
(23, 1, 2, 3, '2025-09-16 18:30:00'),
(24, 5, 3, 4, '2025-09-17 19:45:00'),
(25, 4, 2, 5, '2025-09-17 20:15:00');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `tisch`
--

CREATE TABLE `tisch` (
  `id` int(11) NOT NULL,
  `nummer` int(11) NOT NULL,
  `kapazitaet` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `tisch`
--

INSERT INTO `tisch` (`id`, `nummer`, `kapazitaet`) VALUES
(1, 1, 2),
(2, 2, 4),
(3, 3, 6),
(4, 4, 2),
(5, 5, 4);

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `benutzer`
--
ALTER TABLE `benutzer`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `gast`
--
ALTER TABLE `gast`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_nutzer_id` (`nutzerId`);

--
-- Indizes für die Tabelle `mitarbeiter`
--
ALTER TABLE `mitarbeiter`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_nutzer_id2` (`nutzerId`);

--
-- Indizes für die Tabelle `reservierung`
--
ALTER TABLE `reservierung`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `fk_gast_id` (`gastId`),
  ADD KEY `fk_tisch_id` (`tischId`);

--
-- Indizes für die Tabelle `tisch`
--
ALTER TABLE `tisch`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `benutzer`
--
ALTER TABLE `benutzer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT für Tabelle `gast`
--
ALTER TABLE `gast`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT für Tabelle `mitarbeiter`
--
ALTER TABLE `mitarbeiter`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT für Tabelle `reservierung`
--
ALTER TABLE `reservierung`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT für Tabelle `tisch`
--
ALTER TABLE `tisch`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `gast`
--
ALTER TABLE `gast`
  ADD CONSTRAINT `fk_nutzer_id` FOREIGN KEY (`nutzerId`) REFERENCES `benutzer` (`id`);

--
-- Constraints der Tabelle `mitarbeiter`
--
ALTER TABLE `mitarbeiter`
  ADD CONSTRAINT `fk_nutzer_id2` FOREIGN KEY (`nutzerId`) REFERENCES `benutzer` (`id`);

--
-- Constraints der Tabelle `reservierung`
--
ALTER TABLE `reservierung`
  ADD CONSTRAINT `fk_tisch_id` FOREIGN KEY (`tischId`) REFERENCES `tisch` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
