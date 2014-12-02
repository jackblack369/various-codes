<?php
$serveur = "localhost";
$user= "root";
$passwd = "";
$bdd = "ouvrage";


	$connex = mysqli_connect($serveur, $user, $passwd, $bdd);
	
	if (mysqli_connect_errno()) 
	    die ("Echec de la connexion : ". mysqli_connect_error());
	
$sql = "UPDATE exemple SET 
	nom='Langage'
	,prenom='PHP'
	WHERE id='2'
	";

    $result = mysqli_query($connex, $sql);  

	if (!$result)  die ("Probleme :  " . mysqli_error($connex));
	else
		 	echo "Mise a jour...oki";
		
mysqli_close($connex);
?>
