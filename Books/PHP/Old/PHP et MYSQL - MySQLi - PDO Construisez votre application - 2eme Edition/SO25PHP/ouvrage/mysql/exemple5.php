<?php
$serveur = "localhost";
$user= "root";
$passwd = "";
$bdd = "ouvrage";

$connex = mysql_pconnect($serveur, $user, $passwd);
if (!$connex) echo ("Impossible de se connecter : " . mysql_error());

mysql_select_db($bdd,$connex);

$sql = "DELETE FROM exemple WHERE id='1' "; 
$qid = mysql_query($sql);
if(! $qid ) 
	die ('Requête invalide : ' . mysql_error());       
else
	echo "Suppression... oki";

mysql_close($connex);
?>