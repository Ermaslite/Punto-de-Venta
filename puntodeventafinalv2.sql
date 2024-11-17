create database puntoventa;
use puntoventa;
-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: puntoventa
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `administrador`
--

DROP TABLE IF EXISTS `administrador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `administrador` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre_usuario` varchar(50) NOT NULL,
  `contrasena` varchar(64) NOT NULL,
  `correo` varchar(100) NOT NULL,
  `nombre_completo` varchar(100) NOT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `rol` varchar(20) NOT NULL DEFAULT 'administrador',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre_usuario` (`nombre_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrador`
--

LOCK TABLES `administrador` WRITE;
/*!40000 ALTER TABLE `administrador` DISABLE KEYS */;
/*!40000 ALTER TABLE `administrador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria` (
  `categoryID` int NOT NULL AUTO_INCREMENT,
  `NombreCat` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`categoryID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` VALUES (1,'Bebidas'),(2,'Snacks'),(3,'Lácteos'),(4,'Carnes y Embutidos'),(5,'Frutas y Verduras'),(6,'Productos de Limpieza'),(7,'Cuidado Personal'),(8,'Electrónica'),(9,'Juguetería'),(10,'Ropa y Accesorios'),(11,'Panadería'),(12,'Autos'),(13,'Mascotas'),(14,'Hogar y Cocina'),(15,'Ferretería');
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientes` (
  `ClienteID` int NOT NULL AUTO_INCREMENT,
  `NombreCliente` varchar(100) DEFAULT NULL,
  `Direccion` varchar(255) DEFAULT NULL,
  `CURP` char(18) DEFAULT NULL,
  `CodigoPostal` varchar(10) DEFAULT NULL,
  `Pais` varchar(50) DEFAULT NULL,
  `Telefono` varchar(15) DEFAULT NULL,
  `CodigoBarrasCliente` char(13) DEFAULT NULL,
  PRIMARY KEY (`ClienteID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalleventas`
--

DROP TABLE IF EXISTS `detalleventas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalleventas` (
  `DetalleID` int NOT NULL AUTO_INCREMENT,
  `VentaID` int NOT NULL,
  `IDproducto` int NOT NULL,
  `Cantidad` int NOT NULL,
  `PrecioUnitario` decimal(10,2) NOT NULL,
  PRIMARY KEY (`DetalleID`),
  KEY `VentaID` (`VentaID`),
  KEY `IDproducto` (`IDproducto`),
  CONSTRAINT `detalleventas_ibfk_1` FOREIGN KEY (`VentaID`) REFERENCES `ventas` (`VentaID`),
  CONSTRAINT `detalleventas_ibfk_2` FOREIGN KEY (`IDproducto`) REFERENCES `productos` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalleventas`
--

LOCK TABLES `detalleventas` WRITE;
/*!40000 ALTER TABLE `detalleventas` DISABLE KEYS */;
/*!40000 ALTER TABLE `detalleventas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empleados`
--

DROP TABLE IF EXISTS `empleados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empleados` (
  `empleado_id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `telefono` varchar(15) DEFAULT NULL,
  `correo_electronico` varchar(100) DEFAULT NULL,
  `salario` decimal(10,2) DEFAULT NULL,
  `rfc` varchar(13) DEFAULT NULL,
  `curp` varchar(18) DEFAULT NULL,
  `turno` varchar(50) DEFAULT NULL,
  `nombre_usuario` varchar(20) DEFAULT NULL,
  `contrasena` varchar(255) NOT NULL,
  `rol` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`empleado_id`),
  UNIQUE KEY `unique_nombre_usuario` (`nombre_usuario`),
  UNIQUE KEY `unique_curp` (`curp`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empleados`
--

LOCK TABLES `empleados` WRITE;
/*!40000 ALTER TABLE `empleados` DISABLE KEYS */;
/*!40000 ALTER TABLE `empleados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventario`
--

DROP TABLE IF EXISTS `inventario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventario` (
  `IDproducto` int NOT NULL AUTO_INCREMENT,
  `NombreProducto` varchar(255) NOT NULL,
  `FechaCaducidad` date NOT NULL,
  `Cantidad` int NOT NULL,
  `CodigoBarras` char(13) NOT NULL,
  `CategoryID` int NOT NULL,
  `Precio` decimal(10,2) NOT NULL,
  `Marca` varchar(50) DEFAULT NULL,
  `Proveedor` varchar(100) DEFAULT NULL,
  `Descripcion` text,
  `StockMinimo` int DEFAULT NULL,
  PRIMARY KEY (`IDproducto`),
  KEY `CategoryID` (`CategoryID`),
  CONSTRAINT `inventario_ibfk_1` FOREIGN KEY (`CategoryID`) REFERENCES `categoria` (`categoryID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventario`
--

LOCK TABLES `inventario` WRITE;
/*!40000 ALTER TABLE `inventario` DISABLE KEYS */;
/*!40000 ALTER TABLE `inventario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productos`
--

DROP TABLE IF EXISTS `productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productos` (
  `CodigoBarras` varchar(100) DEFAULT NULL,
  `Descripcion` text,
  `Marca` varchar(50) DEFAULT NULL,
  `Rubro` varchar(50) DEFAULT NULL,
  `Proveedor` varchar(100) DEFAULT NULL,
  `StockMinimo` int DEFAULT NULL,
  `Existencia` int DEFAULT NULL,
  `IVA` decimal(5,2) DEFAULT NULL,
  `ImagenProducto` blob,
  `ID` int NOT NULL AUTO_INCREMENT,
  `CodigoProducto` varchar(30) DEFAULT NULL,
  `precioFinal` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `unique_CodigoProducto` (`CodigoProducto`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productos`
--

LOCK TABLES `productos` WRITE;
/*!40000 ALTER TABLE `productos` DISABLE KEYS */;
/*!40000 ALTER TABLE `productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ventas`
--

DROP TABLE IF EXISTS `ventas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ventas` (
  `VentaID` int NOT NULL AUTO_INCREMENT,
  `FechaVenta` date NOT NULL,
  `empleado_id` int NOT NULL,
  `ClienteID` int DEFAULT NULL,
  `Total` decimal(10,2) NOT NULL,
  `DineroEnCaja` decimal(10,2) NOT NULL,
  `DineroVendido` decimal(10,2) NOT NULL,
  PRIMARY KEY (`VentaID`),
  KEY `empleado_id` (`empleado_id`),
  KEY `ClienteID` (`ClienteID`),
  CONSTRAINT `ventas_ibfk_1` FOREIGN KEY (`empleado_id`) REFERENCES `empleados` (`empleado_id`),
  CONSTRAINT `ventas_ibfk_2` FOREIGN KEY (`ClienteID`) REFERENCES `clientes` (`ClienteID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ventas`
--

LOCK TABLES `ventas` WRITE;
/*!40000 ALTER TABLE `ventas` DISABLE KEYS */;
/*!40000 ALTER TABLE `ventas` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-15 14:22:12
select * from productos;

