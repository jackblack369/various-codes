<?php
$nom_fichier="exemple1.xml";
if (file_exists($nom_fichier)) 
{
	$_xml = simplexml_load_file($nom_fichier);
	
	foreach($_xml->ligne as $ligne) 
	{
	    echo 'Titre :<b> ' ,utf8_decode($ligne->titre).'</b><br> ';
	    echo 'Soustitre :<b> ' ,utf8_decode($ligne->soustitre).'</b> ';
	}
} 
else 
{
    die('Echec lors de l\'ouverture du fichier $nom_fichier.');
}

?>