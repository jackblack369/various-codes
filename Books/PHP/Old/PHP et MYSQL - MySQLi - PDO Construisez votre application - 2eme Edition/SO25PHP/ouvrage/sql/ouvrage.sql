-- phpMyAdmin SQL Dump
-- version 2.11.2.1
-- http://www.phpmyadmin.net
--
-- Serveur: localhost
-- Généré le : Jeu 17 Janvier 2008 à 04:56
-- Version du serveur: 5.0.45
-- Version de PHP: 5.2.5

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Base de données: `ouvrage`
--

-- --------------------------------------------------------

--
-- Structure de la table `actualite`
--

CREATE TABLE `actualite` (
  `id` tinyint(5) NOT NULL auto_increment,
  `titre` varchar(100) NOT NULL,
  `lien` varchar(255) NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Contenu de la table `actualite`
--

INSERT INTO `actualite` (`id`, `titre`, `lien`, `description`) VALUES
(1, 'Editions ENI', 'http://www.editions-eni.com/', 'Le site des Editions ENI'),
(2, 'Hello-Design', 'http://www.hello-design.fr', 'Auteur du livre que vous avez dans les mains'),
(3, 'Actualite PHP et MySQL francaise', 'http://www.nexen.net', 'Soyez informe des actualites francaises venant du PHP'),
(4, 'Portail de la communaute francophone', 'http://www.phpteam.net/', 'Site permettant de progresser en PHP sous la forme de Tutoriaux'),
(5, 'Le site incontournable', 'http://www.elroubio.net/', 'Venez decouvrir le site du pere de elephant');

-- --------------------------------------------------------

--
-- Structure de la table `exemple`
--

CREATE TABLE `exemple` (
  `id` int(5) NOT NULL auto_increment,
  `nom` varchar(20) NOT NULL,
  `prenom` varchar(20) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Contenu de la table `exemple`
--

INSERT INTO `exemple` (`id`, `nom`, `prenom`) VALUES
(1, 'Langage', 'PHP'),
(2, 'ENI', 'ENI');
