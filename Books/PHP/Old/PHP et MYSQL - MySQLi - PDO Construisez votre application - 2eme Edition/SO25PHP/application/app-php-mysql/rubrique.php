<?php $menu="parametre";
require_once "include/config.inc.php";
?>
<?php require_once "head.inc.php"; ?>
<?php
if (isset($_GET['choix'])) $choix=verif_GetPost($_GET['choix']);
if (isset($_GET['id'])) $id=verif_GetPost($_GET['id']);


if (isset ($choix) && $choix=="trash")
{
	$sql="SELECT carnet_details.idcarnet, carnet_details.idcarnet,carnet.iduser,carnet.id  
		FROM carnet_details ,carnet  
		WHERE carnet_details.idcarnet=carnet.id 
			AND carnet.iduser='".$_SESSION['iduser']."' 
			AND carnet_details.idrubrique='$id' 
		";

	assert ('mysql_query($sql)');
	$qid=mysql_query($sql);
	if (mysql_num_rows($qid))
		echo "Impossible effacer la ligne - Des enregistrements sont associés";
	else
	{
		require "rubrique_exec.inc.php";
		exit;
	}

	$id="";
	$choix="";

}

if (sizeof($_POST) > 0) 
{
 $frm = $_POST;
switch($frm['action'])
{
	case "Mise A Jour":
		if ( empty($frm['nom']) ) 
		{
			echo "Le champ est vide";
		} elseif ($_SESSION['ip'] != $_SERVER['REMOTE_ADDR'] 
		 		 	|| $_POST['idclef'] != $_SESSION['idclef'])
		{
			echo "Tentative de pénétation";
		} else {       
		$sql="UPDATE rubrique SET 
				`nom` = '".htmlentities($frm['nom'], ENT_QUOTES)."'
				where id='".htmlentities($frm['id'])."' AND iduser='".$_SESSION['iduser']."' ";
		assert ('mysql_query($sql)');	
		 $qid = mysql_query($sql);	
		 if (!$qid)     die('Requête invalide : ' . mysql_error());
		}
	break;
	
    case "Ajouter":
		if ( empty($frm['nom']) ) 
		{
			echo "Le champ est vide";
		} elseif ($_SESSION['ip'] != $_SERVER['REMOTE_ADDR'] 
		 		 	|| $_POST['idclef'] != $_SESSION['idclef'])
		{
			echo "Tentative de pénétation";
		} else {       
		 $sql = "INSERT INTO rubrique (`iduser`, `nom`)
		 		VALUES (
		 		'".$_SESSION['iduser']."'
		 		,'".htmlentities($frm['nom'], ENT_QUOTES)."'
		 		)";
		 $qid = mysql_query($sql);	
		 if (!$qid)     die('Requête invalide : ' . mysql_error());
		}
	break;
	
	case "Confirmer":
		$sql="SELECT * FROM user
			WHERE password = '".MD5($frm['pass'])."' 
				AND idclef='".$_SESSION['idclef']."' 
				AND idclef='".$frm['idclef']."'	
			";
		assert ('mysql_query($sql)');
		$qid=mysql_query($sql);
		if (!mysql_num_rows($qid))
			die ("Erreur dans votre mot de passe");
		else
		{
		
			$sql="DELETE FROM rubrique	WHERE 
						 rubrique.id = '".$frm['id']."' 
					AND rubrique.iduser = '".$_SESSION['iduser']."'  
				";
			assert ('mysql_query($sql)');
			$qid = mysql_query($sql);
				if (!$qid)     die('Requête invalide : ' . mysql_error());
		}

	break;
	
	
	default:
	break;
}

}
?>
<html><body>
  <table width="100%" border="1">
    <tr>
<?php
$sql="SELECT rubrique.id as idselect,rubrique.iduser,rubrique.nom,user.id  
		FROM rubrique, user  
		WHERE rubrique.iduser=user.id 
			AND rubrique.iduser='".$_SESSION['iduser']."' 
			AND user.idclef='".$_SESSION['idclef']."' 
		ORDER BY rubrique.nom
	";

assert ('mysql_query($sql)');
$qid=mysql_query($sql);
if (!$qid)     die('Requête invalide : ' . mysql_error());
if (mysql_num_rows($qid)!=0)
{
	echo "<td><b>Rubrique(s) Disponible(s)</b><br>";
	echo "<table>";
	while( $row=mysql_fetch_object( $qid) )       
	{
		echo "<tr>";
		echo "<td>".stripslashes($row->nom)."</td>";
		echo "<td><a href=".$_SERVER['PHP_SELF']."?id=$row->idselect class=links>Edit</a></td>";
		echo "<td>";
		echo "<a ";
		echo "onClick=\"Javascript:return confirm('Êtes-vous sûr de vouloir enlever cette ligne ?');\" ";
		echo "href=".$_SERVER['PHP_SELF']."?id=".$row->idselect."&choix=trash";
		echo " class=links>Supp</a>";
		echo "</td>";
		echo "</tr>";
	}
	echo "</table>";
	echo "</td>";
}
else
{
	echo "Aucune rubrique disponible";
}
?>
      <td align="center">
<form action="<?php echo htmlentities($_SERVER['PHP_SELF']); ?>"  method="post">
<?php if (isset($id)) { ?>
Modifier une Rubrique : 
<?php
$id=htmlentities($id);
$sql="SELECT rubrique.id as idselect,rubrique.iduser,rubrique.nom,user.id  
		FROM rubrique, user  
		WHERE rubrique.iduser=user.id 
			AND rubrique.iduser='".$_SESSION['iduser']."' 
			AND user.idclef='".$_SESSION['idclef']."' 
			AND rubrique.id='$id' 
	";
assert ('mysql_query($sql)');
$req=mysql_query($sql);
if (!$req)     die('Requête invalide : ' . mysql_error());
$row=mysql_fetch_object( $req);       

?>
<input name="id" type="hidden" value=<?php echo $id;?> ><br>
<input name="idclef" type="hidden" value=<?php echo $_SESSION['idclef'];?> ><br>
<input type="text" name="nom"  value="<?php if (isset($row->nom)) echo stripslashes($row->nom); ?>"><br>
<input type="submit" name="action" value="Mise A Jour">
<?php } else { ?>
Ajouter une Rubrique : <br>
<input name="idclef" type="hidden" value=<?php echo $_SESSION['idclef'];?> ><br>
<input type="text" name="nom"  value="">
<input type="submit" name="action" value="Ajouter"> 
<?php } ?>
</form>
</td>
    </tr>
  </table>

</body></html>
<?php require_once "footer.inc.php"; ?>