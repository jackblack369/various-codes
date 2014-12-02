
CREATE TABLE `exemple` (
  `id` int(5) NOT NULL auto_increment,
  `nom` varchar(20) NOT NULL,
  `prenom` varchar(20) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

INSERT INTO `exemple` (`id`, `nom`, `prenom`) VALUES (1, 'Edition', 'ENI'),
(2, 'PHP', 'MySQL'),
(3, 'villeneuve', 'christophe'),
(4, 'nom', 'prenom'),
(5, 'notre nom', 'notre prenom');
