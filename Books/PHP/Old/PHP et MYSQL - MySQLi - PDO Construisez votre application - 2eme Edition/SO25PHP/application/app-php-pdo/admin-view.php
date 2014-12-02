<?php $menu="admin"; 
require_once "include/config.inc.php";
if ($_SESSION['niveau']!="admin")	header("Location:index.php");
?>
<?php require_once "head.inc.php"; ?>

<?php
$sql="select * from user";
assert ('$cnx->prepare($sql)');
$qid=$cnx->prepare($sql);
$qid->execute();

echo "<table border=1 width=100%>";
echo "<tr>";
echo "<td>Login</td>";
echo "<td>Niveau</td>";
echo "<td>Email</td>";
echo "<td>Date creation</td>";
echo "<td>dernier passage</td>";
echo "<td>Edit</td>";
echo "</tr>";

while( $row=$qid->fetch(PDO::FETCH_OBJ) ) 
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

$qid->closeCursor();
$cnx = null;

echo "</table>";
?>
<?php
require_once "footer.inc.php";
?>
