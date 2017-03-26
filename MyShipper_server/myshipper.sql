-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th3 26, 2017 lúc 07:15 CH
-- Phiên bản máy phục vụ: 10.1.21-MariaDB
-- Phiên bản PHP: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `myshipper`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `donhang`
--

CREATE TABLE `donhang` (
  `MaHH` varchar(10) NOT NULL DEFAULT '0',
  `SoTK` varchar(10) DEFAULT NULL,
  `MoTa` text,
  `VTN` text,
  `VTD` text,
  `ThoiGian` varchar(50) DEFAULT NULL,
  `TrangThai` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `donhang`
--

INSERT INTO `donhang` (`MaHH`, `SoTK`, `MoTa`, `VTN`, `VTD`, `ThoiGian`, `TrangThai`) VALUES
('MMH0001', 'LHPL0103', 'Giao mot tivi', 'Thanh Xuan', 'Nguyen Trai', '2017-03-09 00:00:00', 0),
('MMH0002', 'LHPL0103', 'Cho Hang', 'Ha Noi', 'Ha Noi', '2017-03-31 00:00:00', 0);

--
-- Bẫy `donhang`
--
DELIMITER $$
CREATE TRIGGER `tg_table2_insert` BEFORE INSERT ON `donhang` FOR EACH ROW BEGIN
	INSERT INTO table2_seq VALUES (NULL);
	SET NEW.MaHH = CONCAT('MMH', LPAD(LAST_INSERT_ID(), 4, '0'));
 END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `giaohang`
--

CREATE TABLE `giaohang` (
  `SoTK` varchar(10) NOT NULL,
  `MaHH` varchar(10) NOT NULL,
  `ThoiGian` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `khachhang`
--

CREATE TABLE `khachhang` (
  `SoTK` varchar(10) NOT NULL DEFAULT '0',
  `Username` varchar(11) DEFAULT NULL,
  `Password` varchar(30) DEFAULT NULL,
  `hoten` varchar(100) NOT NULL,
  `diachi` varchar(500) NOT NULL,
  `phone` varchar(12) DEFAULT NULL,
  `quyen` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `khachhang`
--

INSERT INTO `khachhang` (`SoTK`, `Username`, `Password`, `hoten`, `diachi`, `phone`, `quyen`) VALUES
('LHPL0102', 'Hung', '123', 'Phan Thanh Hung', 'Hoang Van Thai - Thanh Xuan - Ha Noi', '098680360', 1),
('LHPL0103', 'Dung', '123', 'Ngo Thi Dung', 'Ha Noi', '1234567890', 0),
('LHPL0104', 'Nghia', '12345', 'Tran Nguyen Nghia', 'Cu Chinh Lan', '123456', 1),
('LHPL0116', 'Hunghpt', '123', 'Phan Thanh Hung', 'Ha Noi', '0123', 1),
('LHPL0117', 'dmDun', '123', '1235', '123', '123456', 1);

--
-- Bẫy `khachhang`
--
DELIMITER $$
CREATE TRIGGER `tg_table1_insert` BEFORE INSERT ON `khachhang` FOR EACH ROW BEGIN
  INSERT INTO table1_seq VALUES (NULL);
  SET NEW.SoTK = CONCAT('LHPL', LPAD(LAST_INSERT_ID(), 4, '0'));
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `table1_seq`
--

CREATE TABLE `table1_seq` (
  `SoTK` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `table1_seq`
--

INSERT INTO `table1_seq` (`SoTK`) VALUES
(1),
(2),
(31),
(34),
(35),
(36),
(37),
(38),
(39),
(40),
(41),
(55),
(56),
(57),
(69),
(75),
(95),
(96),
(98),
(99),
(100),
(101),
(102),
(103),
(104),
(105),
(107),
(108),
(113),
(115),
(116),
(117);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `table2_seq`
--

CREATE TABLE `table2_seq` (
  `MaHH` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `table2_seq`
--

INSERT INTO `table2_seq` (`MaHH`) VALUES
(1),
(2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `vitri`
--

CREATE TABLE `vitri` (
  `SoTK` varchar(10) NOT NULL,
  `ViTri` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `donhang`
--
ALTER TABLE `donhang`
  ADD PRIMARY KEY (`MaHH`),
  ADD KEY `SoTK` (`SoTK`);

--
-- Chỉ mục cho bảng `giaohang`
--
ALTER TABLE `giaohang`
  ADD KEY `SoTK` (`SoTK`),
  ADD KEY `MaHH` (`MaHH`);

--
-- Chỉ mục cho bảng `khachhang`
--
ALTER TABLE `khachhang`
  ADD PRIMARY KEY (`SoTK`),
  ADD UNIQUE KEY `Username` (`Username`);

--
-- Chỉ mục cho bảng `table1_seq`
--
ALTER TABLE `table1_seq`
  ADD PRIMARY KEY (`SoTK`);

--
-- Chỉ mục cho bảng `table2_seq`
--
ALTER TABLE `table2_seq`
  ADD PRIMARY KEY (`MaHH`);

--
-- Chỉ mục cho bảng `vitri`
--
ALTER TABLE `vitri`
  ADD KEY `SoTK` (`SoTK`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `table1_seq`
--
ALTER TABLE `table1_seq`
  MODIFY `SoTK` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=119;
--
-- AUTO_INCREMENT cho bảng `table2_seq`
--
ALTER TABLE `table2_seq`
  MODIFY `MaHH` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `donhang`
--
ALTER TABLE `donhang`
  ADD CONSTRAINT `donhang_ibfk_1` FOREIGN KEY (`SoTK`) REFERENCES `khachhang` (`SoTK`);

--
-- Các ràng buộc cho bảng `giaohang`
--
ALTER TABLE `giaohang`
  ADD CONSTRAINT `giaohang_ibfk_1` FOREIGN KEY (`SoTK`) REFERENCES `khachhang` (`SoTK`),
  ADD CONSTRAINT `giaohang_ibfk_2` FOREIGN KEY (`MaHH`) REFERENCES `donhang` (`MaHH`);

--
-- Các ràng buộc cho bảng `vitri`
--
ALTER TABLE `vitri`
  ADD CONSTRAINT `vitri_ibfk_1` FOREIGN KEY (`SoTK`) REFERENCES `khachhang` (`SoTK`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
