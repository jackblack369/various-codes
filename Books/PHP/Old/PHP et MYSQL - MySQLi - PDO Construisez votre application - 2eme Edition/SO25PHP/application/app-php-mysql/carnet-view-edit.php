<?php $menu="carnet";
require_once "include/config.inc.php";
?>
<?php
if (sizeof($_POST) > 0) 
{
   $frm = $_POST;
   
switch($frm['action'])
{

case "Mise A Jour":
   $message_erreur = valide_form($frm, $erreurs);

    if (empty($message_erreur)) 
	{
       update_fiche($frm);
		fiche_details($frm);
	   require_once ("include/fct_upload.inc.php");
	
		if (isset($_FILES['fichier'])||!empty($_FILES['fichier'])) $fichier = $_FILES['fichier'];
		if ( isset($fichier) && $fichier != "none")
		{		
			$rep=upload($destDir,$fichier);
			if ($rep[0]== TRUE)
			{
				$fichier=rename_fichier($frm['prenom'],$rep[1]);
			
				$sql = "UPDATE carnet SET photo='".$fichier."' WHERE carnetclef='".$frm['carnetclef']."' ";
				assert ('mysql_query($sql)');
				$qid = mysql_query($sql);  
				if (!$qid)  die ("Probleme :  " . mysql_error());
			}
		}
       
       afficher_enregistrement_succes();
	$carnetclef=$frm['carnetclef'];
     include("footer.inc.php");
 echo "<a href=".htmlentities($_SERVER['PHP_SELF'])."?$carnetclef class=links>Retour fiche</a>";
       exit;
	}   
break;
case "Supprimer":
	$sql="select id FROM carnet WHERE carnetclef='".$frm['carnetclef']."' ";
	assert ('mysql_query($sql)');
	$qid = mysql_query($sql);  
	if (!$qid)  die ("Probleme :  " . mysql_error());
	$row=mysql_fetch_object( $qid);

	$sql="DELETE FROM carnet_details WHERE idcarnet='".$row->id."' ";
	assert ('mysql_query($sql)');
	$qid = mysql_query($sql);
	if (!$qid)     die('Requête invalide : ' . mysql_error());

	$sql="DELETE FROM carnet WHERE carnetclef='".$frm['carnetclef']."' ";
	assert ('mysql_query($sql)');
	$qid = mysql_query($sql);
	if (!$qid)     die('Requête invalide : ' . mysql_error());

	require_once "head.inc.php";
	echo "<h1><center>";
	echo "Fiche supprimée";
	echo "</center></h1>";
	require_once "footer.inc.php";
	exit;
break;
}

}
else
{
$carnetclef = verif_GetPost($_GET['carnetclef']);

$sql="SELECT * FROM carnet WHERE carnetclef='$carnetclef' ";
assert ('mysql_query($sql)');
$qid = mysql_query($sql);  
if (!$qid)  die ("Probleme :  " . mysql_error());

$nligne= mysql_num_rows($qid);
$list=mysql_fetch_object( $qid);

}


function valide_form(&$frm, &$erreurs) 
{
    $erreurs = array();
    $msg = array();

	if (empty($frm['prenom']) ) 
	{
			$erreurs['prenom'] = true;
			$msg['prenom'] = "Il manque le prénom";
	}

	$email=htmlentities($_POST['email'], ENT_QUOTES,'UTF-8');
	
	if (empty($email)) 
	{
		$erreurs['email'] = true;
		$msg['email'] = "Absence d'email";
	}

	elseif (!preg_match('`^[[:alnum:]]([-_.]?[[:alnum:]])*@[[:alnum:]]([-_.]?[[:alnum:]])*.([a-z]{2,4})$`',$email)) 
	{
		$erreurs['email'] = true;
		$msg['email'] = " Mauvais format d'Email";
	}
	
    return $msg;
}
function fiche_details(&$frm)
{
global $connex;

	$sql="select id FROM carnet WHERE carnetclef='".$frm['carnetclef']."' ";
	assert ('mysql_query($sql)');
	$qid = mysql_query($sql);  
	if (!$qid)  die ("Probleme :  " . mysql_error());
	$row=mysql_fetch_object( $qid);

$nligne=$frm['nligne']-1;
for ($i=0;$i<=$nligne;$i++)
{

$sql = "UPDATE carnet_details SET 
	idcarnet='".$row->id."'
	,idrubrique='".$frm['idrubrique'][$i]."' 
	,observation='".htmlentities($frm['observation'][$i])."' 
	WHERE id='".$frm['iddetails'][$i]."'
	";
	
	assert ('mysql_query($sql)');
	$qid = mysql_query($sql);  
	if (!$qid)  die ("Probleme :  " . mysql_error());

}

if ($frm['Newidrubrique']!=-1)
{
	
    $sql = "
    INSERT INTO carnet_details (
            `idcarnet`
            , `idrubrique`	
            , `observation`	
   	) VALUES (
            '".$row->id."'
            ,'".$frm['Newidrubrique']."'
            ,'".htmlentities($frm['NewObservation'])."'
     )";

    $qid = mysql_query($sql);
	if(! $qid ) die ('Requête invalide : ' . mysql_error());       
}

}

