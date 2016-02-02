--
-- Structure de la table `categorie`
--

CREATE TABLE IF NOT EXISTS `categorie` (
  `CATEGORIE` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `categorie`
--

INSERT INTO `categorie` (`CATEGORIE`) VALUES
('Cinéma'),
('ISEN'),
('Jeu Vidéo'),
('Politique'),
('Série TV');

--
-- Structure de la table `historique`
--

CREATE TABLE IF NOT EXISTS `historique` (
  `PSEUDO` varchar(30) NOT NULL,
  `SCORE` int(10) unsigned NOT NULL,
  `DATE_HISTORIQUE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`ID_HISTORIQUE` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;

--
-- Contenu de la table `historique`
--

INSERT INTO `historique` (`PSEUDO`, `SCORE`, `DATE_HISTORIQUE`, `ID_HISTORIQUE`) VALUES
('admin', 94, '2015-06-09 12:20:51', 3),
('admin', 82, '2015-06-09 12:27:17', 4),
('admin', 20, '2015-06-09 12:28:27', 5),
('admin', 100, '2015-06-09 12:29:23', 6),
('David', 97, '2015-06-10 11:13:27', 7),
('David', 66, '2015-06-10 11:13:27', 8),
('David', 17, '2015-06-10 11:13:51', 9),
('David', 51, '2015-06-10 11:13:51', 10),
('admin', 87, '2015-06-10 11:14:13', 11),
('admin', 21, '2015-06-10 11:14:13', 12),
('evan', 50, '2015-06-10 11:14:45', 13),
('evan', 0, '2015-06-10 11:14:45', 14),
('evan', 94, '2015-06-10 11:15:04', 15),
('evan', 29, '2015-06-10 11:15:04', 16),
('konstantin', 90, '2015-06-10 11:15:25', 17),
('konstantin', 35, '2015-06-10 11:15:25', 18),
('konstantin', 63, '2015-06-10 11:15:45', 19),
('konstantin', 78, '2015-06-10 11:15:45', 20),
('lamasticot', 40, '2015-06-10 11:16:05', 21),
('David', 58, '2015-06-10 11:16:05', 22);

--
-- Structure de la table `question`
--

CREATE TABLE IF NOT EXISTS `question` (
  `UN` varchar(30) NOT NULL,
  `AUTRE` varchar(30) NOT NULL,
  `CATEGORIE` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `question`
--

INSERT INTO `question` (`UN`, `AUTRE`, `CATEGORIE`) VALUES
('Frankenstein', 'Franck Leboeuf', 'Cinéma'),
('Laetitia Casta', 'Un canard en plastique', 'Cinéma'),
('Pamela Anderson', 'une voiture d''occasion', 'Cinéma'),
('Sisi', 'Oui-oui', 'Cinéma'),
('Tony Stark', 'Ned Stark', 'Cinéma'),
('l''ISEN', 'Epitech', 'isen'),
('l''ISEN', 'Nesi', 'ISEN'),
('Yann Le Ru', 'Le Ru', 'ISEN'),
('Lara Croft', 'Caylen Croft', 'Jeu Vidéo'),
('Mario Bros', 'une brosse', 'Jeu Vidéo'),
('Sonic', 'un hérisson', 'Jeu Vidéo'),
('Bernard Tapie', 'un tapis', 'Politique'),
('Manuel Valls', 'une valse', 'Politique'),
('Nicolas Sarkozy', 'un clou', 'politique'),
('Clark Kent', 'Ken', 'Série TV'),
('Stargate', 'Star Wars', 'Série TV');

--
-- Structure de la table `reponse`
--

CREATE TABLE IF NOT EXISTS `reponse` (
  `REPONSE` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `reponse`
--

INSERT INTO `reponse` (`REPONSE`) VALUES
(0),
(1),
(2);

--
-- Structure de la table `sous_question`
--

CREATE TABLE IF NOT EXISTS `sous_question` (
  `SOUS_QUESTION` varchar(80) NOT NULL,
  `REPONSE` int(11) NOT NULL,
  `UN` varchar(30) NOT NULL,
  `AUTRE` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `sous_question`
--

INSERT INTO `sous_question` (`SOUS_QUESTION`, `REPONSE`, `UN`, `AUTRE`) VALUES
('A été inventé par Nintendo', 0, 'Mario Bros', 'une brosse'),
('A été ministre de la ville', 0, 'Bernard Tapie', 'un tapis'),
('A une durée de vie infinie', 0, 'Stargate', 'Star Wars'),
('A une fermeture éclair sur la gueule', 0, 'Frankenstein', 'Franck Leboeuf'),
('A vraiment existé', 0, 'Sisi', 'Oui-oui'),
('Délivre un diplôme d''ingénieur', 0, 'l''ISEN', 'Epitech'),
('Elle est blonde', 0, 'Pamela Anderson', 'une voiture d''occasion'),
('Est bleu', 0, 'Sonic', 'un hérisson'),
('Est d''origine espagnole', 0, 'Manuel Valls', 'une valse'),
('Est milliardaire', 0, 'Tony Stark', 'Ned Stark'),
('Est premier ministre', 0, 'Manuel Valls', 'une valse'),
('Est très rapide', 0, 'Sonic', 'un hérisson'),
('Est un Avengers', 0, 'Tony Stark', 'Ned Stark'),
('Est un classique', 0, 'Mario Bros', 'une brosse'),
('Est un extraterrestre', 0, 'Clark Kent', 'Ken'),
('Est un personnage fictif', 0, 'Lara Croft', 'Caylen Croft'),
('Est un personnage fictif', 0, 'Sonic', 'un hérisson'),
('Est une école réputée', 0, 'l''ISEN', 'Nesi'),
('Existe depuis les années 50', 0, 'Nicolas Sarkozy', 'un clou'),
('Il a été maire de Neuilly', 0, 'Nicolas Sarkozy', 'un clou'),
('Matricule 265-449G', 0, 'Bernard Tapie', 'un tapis'),
('N''aime pas la Kryptonite', 0, 'Clark Kent', 'Ken'),
('Sait faire de la bicyclette', 0, 'Laetitia Casta', 'Un canard en plastique'),
('Surnommée Zouzou', 0, 'Laetitia Casta', 'Un canard en plastique'),
('A été découvert en 1975', 1, 'l''ISEN', 'Nesi'),
('A joué dans Batman 2', 1, 'Laetitia Casta', 'Un canard en plastique'),
('A le crâne rasé', 1, 'Frankenstein', 'Franck Leboeuf'),
('A mauvaise réputation', 1, 'l''ISEN', 'Epitech'),
('A une époque, j''ai vomi dessus', 1, 'Bernard Tapie', 'un tapis'),
('S''est fait décapité', 1, 'Tony Stark', 'Ned Stark'),
('Elle est cotée à l''Argus', 1, 'Pamela Anderson', 'une voiture d''occasion'),
('Est né en 1780', 1, 'Manuel Valls', 'une valse'),
('Est un monstre', 1, 'l''ISEN', 'Nesi'),
('Est une adaptation d''un livre', 1, 'Stargate', 'Star Wars'),
('Fait du catch', 1, 'Lara Croft', 'Caylen Croft'),
('Il a une tête plate', 1, 'Nicolas Sarkozy', 'un clou'),
('Il en existe sans tête', 1, 'Nicolas Sarkozy', 'un clou'),
('Joue au foot', 1, 'Frankenstein', 'Franck Leboeuf'),
('Les femmes le maîtrise à la perfection', 1, 'Mario Bros', 'une brosse'),
('Mange des insectes', 1, 'Sonic', 'un hérisson'),
('On s''y bat avec des sabres laser', 1, 'Stargate', 'Star Wars'),
('S''entretient de façon journalière avec un aspirateur', 1, 'Bernard Tapie', 'un tapis'),
('Sort avec Barbie', 1, 'Clark Kent', 'Ken'),
('Vous fait perdre à ni oui ni non', 1, 'Sisi', 'Oui-oui'),
('A déjà fait du cinéma', 2, 'Frankenstein', 'Franck Leboeuf'),
('Se bat pour le bien', 2, 'Tony Stark', 'Ned Stark'),
('Contient des bouts de caoutchouc', 2, 'Laetitia Casta', 'Un canard en plastique'),
('Elle a des kilomètres au compteur', 2, 'Pamela Anderson', 'une voiture d''occasion'),
('Elle n''a pas que des pièces d''origine', 2, 'Pamela Anderson', 'une voiture d''occasion'),
('Elle ne coûte pas cher', 2, 'Pamela Anderson', 'une voiture d''occasion'),
('Est sexy', 2, 'Lara Croft', 'Caylen Croft'),
('Est un classique', 2, 'Stargate', 'Star Wars'),
('Est un personnage fictif', 2, 'Clark Kent', 'Ken'),
('Il peut voler', 2, 'Bernard Tapie', 'un tapis'),
('Il vaut mieux éviter de marcher dessus', 2, 'Nicolas Sarkozy', 'un clou'),
('On peut le/la laquer', 2, 'Laetitia Casta', 'Un canard en plastique'),
('Porte un chapeau', 2, 'Sisi', 'Oui-oui'),
('Posséde une filiére informatique', 2, 'l''ISEN', 'Epitech'),
('Vit dans un monde merveilleux', 2, 'Sisi', 'Oui-oui');

--
-- Structure de la table `temps`
--

CREATE TABLE IF NOT EXISTS `temps` (
  `TEMPS` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `temps`
--

INSERT INTO `temps` (`TEMPS`) VALUES
(10);

--
-- Structure de la table `utilisateur`
--

CREATE TABLE IF NOT EXISTS `utilisateur` (
  `PSEUDO` varchar(30) NOT NULL,
  `MOT_DE_PASSE` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `utilisateur`
--

INSERT INTO `utilisateur` (`PSEUDO`, `MOT_DE_PASSE`) VALUES
('admin', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec'),
('David', 'ee26b0dd4af7e749aa1a8ee3c10ae9923f618980772e473f8819a5d4940e0db27ac185f8a0e1d5f84f88bc887fd67b143732c304cc5fa9ad8e6f57f50028a8ff'),
('evan', 'ee26b0dd4af7e749aa1a8ee3c10ae9923f618980772e473f8819a5d4940e0db27ac185f8a0e1d5f84f88bc887fd67b143732c304cc5fa9ad8e6f57f50028a8ff'),
('konstantin', 'ee26b0dd4af7e749aa1a8ee3c10ae9923f618980772e473f8819a5d4940e0db27ac185f8a0e1d5f84f88bc887fd67b143732c304cc5fa9ad8e6f57f50028a8ff'),
('lamasticot', '586a5267a29b455aecee536de6c3dfa6118d6495b873894341c173571b50ec700fc9b221e9aecbc26daaebb0abcd04e8c5336fb98b42eef52d0ee15818711b60');

--
-- Index pour les tables exportées
--

--
-- Index pour la table `categorie`
--
ALTER TABLE `categorie`
 ADD PRIMARY KEY (`CATEGORIE`);

--
-- Index pour la table `historique`
--
ALTER TABLE `historique`
 ADD PRIMARY KEY (`ID_HISTORIQUE`), ADD KEY `FK_REFERENCE_8` (`PSEUDO`);

--
-- Index pour la table `question`
--
ALTER TABLE `question`
 ADD PRIMARY KEY (`UN`,`AUTRE`), ADD KEY `FK_REFERENCE_6` (`CATEGORIE`);

--
-- Index pour la table `reponse`
--
ALTER TABLE `reponse`
 ADD PRIMARY KEY (`REPONSE`);

--
-- Index pour la table `sous_question`
--
ALTER TABLE `sous_question`
 ADD PRIMARY KEY (`SOUS_QUESTION`,`UN`,`AUTRE`), ADD KEY `FK_REFERENCE_4` (`UN`,`AUTRE`), ADD KEY `FK_REFERENCE_5` (`REPONSE`);

--
-- Index pour la table `temps`
--
ALTER TABLE `temps`
 ADD PRIMARY KEY (`TEMPS`);

--
-- Index pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
 ADD PRIMARY KEY (`PSEUDO`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `historique`
--
ALTER TABLE `historique`
MODIFY `ID_HISTORIQUE` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=23;
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `historique`
--
ALTER TABLE `historique`
ADD CONSTRAINT `FK_REFERENCE_8` FOREIGN KEY (`PSEUDO`) REFERENCES `utilisateur` (`PSEUDO`) ON DELETE CASCADE;

--
-- Contraintes pour la table `question`
--
ALTER TABLE `question`
ADD CONSTRAINT `FK_REFERENCE_6` FOREIGN KEY (`CATEGORIE`) REFERENCES `categorie` (`CATEGORIE`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `sous_question`
--
ALTER TABLE `sous_question`
ADD CONSTRAINT `FK_REFERENCE_4` FOREIGN KEY (`UN`, `AUTRE`) REFERENCES `question` (`UN`, `AUTRE`) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT `FK_REFERENCE_5` FOREIGN KEY (`REPONSE`) REFERENCES `reponse` (`REPONSE`);
