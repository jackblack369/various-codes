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

$sql="select carnet.*,carnet.id as idlinks,user.id,user.idclef 
	 FROM carnet,user 
	 WHERE user.id=carnet.iduser AND carnet.iduser='".$_SESSION['iduser']."' AND user.idclef='".$_SESSION['idclef']."' ";
assert ('$cnx->prepare($sql)');
$qid=$cnx->prepare($sql);
$qid->execute();

@chmod($destDL,0777);
$nom_fichier=$destDL."".$_SESSION['login'].".csv";
if(file_exists($nom_fichier))
{
	unlink($nom_fichier);
}


$fp = fopen($nom_fichier, "a");             // ouverture du fichier en écriture

export("Nom",$separateur);
export("Prenom",$separateur);
export("Adresse",$separateur);
export("Code Postal",$separateur);
export("Ville",$separateur);
export("email",$separateur);
export("Tel",$separateur);
export("Portable");

$i=1;
while( $list=$qid->fetch(PDO::FETCH_OBJ) ) 
{
export($list->nom,$separateur);
export($list->prenom,$separateur);
export($list->adresse1,$separateur);
export($list->codepostal,$separateur);
export($list->ville,$separateur);
export($list->email,$separateur);
export($list->tel,$separateur);
export($list->portable);

}

  fclose($fp); 

?>
<br>
<span class=texte>Préparation Exportation... terminé</span><br>
<br><br>
<span class=texte>Telecharger le fichier</span>
<a href="include/download.php?filename=<?php echo $destDL.$_SESSION['login'].".csv";?>" class=links>Cliquer ici</a> 
<?php
$qid->closeCursor();
$cnx = null;
?>

<?php 
require_once "footer.inc.php"; 
?>
