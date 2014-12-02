<?php
$serveur = "localhost";
$user= "root";
$passwd = "";
$bdd = "ouvrage";

$connex = mysql_pconnect($serveur, $user, $passwd);
if (!$connex) die ("Impossible de se connecter : " . mysql_error());
mysql_select_db($bdd,$connex);

$sql="select * from exemple ";
$valeur=mysql_query($sql);
if( ! $valeur )  echo  "Probleme dans la table exemple :  " . mysql_error();

echo "<table border=1>";
echo "<tr>";
echo "<td>N°</td>";
echo "<td>Nom</td>";
echo "<td>Prenom</td>";
echo "</tr>";

while( $list=mysql_fetch_object( $valeur) )     
{
	echo "<tr>";
	echo "<td>$list->id</td>";
	echo "<td>".stripslashes($list->nom)."</td>";
	echo "<td>".stripslashes($list->prenom)."</td>";
	echo "</tr>";
}
echo "</table>";

mysql_free_result($valeur);
mysql_close($connex);

?>
	