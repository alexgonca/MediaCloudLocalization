CREATE  TABLE `test`.`alexa` (
  `ranking` MEDIUMINT NOT NULL ,
  `link` VARCHAR(150) NULL ,
  PRIMARY KEY (`ranking`) );

CREATE  TABLE `test`.`alexa_language` (
  `ranking` MEDIUMINT NOT NULL ,
  `link` VARCHAR(150) NULL ,
  `language` VARCHAR(100) NULL ,
  PRIMARY KEY (`ranking`) );

CREATE  TABLE `test`.`alexa_country` (
  `ranking` MEDIUMINT NOT NULL ,
  `link` VARCHAR(150) NULL ,
  `language` VARCHAR(100) NULL ,
  `country` VARCHAR(100) NULL ,
  `html` TEXT NULL 
  PRIMARY KEY (`ranking`) );

LOAD DATA INFILE '/tmp/top-1m-2014-Apr-9.csv'
INTO TABLE alexa
  FIELDS TERMINATED BY ','
  LINES TERMINATED BY '\n';

