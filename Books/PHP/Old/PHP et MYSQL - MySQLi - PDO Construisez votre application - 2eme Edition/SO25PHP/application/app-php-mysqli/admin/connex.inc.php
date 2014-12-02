<?php
$serveur = "localhost";
$user    = "root";
$passwd  = "";
$bdd     = "eni";


	$connex = mysqli_connect($serveur, $user, $passwd, $bdd);
	if (mysqli_connect_errno()) 	    die ("Echec de la connexion : ". mysqli_connect_error());

?>