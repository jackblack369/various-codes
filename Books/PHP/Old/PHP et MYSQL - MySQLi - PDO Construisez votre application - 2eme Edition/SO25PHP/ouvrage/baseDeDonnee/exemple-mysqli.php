<?php
$serveur="localhost";
$user="root";
$password="";
$bdd="ouvrage";

$connex = mysqli_connect($serveur, $user, $password, $bdd);
if (mysqli_connect_errno()) 	    
{
echo ("Echec de la connexion : ". mysqli_connect_error());
die ("Code d'erreur : ".mysqli_connect_errno());
}
?>
