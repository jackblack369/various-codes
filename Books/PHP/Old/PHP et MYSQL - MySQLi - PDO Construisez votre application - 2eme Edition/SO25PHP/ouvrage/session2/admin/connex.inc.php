<?php
$serveur = "localhost";
$user    = "root";
$passwd  = "";
$bdd     = "eni";

$connex = mysqli_connect($serveur, $user, $passwd, $bdd);
if (mysqli_connect_errno()) 	    
{
echo ("Echec de la connexion : ". mysqli_connect_error());
die ("Code d'erreur : ".mysqli_connect_errno());
}

?>