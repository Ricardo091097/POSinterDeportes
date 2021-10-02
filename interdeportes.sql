-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 14, 2021 at 03:42 AM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.4.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `interdeportes`
--

-- --------------------------------------------------------

--
-- Table structure for table `detalles_venta`
--

CREATE TABLE `detalles_venta` (
  `NoVenta` int(100) NOT NULL,
  `id_producto` int(100) NOT NULL,
  `precio_unitario` float NOT NULL,
  `cantidad` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `detalles_venta`
--

INSERT INTO `detalles_venta` (`NoVenta`, `id_producto`, `precio_unitario`, `cantidad`) VALUES
(7, 1, 1899, 2),
(7, 3, 2099, 1),
(7, 5, 1999, 1),
(23, 3, 2099, 2),
(23, 5, 1999, 2),
(24, 3, 2099, 2),
(25, 1, 1899, 2),
(25, 5, 1999, 2);

-- --------------------------------------------------------

--
-- Table structure for table `empleados`
--

CREATE TABLE `empleados` (
  `ID` int(20) NOT NULL,
  `Nombre` varchar(50) NOT NULL,
  `Nacimiento` date NOT NULL,
  `Puesto` varchar(50) NOT NULL,
  `Sexo` char(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `empleados`
--

INSERT INTO `empleados` (`ID`, `Nombre`, `Nacimiento`, `Puesto`, `Sexo`) VALUES
(1, 'Ricardo', '1997-10-09', 'Administrador', 'M'),
(2, 'Sandra', '2000-05-13', 'Vendedor', 'F'),
(3, 'Jesus', '2001-01-19', 'Vendedor', 'M'),
(4, 'Oscar', '2001-01-29', 'Vendedor', 'M'),
(5, 'Giselle', '1998-08-28', 'Vendedor', 'F'),
(6, 'Jorge', '1998-06-07', 'Vendedor', 'F');

-- --------------------------------------------------------

--
-- Table structure for table `productos`
--

CREATE TABLE `productos` (
  `ID` int(100) NOT NULL,
  `Nombre` varchar(100) NOT NULL,
  `Seccion` char(1) NOT NULL,
  `Categoria` varchar(100) NOT NULL,
  `Marca` varchar(100) NOT NULL,
  `Color` varchar(100) NOT NULL,
  `Precio` float NOT NULL,
  `Cantidad` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `productos`
--

INSERT INTO `productos` (`ID`, `Nombre`, `Seccion`, `Categoria`, `Marca`, `Color`, `Precio`, `Cantidad`) VALUES
(1, 'AirForce1', 'M', 'Tenis', 'Nike', 'Blanco', 1899, 8),
(3, 'Jordan', 'F', 'Tenis', 'Nike', 'Rojo', 2099, 8),
(5, 'Real Madrid', 'M', 'Chamarras', 'Adidas', 'Blanco', 1999, 8);

-- --------------------------------------------------------

--
-- Table structure for table `provedores`
--

CREATE TABLE `provedores` (
  `ID` int(100) NOT NULL,
  `Nombre` varchar(100) NOT NULL,
  `Telefono` varchar(100) NOT NULL,
  `Correo` varchar(100) NOT NULL,
  `Nombre_contacto` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `provedores`
--

INSERT INTO `provedores` (`ID`, `Nombre`, `Telefono`, `Correo`, `Nombre_contacto`) VALUES
(1, 'Nike', '3399887717', 'contacto@nike.com', 'Diego Sandoval Molins'),
(2, 'Adidas', '3315022538', 'contacto@adidas.com', 'Eduardo Corona Fernandez'),
(3, 'Jordan', '5506260802', 'contacto@jordan.com', 'Jesus Ismael Urzua Gonzales'),
(4, 'Pirma', '3365851448', 'contacto@pirma.com', 'Jorge Palomino'),
(5, 'Under Armour', '8546852132', 'contacto@under.com', 'Jose Calderon');

-- --------------------------------------------------------

--
-- Table structure for table `usuarios`
--

CREATE TABLE `usuarios` (
  `id_usuario` varchar(50) NOT NULL,
  `contra` varchar(50) NOT NULL,
  `nivel` int(10) NOT NULL,
  `id_emp` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `usuarios`
--

INSERT INTO `usuarios` (`id_usuario`, `contra`, `nivel`, `id_emp`) VALUES
('admin', '1234', 1, 1),
('V.Jorge', '1234', 2, 6),
('V.Oscar', '1234', 2, 4);

-- --------------------------------------------------------

--
-- Table structure for table `ventas`
--

CREATE TABLE `ventas` (
  `NoVenta` int(200) NOT NULL,
  `Fecha` timestamp NOT NULL DEFAULT current_timestamp(),
  `Total` float NOT NULL,
  `id_vendedor` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `ventas`
--

INSERT INTO `ventas` (`NoVenta`, `Fecha`, `Total`, `id_vendedor`) VALUES
(7, '2021-03-25 01:38:27', 7896, 'V.Jorge'),
(23, '2021-04-14 01:13:53', 8196, 'V.Jorge'),
(24, '2021-04-14 01:15:01', 4198, 'V.Jorge'),
(25, '2021-04-14 01:19:27', 7796, 'V.Jorge');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `detalles_venta`
--
ALTER TABLE `detalles_venta`
  ADD PRIMARY KEY (`NoVenta`,`id_producto`),
  ADD KEY `id_producto_fk` (`id_producto`);

--
-- Indexes for table `empleados`
--
ALTER TABLE `empleados`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`ID`) USING BTREE;

--
-- Indexes for table `provedores`
--
ALTER TABLE `provedores`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id_usuario`),
  ADD UNIQUE KEY `UK_IDEMP` (`id_emp`);

--
-- Indexes for table `ventas`
--
ALTER TABLE `ventas`
  ADD PRIMARY KEY (`NoVenta`),
  ADD KEY `vendedor_id_fk` (`id_vendedor`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `empleados`
--
ALTER TABLE `empleados`
  MODIFY `ID` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `productos`
--
ALTER TABLE `productos`
  MODIFY `ID` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `provedores`
--
ALTER TABLE `provedores`
  MODIFY `ID` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `ventas`
--
ALTER TABLE `ventas`
  MODIFY `NoVenta` int(200) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `detalles_venta`
--
ALTER TABLE `detalles_venta`
  ADD CONSTRAINT `NoVenta_fk` FOREIGN KEY (`NoVenta`) REFERENCES `ventas` (`NoVenta`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `id_producto_fk` FOREIGN KEY (`id_producto`) REFERENCES `productos` (`ID`) ON UPDATE CASCADE;

--
-- Constraints for table `usuarios`
--
ALTER TABLE `usuarios`
  ADD CONSTRAINT `usuarios_idemp_fk` FOREIGN KEY (`id_emp`) REFERENCES `empleados` (`ID`);

--
-- Constraints for table `ventas`
--
ALTER TABLE `ventas`
  ADD CONSTRAINT `vendedor_id_fk` FOREIGN KEY (`id_vendedor`) REFERENCES `usuarios` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
