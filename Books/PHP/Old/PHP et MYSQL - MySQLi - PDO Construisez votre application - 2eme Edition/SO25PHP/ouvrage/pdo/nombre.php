<?php

try
{
$cnx = new PDO('mysql:host=localhost;port=3306;dbname=ouvrage', 'root', '');
$cnx->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION); // Obligatoire pour la suite

$sql="SELECT COUNT(*) from exemple ";
$compte = $cnx->prepare($sql);
$compte->execute();

$resultat = $compte->fetchColumn();
echo  $resultat."<br>";

$compte->closeCursor();
$cnx = null;
}


catch (PDOException $e) 
{	
echo 'N° : '.$e->getCode().'<br />';
die ('Erreur : '.$e->getMessage().'<br />');
}
?>
