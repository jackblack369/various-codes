<?php
$serveur = "localhost";
$user= "root";
$passwd = "";
$bdd = "ouvrage";
$port='3306';

try
{

$cnx = new PDO('mysql:host=localhost;port=3306;dbname=ouvrage', $user, $passwd);
$cnx->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION); 

$cnx->beginTransaction();
$sql="INSERT INTO exemple (`nom`, `prenom`) VALUES ('Villeneuve', 'Christophe')";
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
