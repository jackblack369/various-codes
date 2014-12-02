<?php
$serveur = "localhost";
$user= "root";
$passwd = "";
$bdd = "ouvrage";

$connex = mysql_pconnect($serveur, $user, $passwd);
if (!$connex) die ("Impossible de se connecter : " . mysql_error());


$sql = "INSERT INTO `exemple` (`nom`, `prenom`) VALUES ('notre nom','notre prenom')";
$qid = mysql_query($sql);
if(! $qid ) 
	die ('Requête invalide : ' . mysql_error());       
else
	echo "Insertion... oki";

mysql_close($connex);

?>