function update_fiche(&$frm)
{
global $connex;


$sql = "UPDATE carnet SET 
	genre='".$frm['genre']."'
	,nom='".strtoupper(htmlentities($frm['nom']))."'
	,prenom='".ucfirst( strtolower(htmlentities($frm['prenom'])))."'
	,adresse1='".htmlentities($frm['adresse1'])."'
	,adresse2='".htmlentities($frm['adresse2'])."'
	,codepostal='".$frm['codepostal']."'
	,ville='".htmlentities($frm['ville'])."'
	,tel='".htmlentities($frm['tel'])."'
	,portable='".htmlentities($frm['portable'])."'
	,email='".htmlentities($frm['email'])."'
	WHERE carnetclef='".$frm['carnetclef']."'
	";

	assert ('mysql_query($sql)');
	$qid = mysql_query($sql);  
	if (!$qid)  die ("Probleme :  " . mysql_error());



}

function afficher_enregistrement_succes()
{
	require_once "head.inc.php";
	echo "<h1><center>";
	echo "Mise à jour...Termine";
	echo "</center></h1>";
	require_once "footer.inc.php";
	exit;
}


?>
<html>

<body>
<?php require_once "head.inc.php"; ?>

Fiche : <br>
<form name="fiche" method="post" action="<?php echo htmlentities($_SERVER['PHP_SELF']); ?>"  enctype="multipart/form-data" >
<table width="100%" border="0" cellspacing="0" cellpadding="0"><tr><td valign="top">
<table  border="0" >
 <tr><td align=right>
   Genre :
 </td><td> 
 <input name="carnetclef" type="hidden" value="<?php echo $carnetclef; ?>">
 <select name="genre">
 	<?php
  	echo ligne_selected(" ","-1",$list->genre);
 	echo ligne_selected("Monsieur","Monsieur",$list->genre);
 	echo ligne_selected("Madame","Madame",$list->genre);
 	echo ligne_selected("Mademoiselle","Mademoiselle",$list->genre);
 	?>
  </select>
 </td></tr>
  <tr> 
    <td align=right>Nom : </td>
    <td><input name="nom" type="text" value="<?php echo stripslashes($list->nom); ?>"></td>
  </tr>
  <tr> 
    <td align=right>Prenom : </td>
    <td><input name="prenom" type="text" value="<?php echo stripslashes($list->prenom) ?>">
        * <?php if (isset($erreurs['prenom'])) echo $message_erreur['prenom'] ?></td>
  </tr>
  <tr> 
    <td align=right>Adresse : </td>
    <td><input name="adresse1" type="text"  value="<?php echo stripslashes($list->adresse1); ?>"></td>
  </tr>
  <tr> 
    <td align=right>&nbsp; </td>
    <td><input name="adresse2" type="text" value="<?php echo stripslashes($list->adresse2); ?>"></td>
  </tr>
  <tr> 
    <td align=right>Code postal : </td>
    <td><input name="codepostal" type="text" size="5" maxlength="5" value="<?php echo stripslashes($list->codepostal); ?>"></td>
  </tr>
  <tr> 
    <td align=right>Ville : </td>
    <td><input name="ville" type="text" size="30" maxlength="30" value="<?php echo stripslashes($list->ville); ?>"></td>
  </tr>
 <tr> 
    <td align=right>Email : </td>
    <td><input name="email" type="text" value="<?php echo stripslashes($list->email); ?>">
       * <?php if (isset($erreurs['email'])) echo $message_erreur['email'] ?></td>
  </tr>
  <tr> 
    <td align=right>T&eacute;l&eacute;phone : </td>
    <td><input name="tel" type="text" value="<?php echo stripslashes($list->tel); ?>"></td>
  </tr>
  <tr> 
    <td align=right>Portable : </td>
    <td><input name="portable" type="text" value="<?php echo stripslashes($list->portable); ?>"></td>
  </tr>
  <tr> 
    <td align=right>Photo : </td>
            <td>
