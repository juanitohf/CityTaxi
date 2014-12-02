
/* This is develop by Tal */

-- phpMyAdmin SQL Dump
-- version 3.5.8
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Nov 23, 2014 at 09:28 AM
-- Server version: 5.5.40-36.1
-- PHP Version: 5.4.23

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `juanhf_City_Taxi`
--

-- --------------------------------------------------------

--
-- Table structure for table `REQUEST_STATUS`
--

CREATE TABLE IF NOT EXISTS `REQUEST_STATUS` (
  `RequestStatusID` int(11) NOT NULL AUTO_INCREMENT,
  `StatusDescription` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`RequestStatusID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=3 ;

--
-- Dumping data for table `REQUEST_STATUS`
--

INSERT INTO `REQUEST_STATUS` (`RequestStatusID`, `StatusDescription`) VALUES
(1, 'Active'),
(2, 'Inactive');

-- --------------------------------------------------------

--
-- Table structure for table `ROLE`
--

CREATE TABLE IF NOT EXISTS `ROLE` (
  `RoleID` int(11) NOT NULL AUTO_INCREMENT,
  `Description` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`RoleID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=4 ;

--
-- Dumping data for table `ROLE`
--

INSERT INTO `ROLE` (`RoleID`, `Description`) VALUES
(1, 'Client'),
(2, 'Taxi Driver'),
(3, 'Admin');

-- --------------------------------------------------------

--
-- Table structure for table `TAXI`
--

CREATE TABLE IF NOT EXISTS `TAXI` (
  `TaxiID` int(11) NOT NULL AUTO_INCREMENT,
  `CarModel` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `CabNumber` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `UserID` int(11) NOT NULL,
  PRIMARY KEY (`TaxiID`),
  KEY `UserID` (`UserID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=5 ;

--
-- Dumping data for table `TAXI`
--

INSERT INTO `TAXI` (`TaxiID`, `CarModel`, `CabNumber`, `UserID`) VALUES
(2, 'Audi A4', '1112', 2),
(3, 'Mercedes AMG', '1113', 3),
(4, 'Ford Fusion', '1333', 4);

-- --------------------------------------------------------

--
-- Table structure for table `USER`
--

CREATE TABLE IF NOT EXISTS `USER` (
  `UserID` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `Email` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `Password` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `Phone` char(12) COLLATE utf8_unicode_ci NOT NULL,
  `EffeciveDate` date NOT NULL,
  `Latitude` float(10,6) NOT NULL,
  `Longitude` float(10,6) NOT NULL,
  `RoleID` int(11) NOT NULL,
  PRIMARY KEY (`UserID`),
  KEY `RoleID` (`RoleID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=6 ;

--
-- Dumping data for table `USER`
--

INSERT INTO `USER` (`UserID`, `Username`, `Email`, `Password`, `Phone`, `EffeciveDate`, `Latitude`, `Longitude`, `RoleID`) VALUES
(1, 'juanitohf', 'juanitohf@gmail.com', '827ccb0eea8a706c4c34a16891f84e7b', '2673487342', '2014-10-23', 39.920315, -75.174011, 3),
(2, 'Erik Bullock', 'erik@gmail.com', '827ccb0eea8a706c4c34a16891f84e7b', '267342589', '2014-10-23', 39.920414, -75.173965, 2),
(3, 'Donna', 'donna@gmail.com', '827ccb0eea8a706c4c34a16891f84e7b', '215356987', '2014-10-25', 39.981071, -75.157867, 2),
(4, 'teiden', 'teiden@temple.edu', 'e99a18c428cb38d5f260853678922e03', '2155013634', '2014-11-13', 39.965740, -75.162422, 2),
(5, 'abc', 'a@b.com', 'ab56b4d92b40713acc5af89985d4b786', '1234567890', '2014-11-15', -75.041443, 40.116688, 1);

-- --------------------------------------------------------

--
-- Table structure for table `USER_requests_TAXI`
--

CREATE TABLE IF NOT EXISTS `USER_requests_TAXI` (
  `USER_requests_TAXI_ID` int(11) NOT NULL AUTO_INCREMENT,
  `UserEmail` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `TaxiID` int(11) NOT NULL,
  `DateOfRequest` date NOT NULL,
  `TimeOfRequest` time NOT NULL,
  `LocationOfCall_Lat` float NOT NULL,
  `LocationOfCall_Lon` float NOT NULL,
  `RequestStatusID` int(11) NOT NULL,
  PRIMARY KEY (`USER_requests_TAXI_ID`),
  KEY `UserID` (`UserEmail`,`TaxiID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=26 ;

--
-- Dumping data for table `USER_requests_TAXI`
--

INSERT INTO `USER_requests_TAXI` (`USER_requests_TAXI_ID`, `UserEmail`, `TaxiID`, `DateOfRequest`, `TimeOfRequest`, `LocationOfCall_Lat`, `LocationOfCall_Lon`, `RequestStatusID`) VALUES
(1, 'juanitohf@gmail.com', 0, '2014-11-20', '20:46:17', 39.9203, -75.174, 1),
(2, 'juanitohf@gmail.com', 0, '2014-11-20', '21:00:40', 39.9204, -75.174, 1),
(3, 'juanitohf@gmail.com', 0, '2014-11-20', '21:08:30', 39.9204, -75.174, 1),
(4, 'juanitohf@gmail.com', 0, '2014-11-20', '21:33:06', 39.9204, -75.174, 1),
(5, 'juanitohf@gmail.com', 0, '2014-11-20', '21:39:17', 39.9204, -75.174, 1),
(6, 'juanitohf@gmail.com', 0, '2014-11-20', '21:40:37', 39.9203, -75.174, 1),
(7, 'juanitohf@gmail.com', 0, '2014-11-20', '21:48:03', 39.9203, -75.174, 1),
(8, 'juanitohf@gmail.com', 0, '2014-11-20', '22:06:19', 39.9204, -75.174, 1),
(9, 'juanitohf@gmail.com', 0, '2014-11-20', '22:15:51', 39.9204, -75.174, 1),
(10, 'juanitohf@gmail.com', 0, '2014-11-20', '22:21:10', 39.9204, -75.174, 1),
(11, 'juanitohf@gmail.com', 0, '2014-11-20', '22:22:28', 39.9203, -75.174, 1),
(12, 'juanitohf@gmail.com', 0, '2014-11-20', '22:24:04', 39.9204, -75.174, 1),
(13, 'juanitohf@gmail.com', 0, '2014-11-20', '22:25:50', 39.9204, -75.174, 1),
(14, 'juanitohf@gmail.com', 0, '2014-11-20', '22:27:28', 39.9203, -75.174, 1),
(15, 'juanitohf@gmail.com', 0, '2014-11-20', '22:35:42', 39.9204, -75.174, 1),
(16, 'juanitohf@gmail.com', 0, '2014-11-20', '22:40:16', 39.9204, -75.174, 1),
(17, 'juanitohf@gmail.com', 0, '2014-11-20', '22:42:15', 39.9205, -75.1739, 1),
(18, 'juanitohf@gmail.com', 0, '2014-11-20', '22:45:42', 39.9203, -75.174, 1),
(19, 'juanitohf@gmail.com', 0, '2014-11-20', '22:47:12', 39.9204, -75.174, 1),
(20, 'juanitohf@gmail.com', 0, '2014-11-20', '22:48:42', 39.9204, -75.174, 1),
(21, 'juanitohf@gmail.com', 0, '2014-11-20', '22:51:14', 39.9203, -75.174, 1),
(22, 'juanitohf@gmail.com', 0, '2014-11-20', '22:58:30', 39.9204, -75.174, 1),
(23, 'juanitohf@gmail.com', 0, '2014-11-20', '22:59:30', 39.9204, -75.174, 1),
(24, 'juanitohf@gmail.com', 0, '2014-11-22', '08:39:09', 39.9204, -75.174, 1),
(25, 'juanitohf@gmail.com', 0, '2014-11-22', '08:39:43', 39.9204, -75.174, 1);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
