<?php
$serveur = "localhost";
$user= "root";
$passwd = "";
$bdd = "ouvrage";

$connex = mysql_pconnect($serveur, $user, $passwd);
if (!$connex) echo ("Impossible de se connecter : " . mysql_error());

mysql_select_db($bdd,$connex);

$sql = "UPDATE exemple SET 
	nom='Langage'
	,prenom='PHP'
	WHERE id='1'
	";

$qid = mysql_query($sql);
if(! $qid ) 
	die ('Requête invalide : ' . mysql_error());       
else
	echo "Mise à jour... oki";

mysql_close($connex);
?>
