<?php $menu="admin"; 
require_once "include/config.inc.php";
if ($_SESSION['niveau']!="admin")	header("Location:index.php");
?>
<?php require_once "head.inc.php"; ?>

<?php
$cnx = new ESPACEdeNOM\cnxBDD($serveur,$user,$passwd,$bdd);
$cnx->connect(); 

$sql="select * from user";
$res=$cnx->requeteSelect($sql); 

echo "<table border=1 width=100%>";
echo "<tr>";
echo "<td>Login</td>";
echo "<td>Niveau</td>";
echo "<td>Email</td>";
echo "<td>Date creation</td>";
echo "<td>dernier passage</td>";
echo "<td>Edition</td>";
echo "</tr>";

foreach($res as $row)
{
	echo "<tr valign=top>";
	echo "<td>$row->login</td>";
	echo "<td>$row->niveau</td>";
	echo "<td>$row->email</td>";
	echo "<td>".datefr($row->date_creation)."</td>";
	echo "<td>".datefr($row->date_lastpass)."</td>";
	echo "<td><a href=admin-view-edit.php?clef=$row->idclef class=links>Cliquez ici</a></td>";
	echo "</tr>";
}

unset ($cnx);
echo "</table>";
?>
<?php
require_once "footer.inc.php";
?>
