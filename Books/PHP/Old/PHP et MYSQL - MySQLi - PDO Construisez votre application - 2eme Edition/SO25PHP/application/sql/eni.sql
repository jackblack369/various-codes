-- phpMyAdmin SQL Dump
-- version 2.9.0.3
-- http://www.phpmyadmin.net
--
-- Serveur: localhost
-- Généré le : Mardi 25 Septembre 2007 à 22:25
-- Version du serveur: 5.0.27
-- Version de PHP: 5.2.0
--
-- Base de données: `eni`
--

-- --------------------------------------------------------

--
-- Structure de la table `carnet`
--

CREATE TABLE `carnet` (
  `id` int(11) unsigned NOT NULL auto_increment,
  `carnetclef` varchar(20) NOT NULL,
  `iduser` int(11) NOT NULL default '0',
  `genre` varchar(15) NOT NULL,
  `nom` varchar(20) NOT NULL default '',
  `prenom` varchar(30) NOT NULL default '',
  `adresse1` varchar(50) NOT NULL default '',
  `adresse2` varchar(50) NOT NULL default '',
  `codepostal` int(5) NOT NULL,
  `ville` varchar(50) NOT NULL default '',
  `tel` varchar(20) NOT NULL default '',
  `portable` varchar(20) NOT NULL default '',
  `email` varchar(100) NOT NULL default '',
  `photo` varchar(100) NOT NULL default '',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Contenu de la table `carnet`
--

INSERT INTO `carnet` (`id`, `carnetclef`, `iduser`, `genre`, `nom`, `prenom`, `adresse1`, `adresse2`, `codepostal`, `ville`, `tel`, `portable`, `email`, `photo`) VALUES
(1, '2votizspyj0lqvkhyns9', 2, '-1', 'VILLENEUVE', 'Christophe', 'route de Paris', '', 75000, 'Paris', '', '', 'livre@hello-design.fr', 'christophe.jpg'),
(2, 'azgtenwpa3k1y3o123kl', 1, '-1', 'VOTRE NOM', 'Votre prenom', 'rue des champs-elysees', '', 75000, 'paris', '', '', 'email@votresite.com', 'votre prenom.jpg');

-- --------------------------------------------------------

--
-- Structure de la table `carnet_details`
--

CREATE TABLE `carnet_details` (
  `id` int(11) NOT NULL auto_increment,
  `idcarnet` int(11) NOT NULL default '0',
  `idrubrique` int(11) NOT NULL default '0',
  `observation` text NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Contenu de la table `carnet_details`
--

INSERT INTO `carnet_details` (`id`, `idcarnet`, `idrubrique`, `observation`) VALUES
(1, 1, 2, 'www.hello-design.fr'),
(2, 1, 3, 'www.editions-eni.fr'),
(3, 2, 4, 'www.hello-design.fr');

-- --------------------------------------------------------

--
-- Structure de la table `rubrique`
--

CREATE TABLE `rubrique` (
  `id` int(11) NOT NULL auto_increment,
  `iduser` int(11) NOT NULL default '0',
  `nom` varchar(100) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Contenu de la table `rubrique`
--

INSERT INTO `rubrique` (`id`, `iduser`, `nom`) VALUES
(1, 2, 'URL'),
(2, 2, 'Site internet'),
(3, 2, 'Editeur'),
(4, 1, 'URL');

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL auto_increment,
  `idclef` varchar(20) NOT NULL default '',
  `niveau` varchar(20) NOT NULL default '',
  `login` varchar(32) NOT NULL default '',
  `password` varchar(32) NOT NULL default '',
  `email` varchar(100) NOT NULL default '',
  `date_creation` date NOT NULL default '0000-00-00',
  `date_lastpass` date NOT NULL default '0000-00-00',
  `page` varchar(50) NOT NULL default '',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Contenu de la table `user`
--

INSERT INTO `user` (`id`, `idclef`, `niveau`, `login`, `password`, `email`, `date_creation`, `date_lastpass`, `page`) VALUES
(1, 'yfktur4hq70h2f8x2rcl', 'user', 'test', '098f6bcd4621d373cade4e832627b4f6', 'hello@chez.com', '2007-06-25', '2007-09-25', 'compte.php'),
(2, 'r4la7w1mzk1qrgpm3sty', 'admin', 'admin', '21232f297a57a5a743894a0e4a801fc3', 'hellosct1@gmail.com', '2007-08-01', '2007-09-23', 'compte.php');