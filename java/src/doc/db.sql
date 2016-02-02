drop table if exists sous_question;
--#
drop table if exists question;
--#
drop table if exists categorie;
--#
drop table if exists historique;
--#
drop table if exists reponse;
--#
drop table if exists temps;
--#
drop table if exists utilisateur;
--#
create table categorie
(
   CATEGORIE            varchar(30) not null,
   primary key (CATEGORIE)
);
--#
create table historique
(
   PSEUDO               varchar(30) not null,
   SCORE                INT UNSIGNED not null,
   DATE_HISTORIQUE 		timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
   ID_HISTORIQUE        int not null AUTO_INCREMENT,
   primary key (ID_HISTORIQUE)
) AUTO_INCREMENT=0;
--#
create table question
(
   UN                   varchar(30) not null,
   AUTRE                varchar(30) not null,
   CATEGORIE            varchar(30) not null,
   primary key (UN, AUTRE)
);
--#
create table reponse
(
   REPONSE              int not null,
   primary key (REPONSE)
);
--#
create table sous_question
(
   SOUS_QUESTION        varchar(80) not null,
   REPONSE              int not null,
   UN                   varchar(30) not null,
   AUTRE                varchar(30) not null,
   primary key (SOUS_QUESTION, UN, AUTRE)
);
--#
create table temps
(
   TEMPS                int not null,
   primary key (TEMPS)
);
--#
INSERT INTO temps (`TEMPS`) VALUES
(10);
--#
create table utilisateur
(
   PSEUDO               varchar(30) not null,
   MOT_DE_PASSE         varchar(500) not null,
   primary key (PSEUDO)
);
--#
INSERT INTO reponse (`REPONSE`) VALUES
(0),
(1),
(2);
--#
alter table historique add constraint FK_REFERENCE_8 foreign key (PSEUDO)
      references utilisateur (PSEUDO) on delete cascade on update restrict;
--#
alter table question add constraint FK_REFERENCE_6 foreign key (CATEGORIE)
      references categorie (CATEGORIE) on delete cascade on update cascade;
--#
alter table sous_question add constraint FK_REFERENCE_4 foreign key (UN, AUTRE)
      references question (UN, AUTRE) on delete cascade on update cascade;
--#
alter table sous_question add constraint FK_REFERENCE_5 foreign key (REPONSE)
      references reponse (REPONSE) on delete restrict on update restrict;
