<?php 
$serveur='localhost';
$login= 'root';
$password= '';
$bdd='ouvrage';

require_once ('maclasse.php');

try
{

$CnxBDD = new CnxBDD($serveur,$login,$password,$bdd);
$CnxBDD->connect(); 

$row=$CnxBDD->requete("SELECT * FROM actualite"); 

echo" <pre>";
print_r($row);
echo "</pre>";

$CnxBDD->deconnect(); 
unset ($CnxBDD);

}
catch (MySQLExeption $e)
{
die ('Erreur : '.$e->getMessage().'<br />');
}
?>


	