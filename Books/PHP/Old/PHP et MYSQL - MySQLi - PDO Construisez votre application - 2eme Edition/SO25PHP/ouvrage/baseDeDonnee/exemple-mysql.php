<?php
$serveur="localhost";
$user="root";
$password="";
$bdd="ouvrage";

$connex = mysql_connect($serveur, $user, $password, $bdd);
if (mysql_errno()) 	    die ("Echec de la connexion : ". mysql_error());

mysql_select_db($bdd,$connex);
?>
