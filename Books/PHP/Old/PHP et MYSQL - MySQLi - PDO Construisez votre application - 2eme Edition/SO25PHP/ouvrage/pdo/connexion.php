<?php
$serveur = "localhost";
$user= "root";
$passwd = "";
$bdd = "ouvrage";
$port='3306';

try 
{
   $cnx = new PDO('mysql:host='.$serveur.';port='.$port.';dbname='.$bdd, $user, $passwd);
	$cnx->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION); // Obligatoire pour la suite
}

catch (PDOException $error) 
{	
echo 'N° : '.$error->getCode().'<br />';
die ('Erreur : '.$error->getMessage().'<br />');
}
?>
