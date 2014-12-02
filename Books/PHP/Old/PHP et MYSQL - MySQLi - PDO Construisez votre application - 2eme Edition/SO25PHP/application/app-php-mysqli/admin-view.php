<?php $menu="admin"; 
require_once "include/config.inc.php";
if ($_SESSION['niveau']!="admin")	header("Location:index.php");
?>
<?php require_once "head.inc.php"; ?>

<?php
$sql="select * from user";
assert ('mysqli_query($connex,$sql)');
$qid = mysqli_query($connex, $sql);  
if (!$qid)  die ("Requête invalide :  " . mysqli_error($connex));

echo "<table border=1 width=100%>";
echo "<tr>";
echo "<td>Login</td>";
echo "<td>Niveau</td>";
echo "<td>Email</td>";
echo "<td>Date creation</td>";
echo "<td>dernier passage</td>";
echo "<td>Edition</td>";
echo "</tr>";

while( $row=mysqli_fetch_object( $qid) ) 
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
mysqli_free_result($qid);
mysqli_close($connex);

echo "</table>";
?>
<?php
require_once "footer.inc.php";
?>
