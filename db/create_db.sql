CREATE DATABASE IF NOT EXISTS Delegacia;

USE Delegacia;

CREATE TABLE `criminoso` (
	`id` int NOT NULL AUTO_INCREMENT,
	`nome` char(50) NOT NULL UNIQUE,
	`idade` tinyint NOT NULL,
	`sexo` char(1) NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `vitima` (
	`id` int NOT NULL AUTO_INCREMENT,
	`nome` char(50) NOT NULL UNIQUE,
	`idade` tinyint NOT NULL,
	`sexo` char(1) NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `arma` (
	`id` int NOT NULL AUTO_INCREMENT,
	`crime_id` int NOT NULL,
	`nome` char(50) NOT NULL UNIQUE,
	PRIMARY KEY (`id`)
);

CREATE TABLE `crime` (
	`id` int NOT NULL AUTO_INCREMENT,
	`data` DATE NOT NULL,
	`descricao` TEXT,
	`horario` TIME NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `crimes_criminosos` (
	`crime_id` int NOT NULL,
	`criminoso_id` int NOT NULL,
	PRIMARY KEY (`crime_id`,`criminoso_id`)
);

CREATE TABLE `crimes_vitimas` (
	`crime_id` int NOT NULL,
	`vitima_id` int NOT NULL,
	PRIMARY KEY (`crime_id`,`vitima_id`)
);

CREATE TABLE `criminosos_vitimas` (
	`criminoso_id` int NOT NULL,
	`vitima_id` int NOT NULL,
	PRIMARY KEY (`criminoso_id`,`vitima_id`)
);

ALTER TABLE `arma` ADD CONSTRAINT `arma_fk0` FOREIGN KEY (`crime_id`) REFERENCES `crime`(`id`);

ALTER TABLE `crimes_criminosos` ADD CONSTRAINT `crimes_criminosos_fk0` FOREIGN KEY (`crime_id`) REFERENCES `crime`(`id`);

ALTER TABLE `crimes_criminosos` ADD CONSTRAINT `crimes_criminosos_fk1` FOREIGN KEY (`criminoso_id`) REFERENCES `criminoso`(`id`);

ALTER TABLE `crimes_vitimas` ADD CONSTRAINT `crimes_vitimas_fk0` FOREIGN KEY (`crime_id`) REFERENCES `crime`(`id`);

ALTER TABLE `crimes_vitimas` ADD CONSTRAINT `crimes_vitimas_fk1` FOREIGN KEY (`vitima_id`) REFERENCES `vitima`(`id`);

ALTER TABLE `criminosos_vitimas` ADD CONSTRAINT `criminosos_vitimas_fk0` FOREIGN KEY (`criminoso_id`) REFERENCES `criminoso`(`id`);

ALTER TABLE `criminosos_vitimas` ADD CONSTRAINT `criminosos_vitimas_fk1` FOREIGN KEY (`vitima_id`) REFERENCES `vitima`(`id`);








