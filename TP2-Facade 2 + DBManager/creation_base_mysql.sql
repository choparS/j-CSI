CREATE DATABASE `java` ;
CREATE TABLE `personne` (
`id` INT NOT NULL AUTO_INCREMENT ,
`nom` VARCHAR( 50 ) NOT NULL ,
PRIMARY KEY ( `id` )
);
INSERT INTO `personne` ( `id` , `nom` )
VALUES (
'', 'nicolas'
);
INSERT INTO `personne` ( `id` , `nom` )
VALUES (
'', 'isabelle'
);
INSERT INTO `personne` ( `id` , `nom` )
VALUES (
'', 'marie'
);