<?php            
if ($list->photo)
{
	echo "<img src='".$destDir.$list->photo."'>";
} else {
?>
<input type="hidden" name="MAX_FILE_SIZE" value="<?php echo $taille_max; ?>" >
<input name="fichier" type="file" >
<?php } ?> 
</td>
  </tr>
<tr><td align="center" colspan="2"><a href=carnet-googlemaps.php?carnetclef=<?php echo $carnetclef; ?> class=links>Voir plan avec GoogleMaps</a> &nbsp;</td></tr>

</table>
</td><td valign=top>
<table border="0" cellspacing="2" cellpadding="2">
 <tr> 
    <td align=center>Rubrique</td>
    <td align=center>Observations</td>
    </tr>

     <?php 

$sql="SELECT carnet_details.idcarnet,carnet_details.id as idtmp,carnet_details.idrubrique,carnet_details.observation,carnet.id,carnet.carnetclef 
	FROM carnet,carnet_details  
	WHERE carnet.id=carnet_details.idcarnet AND carnet.carnetclef='$carnetclef' ";

	assert ('mysql_query($sql)');
	$qid = mysql_query($sql);  
	if (!$qid)  die ("Probleme :  " . mysql_error());

$nligne= mysql_num_rows($qid);

$nligne=0;
while ($row=mysql_fetch_array( $qid))  
{ 
  		echo "<tr>";
		echo "<td valign=top>";	

	echo "<select name=\"idrubrique[]\">";
	echo ligne_selected(" ","-1",$frm['idrubrique'][$i]);
	
	$sql="SELECT id,iduser,nom FROM rubrique WHERE iduser='".$_SESSION['iduser']."' ORDER BY nom";
	assert ('mysql_query($sql)');
	$rubrique=mysql_query($sql);
	if (!$rubrique)     die('Requête invalide : ' . mysql_error());
	if (mysql_num_rows($rubrique)!=0)
	{
		while( $row2=mysql_fetch_array( $rubrique) )       
		{
			echo ligne_selected($row2['nom'],$row2['id'],$row['idrubrique']);  
		}
	}   
	echo "</select>";
	echo "<br>";
	echo url_test($row['observation']);
	echo "</td>";
   	echo "<td>";
	echo "<textarea name=\"observation[]\" cols=\"30\" rows=\"3\">".stripslashes(nl2br($row['observation']))."</textarea>";
	echo "<input name=\"iddetails[]\" type=\"hidden\" value=".$row['idtmp'].">";
	echo "</td>";
	echo "</tr>";
   $nligne++;
} 
	mysql_free_result($qid);

?>
<input name="nligne" type="hidden" value="<?php echo $nligne; ?>">
  
   <tr> 
    <td> 
      
	<?php
	$sql="SELECT id,iduser,nom FROM rubrique WHERE iduser='".$_SESSION['iduser']."' ORDER BY nom";
	assert ('mysql_query($sql)');
	$rubrique=mysql_query($sql);
	if (!$rubrique)     die('Requête invalide : ' . mysql_error());
	if (mysql_num_rows($rubrique)!=0)
	{
		echo "<select name=\"Newidrubrique\">";
		echo ligne_selected(" ","-1","");
		while( $list=mysql_fetch_object( $rubrique) )       
		{
		 	echo ligne_selected($list->nom,$list->id,'');
		}
		echo "</select>";
	}   
		mysql_free_result($rubrique);
	 ?>
	
    </td>
    <td><textarea name="NewObservation" cols="30" rows="3"></textarea></td>

   </tr>
</table>
</td></tr>
<tr><td align=center colspan=4><input type="submit" name="action" value="Mise A Jour"> 
<input type="submit" name="action" value="Supprimer" onClick="Javascript:return confirm('Êtes-vous sûr de vouloir enlever cette ligne ?');"> </td></tr> 
</table>
</form>


<?php
	mysql_close();
?>
<?php require ("footer.inc.php"); ?>
</body></html>
