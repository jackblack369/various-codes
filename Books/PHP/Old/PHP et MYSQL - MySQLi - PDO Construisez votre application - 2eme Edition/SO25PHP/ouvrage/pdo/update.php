<?php
$serveur = "localhost";
$user= "root";
$passwd = "";
$bdd = "ouvrage";
$port='3306';

try
{

$cnx = new PDO('mysql:host=localhost;port=3306;dbname=ouvrage', $user, $passwd);
$cnx->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION); // Obligatoire pour la suite

$cnx->beginTransaction();
$sql="UPDATE exemple SET nom='ENI' WHERE id=3";
$qid=$cnx->prepare($sql);
$qid->execute();

$cnx->commit();
$cnx = null;

}


catch (PDOException $e) 
{	
echo 'N° : '.$e->getCode().'<br />';
die ('Erreur : '.$e->getMessage().'<br />');
}
?>
