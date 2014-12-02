<?php

try
{
$cnx = new PDO('mysql:host=localhost;port=3306;dbname=ouvrage', 'root', '');
$cnx->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION); 


$sql="SELECT nom,prenom from exemple ";
$qid=$cnx->prepare($sql);
$qid->execute();

while($row=$qid->fetch(PDO::FETCH_OBJ))
{
	echo $row->nom.' '.$row->prenom.'<br />';
}
$qid->closeCursor();

$cnx = null;
}


catch (PDOException $e) 
{	
echo 'N° : '.$e->getCode().'<br />';
die ('Erreur : '.$e->getMessage().'<br />');
}
?>
