<?php
$serveur = "localhost";
$user= "root";
$passwd = "";
$bdd = "ouvrage";

$connex = mysqli_connect($serveur, $user, $passwd, $bdd);

if (mysqli_connect_errno()) 
    die ("Échec de la connexion : ". mysqli_connect_error());


$sql="SELECT * FROM exemple";
$req=mysqli_query($connex,$sql);

echo "<table border=1>";
echo "<tr>";
echo "<td>N°</td>";
echo "<td>Nom</td>";
echo "<td>Prenom</td>";
echo "</tr>";

while ($row=mysqli_fetch_assoc($req))
{
	echo "<tr>";
	echo "<td>".$row['id']."</td>";
	echo "<td>".stripslashes($row['nom'])."</td>";
	echo "<td>".stripslashes($row['prenom'])."</td>";
	echo "</tr>";
}
echo "</table>";


mysqli_free_result($req);
mysqli_close($connex);
?> 


