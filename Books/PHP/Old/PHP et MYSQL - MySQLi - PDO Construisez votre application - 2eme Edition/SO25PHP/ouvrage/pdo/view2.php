<?php

try
{
$cnx = new PDO('mysql:host=localhost;port=3306;dbname=ouvrage', 'root', '');
$cnx->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION); // Obligatoire pour la suite

$sql="SELECT * from exemple ";
$qid=$cnx->prepare($sql);
$qid->execute();
$row=$qid->fetchAll(PDO::FETCH_ASSOC);

echo '<pre>';
print_r($row);
echo '</pre>';

$qid->closeCursor();

$cnx = null;
}


catch (PDOException $e) 
{	

echo 'Erreur : '.$e->getMessage().'<br />';
echo 'N° : '.$e->getCode().'<br />';

die();
}
?>
