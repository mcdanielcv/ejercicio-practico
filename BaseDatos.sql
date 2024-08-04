CREATE DATABASE IF NOT EXISTS microservicios_db;

use microservicios_db;

CREATE TABLE IF NOT EXISTS cliente (
  `clienteid` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `genero` varchar(255) DEFAULT NULL,
  `identificacion` varchar(255) DEFAULT NULL,
  `edad` int DEFAULT NULL,
  `direccion` varchar(255) NOT NULL,
  `telefono` varchar(255) NOT NULL,
  `contrasena` varchar(255) NOT NULL,
  `estado` tinyint(1) NOT NULL,
  PRIMARY KEY (`clienteid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS cuenta  (
  `numero_cuenta` bigint NOT NULL,
  `clienteid` bigint NOT NULL,
  `estado` tinyint(1) NOT NULL,
  `saldo_inicial` double NOT NULL,
  `saldo_disponible` double NOT NULL,
  `tipo_cuenta` varchar(255) NOT NULL,
  PRIMARY KEY (`numero_cuenta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS movimiento  (
  `numero_cuenta` bigint NOT NULL,
  `tipo_movimiento` varchar(255) NOT NULL,
  `fecha` datetime(6) NOT NULL,
  `valor` double NOT NULL,
  `saldo` double NOT NULL,
  PRIMARY KEY (`fecha`,`numero_cuenta`,`tipo_movimiento`,`valor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


---insert
INSERT INTO `cliente` (`clienteid`,`nombre`,`genero`,`identificacion`,`edad`,`direccion`,`telefono`,`contrasena`,`estado`) VALUES (1,'Jose Lema',NULL,NULL,0,'Otavalo sn y principal ','098254785','1234',1);
INSERT INTO `cliente` (`clienteid`,`nombre`,`genero`,`identificacion`,`edad`,`direccion`,`telefono`,`contrasena`,`estado`) VALUES (2,'Marianela Montalvo',NULL,NULL,0,'Amazonas y  NNUU','097548965','5678 ',1);
INSERT INTO `cliente` (`clienteid`,`nombre`,`genero`,`identificacion`,`edad`,`direccion`,`telefono`,`contrasena`,`estado`) VALUES (3,'Juan Osorio',NULL,NULL,0,'13 junio y Equinoccial','098874587','1245',1);

INSERT INTO `cuenta` (`numero_cuenta`,`clienteid`,`estado`,`saldo_inicial`,`tipo_cuenta`,`saldo_disponible` ) VALUES (225487,2,'1',700,'Corriente',700);
INSERT INTO `cuenta` (`numero_cuenta`,`clienteid`,`estado`,`saldo_inicial`,`tipo_cuenta`,`saldo_disponible`) VALUES (478758,1,'1',1425,'Ahorro',1425);
INSERT INTO `cuenta` (`numero_cuenta`,`clienteid`,`estado`,`saldo_inicial`,`tipo_cuenta`,`saldo_disponible`) VALUES (495878,3,'1',150,'Ahorro',150);
INSERT INTO `cuenta` (`numero_cuenta`,`clienteid`,`estado`,`saldo_inicial`,`tipo_cuenta`,`saldo_disponible`) VALUES (496825,2,'1',0,'Ahorro',0);
INSERT INTO `cuenta` (`numero_cuenta`,`clienteid`,`estado`,`saldo_inicial`,`tipo_cuenta`,`saldo_disponible`) VALUES (585545,1,'1',1000,'Corriente',1000);

INSERT INTO `movimiento` (`numero_cuenta`,`tipo_movimiento`,`fecha`,`valor`,`saldo`) VALUES (496825,'Retiro','2022-02-08 00:00:00.000000',-540,0);
INSERT INTO `movimiento` (`numero_cuenta`,`tipo_movimiento`,`fecha`,`valor`,`saldo`) VALUES (478758,'Retiro','2022-02-10 00:00:00.000000',-575,1425);
INSERT INTO `movimiento` (`numero_cuenta`,`tipo_movimiento`,`fecha`,`valor`,`saldo`) VALUES (225487,'Deposito','2022-02-11 00:00:00.000000',600,700);
INSERT INTO `movimiento` (`numero_cuenta`,`tipo_movimiento`,`fecha`,`valor`,`saldo`) VALUES (495878,'Deposito','2022-02-12 00:00:00.000000',150,150);