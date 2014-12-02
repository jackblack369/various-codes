<?php $menu="export";
require_once "include/config.inc.php";
?>
<?php require_once "head.inc.php"; ?>

<?php
if (!is_dir($destDL))
{
	if (!@mkdir($destDL))
	{
	    return array(false,'Erreur lors de la création du dossier $destDir');
	}
}
@chmod($destDL,0777);
$nom_fichier=$destDL."".$_SESSION['login'].".xml";
if(file_exists($nom_fichier))
{
	unlink($nom_fichier);
}

$sql="select carnet.*,carnet.id as idlinks,user.id,user.idclef 
	 FROM carnet,user 
	 WHERE user.id=carnet.iduser AND carnet.iduser='".$_SESSION['iduser']."' AND user.idclef='".$_SESSION['idclef']."' ";

$qid=mysql_query($sql);
if( ! $qid )  echo  "Probleme dans la table exemple :  " . mysql_error();

if (mysql_num_rows($qid)>0)
{
	$_xml ="<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>\r\n";
	$_xml .="<carnet_adresse>\r\n";
	while ($row = mysql_fetch_object($qid)) 
	{
		$_xml .="<personne>\r\n";
		$_xml .="\t\t<nom>$row->nom</nom>\r\n";
		$_xml .="\t\t<prenom>$row->prenom</prenom>\r\n";
		$_xml .="\t\t<adresse1>$row->adresse1</adresse1>\r\n";
		$_xml .="\t\t<adresse2>$row->adresse2</adresse2>\r\n";
		$_xml .="\t\t<codepostal>$row->codepostal</codepostal>\r\n";
		$_xml .="\t\t<ville>$row->ville</ville>\r\n";
		$_xml .="</personne>\r\n";
	}
	$_xml .="</carnet_adresse>";

	file_put_contents($nom_fichier,$_xml);

	echo "Fichier crée<br>";
	echo "<a href='".$destDL.$_SESSION['login'].".xml' class='links'>Visualier le fichier XML.</a>";
} else {
	echo "Aucun enregistrement possible";
}

mysql_close();

?>

<?php require_once ("footer.inc.php"); ?>
