SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;


CREATE TABLE `auspraegung` (
  `id` int(11) NOT NULL,
  `wert` varchar(100) NOT NULL,
  `eigenschaftid` int(11) NOT NULL,
  `kontaktid` int(11) NOT NULL,
  `status` tinyint(1) NOT NULL,
  `ownerid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `berechtigung` (
  `id` int(11) NOT NULL,
  `ownerid` int(11) DEFAULT NULL,
  `receiverid` int(11) DEFAULT NULL,
  `objectid` int(11) DEFAULT NULL,
  `type` char(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `eigenschaft` (
  `id` int(11) NOT NULL,
  `bezeichnung` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `kontakt` (
  `id` int(11) NOT NULL,
  `vorname` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `nachname` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `erstellungsdatum` datetime NOT NULL,
  `modifikationsdatum` datetime NOT NULL,
  `ownerid` int(11) NOT NULL,
  `identifier` char(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `kontaktliste` (
  `id` int(11) NOT NULL,
  `titel` varchar(100) DEFAULT NULL,
  `ownerid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `kontaktlistekontakt` (
  `kontaktlisteid` int(11) NOT NULL DEFAULT '0',
  `kontaktid` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `nutzer` (
  `id` int(11) NOT NULL,
  `email` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE `auspraegung`
  ADD PRIMARY KEY (`id`),
  ADD KEY `bfk_a_1` (`eigenschaftid`),
  ADD KEY `bfk_a_2` (`kontaktid`),
  ADD KEY `bfk_a_3` (`ownerid`);

ALTER TABLE `berechtigung`
  ADD PRIMARY KEY (`id`,`type`),
  ADD KEY `b_ibfk_1` (`ownerid`),
  ADD KEY `b_ibfk_2` (`receiverid`),
  ADD KEY `b_ibfk_33` (`objectid`);

ALTER TABLE `eigenschaft`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `kontakt`
  ADD PRIMARY KEY (`id`),
  ADD KEY `k_bfk_1` (`ownerid`);

ALTER TABLE `kontaktliste`
  ADD PRIMARY KEY (`id`),
  ADD KEY `kl_bfk_1` (`ownerid`);

ALTER TABLE `kontaktlistekontakt`
  ADD PRIMARY KEY (`kontaktlisteid`,`kontaktid`),
  ADD KEY `bfk2` (`kontaktid`);

ALTER TABLE `nutzer`
  ADD PRIMARY KEY (`id`);


ALTER TABLE `auspraegung`
  ADD CONSTRAINT `bfk_a_1` FOREIGN KEY (`eigenschaftid`) REFERENCES `eigenschaft` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `bfk_a_2` FOREIGN KEY (`kontaktid`) REFERENCES `kontakt` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `bfk_a_3` FOREIGN KEY (`ownerid`) REFERENCES `nutzer` (`id`) ON UPDATE CASCADE;

ALTER TABLE `berechtigung`
  ADD CONSTRAINT `b_ibfk_1` FOREIGN KEY (`ownerid`) REFERENCES `nutzer` (`id`),
  ADD CONSTRAINT `b_ibfk_2` FOREIGN KEY (`receiverid`) REFERENCES `nutzer` (`id`);

ALTER TABLE `kontakt`
  ADD CONSTRAINT `k_bfk_1` FOREIGN KEY (`ownerid`) REFERENCES `nutzer` (`id`) ON UPDATE CASCADE;

ALTER TABLE `kontaktliste`
  ADD CONSTRAINT `kl_bfk_1` FOREIGN KEY (`ownerid`) REFERENCES `nutzer` (`id`) ON UPDATE CASCADE;

ALTER TABLE `kontaktlistekontakt`
  ADD CONSTRAINT `bfk1` FOREIGN KEY (`kontaktlisteid`) REFERENCES `kontaktliste` (`id`),
  ADD CONSTRAINT `bfk2` FOREIGN KEY (`kontaktid`) REFERENCES `kontakt` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
