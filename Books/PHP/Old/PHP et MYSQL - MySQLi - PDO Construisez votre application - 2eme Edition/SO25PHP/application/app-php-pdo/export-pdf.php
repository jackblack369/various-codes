<?php $menu="export";
require_once "include/config.inc.php";
?>
<?php require_once "head.inc.php"; ?>

Choisir une ligne pour exporter une fiche de votre carnet<br>
<?php
$sql="select carnet.*,user.id,user.idclef 
	 FROM carnet,user 
	 WHERE user.id=carnet.iduser 
	 	AND carnet.iduser='".$_SESSION['iduser']."' 
	 	AND user.idclef='".$_SESSION['idclef']."' 
	";

assert ('$cnx->prepare($sql)');
$qid=$cnx->prepare($sql);
$qid->execute();

echo "<table border=1 width=100%>";
echo "<tr>";
echo "<td>Nom</td>";
echo "<td>Prenom</td>";
echo "<td>Email</td>";
echo "<td>Tel</td>";
echo "<td>Portable</td>";
echo "<td>Export PDF</td>";
echo "</tr>";

while( $row=$qid->fetch(PDO::FETCH_OBJ) ) 
{
	echo "<tr>";
	echo "<td>$row->nom</td>";
	echo "<td>$row->prenom</td>";
	echo "<td>$row->email</td>";
	echo "<td>$row->tel</td>";
	echo "<td>$row->portable</td>";
	echo "<td><a href=export-pdf-file.php?carnetclef=$row->carnetclef class=links target=_blanck>Cliquez ici</a></td>";
	echo "</tr>";
}
echo "</table>";
$qid->closeCursor();
$cnx = null;

?>
<?php
require_once "footer.inc.php";
?>
