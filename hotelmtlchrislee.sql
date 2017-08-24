-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 12, 2017 at 04:08 AM
-- Server version: 10.1.25-MariaDB
-- PHP Version: 7.1.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hotelmtlchrislee`
--

-- --------------------------------------------------------

--
-- Table structure for table `Guests`
--

CREATE TABLE `guests` (
  `GuestID` varchar(15) NOT NULL,
  `lastName` varchar(15) DEFAULT NULL,
  `firstName` varchar(15) DEFAULT NULL,
  `phoneNumber` varchar(15) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Guests`
--

INSERT INTO `guests` (`GuestID`, `lastName`, `firstName`, `phoneNumber`, `email`) VALUES
('MTLChrisLEE', 'LEE', 'SH Chris', '1321234568', 'test@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `Reservation`
--

CREATE TABLE `reservation` (
  `GuestID` varchar(15) DEFAULT NULL,
  `RoomNumber` int(4) DEFAULT NULL,
  `checkinDate` date DEFAULT NULL,
  `checkoutDate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Rooms`
--

CREATE TABLE `rooms` (
  `RoomNumber` int(4) NOT NULL,
  `rate` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Rooms`
--

INSERT INTO `rooms` (`RoomNumber`, `rate`) VALUES
(100, 80),
(101, 80),
(102, 80),
(103, 80),
(104, 80),
(105, 80),
(106, 80),
(107, 80),
(108, 80),
(109, 80),
(110, 80),
(200, 80),
(201, 80),
(202, 80),
(203, 80),
(204, 80),
(205, 80),
(206, 80),
(207, 80),
(208, 80),
(209, 80),
(210, 80),
(300, 80),
(301, 80),
(302, 80),
(303, 80),
(304, 80),
(305, 80),
(306, 80),
(307, 80),
(308, 80),
(309, 80),
(310, 80),
(400, 120),
(401, 120),
(402, 120),
(403, 120),
(404, 120),
(405, 120),
(406, 120),
(407, 120),
(408, 120),
(409, 120),
(410, 120),
(500, 120),
(501, 120),
(502, 120),
(503, 120),
(504, 120),
(505, 120),
(506, 120),
(507, 120),
(508, 120),
(509, 120),
(510, 120),
(600, 150),
(601, 150),
(602, 150),
(603, 150),
(604, 150),
(605, 150),
(700, 150),
(701, 150),
(702, 150),
(703, 150),
(704, 150),
(705, 150),
(800, 250),
(900, 250);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Guests`
--
ALTER TABLE `guests`
  ADD PRIMARY KEY (`GuestID`);

--
-- Indexes for table `Reservation`
--
ALTER TABLE `reservation`
  ADD KEY `GuestID` (`GuestID`),
  ADD KEY `RoomNumber` (`RoomNumber`);

--
-- Indexes for table `Rooms`
--
ALTER TABLE `rooms`
  ADD PRIMARY KEY (`RoomNumber`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `reservation_ibfk_1` FOREIGN KEY (`GuestID`) REFERENCES `guests` (`GuestID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `reservation_ibfk_2` FOREIGN KEY (`RoomNumber`) REFERENCES `rooms` (`RoomNumber`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
