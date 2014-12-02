<?php $menu="carnet";
require_once "include/config.inc.php";

?>
<html>
<head></head>
<body>
<?php require_once "head.inc.php"; ?>
<?php
if (isset ($start) && !$start)  $start=0;

echo "Sélectionner une lettre : ";
$lettre=array('A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','Tous');
for ($i=0;$i<27;$i++)
{
	echo "<a href=".htmlentities($_SERVER['PHP_SELF'])."?filtre=".$lettre[$i]." class=links>".$lettre[$i]."</a>";
	if ($i != 26) echo "-";
}
echo "<br>";

if (isset ($_GET['filtre'])) $filtre=verif_GetPost($_GET['filtre']);

if (!empty($filtre) && $filtre!="Tous")
{
	$filtre= "AND ( nom LIKE '" . $filtre . "%' )  ";
}
else
	$filtre= " ";

if (isset ($_GET['tri'])) $tri=verif_GetPost($_GET['tri']);
if (isset ($_GET['ordre'])) $ordre=verif_GetPost($_GET['ordre']);
if (isset ($tri) && $tri=="nom") $order="ORDER BY nom "; else $order="";
if (isset ($tri) && $tri=="prenom") $order="ORDER BY prenom ";

if (isset ($tri) && $tri && $ordre!="DESC") $ordre = "DESC"; else $ordre = "";

if (isset ($_GET['start'])) $start=verif_GetPost($_GET['start']); else $start=0;

if(isset($start) && !$start)
    $start=0;
else 
	$start = $start;


$sql="select carnet.*,user.id,user.idclef 
	 FROM carnet,user 
	 WHERE user.id=carnet.iduser 
	 	AND carnet.iduser='".$_SESSION['iduser']."' 
	 	AND user.idclef='".$_SESSION['idclef']."'
	 	$filtre
	 	$order $ordre
	 	LIMIT $start,$nb_pages 
	 ";

	assert ('mysql_query($sql)');
	$qid = mysql_query($sql);  
	if (!$qid)  die ("Probleme :  " . mysql_error());

if (mysql_num_rows($qid))
{

echo "<table border=1 width=100%>";
echo "<tr>";
echo "<td>Nom ";
echo "<a href=".htmlentities($_SERVER['PHP_SELF'])."?tri=nom&ordre=$ordre class=links>Tri</a>";
echo "</td>";
echo "<td>Prenom";
echo "<a href=".htmlentities($_SERVER['PHP_SELF'])."?tri=prenom&ordre=$ordre class=links>Tri</a>";
echo "</td>";
echo "<td>Email</td>";
echo "<td>Tel</td>";
echo "<td>Portable</td>";
echo "<td>Edit</td>";
echo "</tr>";

while( $row=mysql_fetch_object( $qid) )       
{
	echo "<tr>";
	echo "<td>".$row->nom."</td>";
	echo "<td>".$row->prenom."</td>";
	echo "<td>".$row->email."</td>";
	echo "<td>".$row->tel."</td>";
	echo "<td>".$row->portable."</td>";
	echo "<td><a href=carnet-view-edit.php?carnetclef=$row->carnetclef class=links>Cliquez ici</a></td>";
	echo "</tr>";
}
echo "</table>";
}



if($start)
{
echo "<a href=".$_SERVER['PHP_SELF']."?start=".($start-$nb_pages)." class=links>";
echo "Début";
echo "</a>";
echo "&nbsp;";
}


$sql="select COUNT(*)  
	 FROM carnet,user 
	 WHERE user.id=carnet.iduser 
	 	AND carnet.iduser='".$_SESSION['iduser']."' 
	 	AND user.idclef='".$_SESSION['idclef']."'
	 	$filtre
	 	$order
	 ";

	assert ('mysql_query($sql)');

	$qid = mysql_query($sql);  
	if (!$qid)  die ("Probleme :  " . mysql_error());

$row=mysql_fetch_row($qid);

if($row[0]>$nb_pages)
{
	$start++;
    for($index=0;($index*$nb_pages)<$row[0];$index++)
    {
    	
	    $npage=$index+1;
	
		if ($start!=$npage)
		{
	     echo "<b>";
		 echo "<a href=".$_SERVER['PHP_SELF']."?start=".$index*$nb_pages." class=links>$npage</a>";
		 echo "</b>";
		 echo "&nbsp;";
		}
		else
		{
			echo $npage;
			 echo "&nbsp;";
		}
    }
}
else
	$npage=0;
	
if($row[0]>($start+$npage))
{
echo "<a href=".$_SERVER['PHP_SELF']."?start=".($start+$npage)." class=links>";
echo " Fin";
echo "</a>";
}


mysql_free_result($qid);
mysql_close();

require_once "footer.inc.php";
?>
</body></html>