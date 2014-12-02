<?php
$serveur = "localhost";
$user= "root";
$passwd = "";
$bdd = "ouvrage";

$connex = mysqli_connect($serveur, $user, $passwd, $bdd);
	
if (mysqli_connect_errno()) 
    die ("Echec de la connexion : ". mysqli_connect_error());

$sql="select nom,prenom from exemple ";
$result = mysqli_query($connex, $sql);  

$tableau = array();
while($obj = mysqli_fetch_object($result)) 
{
	$tableau[] = $obj;
}		 	

$json= '{"personne":'.json_encode($tableau).'}';
print_r ($json);

mysqli_close($connex);
?>