<?php
$serveur = "localhost";
$user    = "root";
$passwd  = "";
$bdd     = "ouvrage";
$nom_fichier="exemple2.xml";

$connex = mysqli_connect($serveur, $user, $passwd, $bdd);
if (mysqli_connect_errno()) 	    die ("Echec de la connexion : ". mysqli_connect_error());

$sql="select nom,prenom from exemple ";
$valeur=mysqli_query($connex,$sql);
if( ! $valeur )  echo  "Probleme dans la table exemple :  " . mysql_error();

if (mysqli_num_rows($valeur)>0)
{
	$_xml ="<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>\r\n";
	$_xml .="<personne>\r\n";
	while ($row = mysqli_fetch_object($valeur)) 
	{
		$_xml .="\t\t<ligne>";
		$_xml .="\t\t<nom>$row->nom</nom>\r\n";
		$_xml .="\t\t<prenom>$row->prenom</prenom>\r\n";
		$_xml .="</ligne>\r\n";
	}
	$_xml .="</personne>";

	file_put_contents($nom_fichier,$_xml);
	
	echo "<a href=\"exemple2.xml\">Voir le fichier Resultat.</a>";
} else {
	echo "Auncun enregistrement";
}

mysqli_close($connex);

?>