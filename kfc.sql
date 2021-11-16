-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Apr 26, 2021 at 11:48 AM
-- Server version: 8.0.17
-- PHP Version: 7.3.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `kfc`
--

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` varchar(255) NOT NULL,
  `qty` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `food`
--

CREATE TABLE `food` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `img` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `food`
--

INSERT INTO `food` (`id`, `name`, `price`, `description`, `img`) VALUES
(1, 'ซิงเกอร์เบอร์เกอร์', '65', 'เบอร์เกอร์ไก่ทอดกรอบจากเนื้อสะโพกแท้ๆ รสจัดจ้าน พร้อมขนมปังงา มายองเนสและผักกาดแก้วกรุบกรอบ (ราคาต่อชิ้น)', 'Zinger_Burger.png'),
(2, 'ชุดนักเก็ตส์เบาใจ', '49', 'นักเก็ตส์ 5 ชิ้น\r\nเฟรนช์ฟรายส์ (ใหญ่) 1 กล่อง', 'nugget_bao_jai_set.png'),
(3, 'ไก่ฮอทแอนด์สไปซี่ 1 ชิ้น', '39', 'ไก่สดคัดพิเศษชุบด้วยเกล็ดขนมปัง คลุกเคล้ากับผงพริกและเครื่องเทศ นำไปทอดจนสุกเหลืองกรอบหอมกรุ่นพร้อมเสิร์ฟ', 'Hot_Spicy_Chicken.png'),
(4, 'ไก่เนื้อล้น', '259', 'ไก่ทอด 6 ชิ้น\r\nไก่วิงซ์แซ่บ 4 ชิ้น\r\nนักเก็ตส์ 5 ชิ้น', 'kai_nuer_lon.png'),
(5, 'ชุดไก่ไม่มีกระดูก', '79', 'ไก่ไม่มีกระดูก 3 ชิ้น\r\nเฟรนช์ฟรายส์ (ปกติ) 1 ชุด\r\nเป๊ปซี่รีฟิล 1 แก้ว', 'crispy_strips_set_cnc.png');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `food`
--
ALTER TABLE `food`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cart`
--
ALTER TABLE `cart`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `food`
--
ALTER TABLE `food`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
