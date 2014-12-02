<?php $menu="carnet";
require_once "include/config.inc.php";
?>
<?php require_once "head.inc.php"; ?>
<?php
if (sizeof($_POST) > 0) 
{
      $frm = $_POST;

       $message_erreur = valide_form($frm, $erreurs);

        if (empty($message_erreur)) 
		{
               $id_site=insere_carnet($frm);

				require_once ("include/fct_upload.inc.php");
			
				if (isset($_FILES['fichier'])||!empty($_FILES['fichier'])) $fichier = $_FILES['fichier'];
				if ( $fichier && $fichier != "none")
				{		
					$rep=upload($destDir,$fichier);
					if ($rep[0]== TRUE)
					{
						$fichier=rename_fichier($frm['prenom'],$rep[1]);
					
						$sql = "UPDATE carnet SET photo='".$fichier."' WHERE id='".$id_site."' ";
						assert ('mysqli_query($connex, $sql)');
						$qid = mysqli_query($connex,$sql);
						if (!$qid)     die('Requête invalide : ' . mysqli_error($connex));
					}
				}
				    
			   afficher_enregistrement_succes();
				mysqli_close($connex);
			   include("footer.inc.php");
			   exit;
        }
}


function valide_form(&$frm, &$erreurs) 
{
Global $connex;
 
    $erreurs = array();
    $msg = array();

	if ($_SESSION['textCaptcha'] != $frm['textCaptcha']) 
	{
		$erreurs['textCaptcha'] = true;
		$msg['textCaptcha'] = "Erreur de saisie";
	}
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
	elseif  ((mysqli_num_rows(mysqli_query($connex,"SELECT 1 FROM carnet WHERE email='".$frm['email']."' AND iduser='".$_SESSION['iduser']."'")) > 0))
	{
		$erreurs['email'] = true;
		$msg['email'] = "L'email existe déjà";
	} 
	
    return $msg;
}

function insere_carnet(&$frm)
{
Global $connex;

		$sql = "
		INSERT INTO carnet (
		        `iduser`
		        , `carnetclef`
		        , `nom`
		        , `prenom`
		        , `adresse1`
		        , `adresse2`	
		        , `codepostal`	
		        , `ville`	
		        , `tel`	
		        , `portable`	
		        , `email`	
		) VALUES (
		        '".$_SESSION['iduser']."'
		        ,'".recup_clef()."'
		        ,'".strtoupper(htmlentities($frm['nom']))."'
		        ,'".ucfirst( strtolower(htmlentities($frm['prenom'])))."'
		        ,'".htmlentities($frm['adresse1'])."'
		        ,'".htmlentities($frm['adresse2'])."'
		        ,'".$frm['codepostal']."'
		        ,'".htmlentities($frm['ville'])."'
		        ,'".htmlentities($frm['tel'])."'
		        ,'".htmlentities($frm['portable'])."'
		        ,'".$frm['email']."'
		 )";

		$qid = mysqli_query($connex,$sql);
		if (!$qid)     die('Requête invalide : ' . mysqli_error($connex));

 		$id_carnet = mysqli_insert_id($connex);

        $sql = "
        INSERT INTO carnet_details (
                `idcarnet`
                , `idrubrique`	
                , `observation`	
       	) VALUES (
                '".$id_carnet."'
                ,'".$frm['idrubrique']."'
                ,'".htmlentities($frm['observation'])."'
         )";
		$qid = mysqli_query($connex,$sql);
		if (!$qid)     die('Requête invalide : ' . mysqli_error($connex));


         return $id_carnet;     

}

function afficher_enregistrement_succes()
{
	echo "<h1><center>";
	echo "Enregistrement a été correctement enregistré.";
	echo "</center></h1>";
}



      
?>
<html><body>
Ajouter un contact dans le carnet : 
<form enctype="multipart/form-data" name="fcoord" method="post" action="<?php echo htmlentities($_SERVER['PHP_SELF']); ?>">

<table width="500" border="0" cellspacing="2" cellpadding="2">
 <tr><td align=right>
   Genre :
 </td><td> 
 	<?php
 	echo "<select name=\"genre\">";
  	echo ligne_selected(" ","-1",$frm['genre']);
 	echo ligne_selected("Monsieur","Monsieur",$frm['genre']);
 	echo ligne_selected("Madame","Madame",$frm['genre']);
 	echo ligne_selected("Mademoiselle","Mademoiselle",$frm['genre']);
	echo "</select>";
 	?>
 </td></tr>
  <tr> 
    <td align=right>Nom : </td>
    <td><input name="nom" type="text" value="<?php if (isset($frm['nom'])) echo stripslashes($frm['nom']); ?>"></td>
  </tr>
  <tr> 
    <td align=right>Prenom : </td>
    <td><input name="prenom" type="text" value="<?php if (isset($frm['prenom'])) echo stripslashes($frm['prenom']) ?>">
        * <?php if (isset($erreurs['prenom'])) echo $message_erreur['prenom'] ?></td>
  </tr>
  <tr> 
    <td align=right>Adresse : </td>
    <td><input name="adresse1" type="text"  value="<?php if (isset($frm['adresse1'])) echo stripslashes($frm['adresse1']); ?>"></td>
  </tr>
  <tr> 
    <td align=right>&nbsp; </td>
    <td><input name="adresse2" type="text" value="<?php if (isset($frm['adresse2'])) echo stripslashes($frm['adresse2']); ?>"></td>
  </tr>
  <tr> 
    <td align=right>Code postal : </td>
    <td><input name="codepostal" type="text" size="5" maxlength="5" value="<?php if (isset($frm['codepostal']))  echo stripslashes($frm['codepostal']); ?>"></td>
  </tr>
  <tr> 
    <td align=right>Ville : </td>
    <td><input name="ville" type="text" size="30" maxlength="30" value="<?php if (isset($frm['ville'])) echo stripslashes($frm['ville']); ?>"></td>
  </tr>
 <tr> 
    <td align=right>Email : </td>
    <td><input name="email" type="text" value="<?php if (isset($frm['email'])) echo stripslashes($frm['email']); ?>">
       * <?php if (isset($erreurs['email'])) echo $message_erreur['email'] ?></td>
  </tr>
  <tr> 
    <td align=right>T&eacute;l&eacute;phone : </td>
    <td><input name="tel" type="text" value="<?php if (isset($frm['tel'])) echo stripslashes($frm['tel']); ?>"></td>
  </tr>
  <tr> 
    <td align=right>Portable : </td>
    <td><input name="portable" type="text" value="<?php if (isset($frm['portable'])) echo stripslashes($frm['portable']); ?>"></td>
  </tr>
  <tr> 
    <td align=right>Photo : </td>
    <td> 
    <input type="hidden" name="MAX_FILE_SIZE" value="<?php echo $taille_max; ?>" >
<input name="fichier" type="file" >
</td>
  </tr>
  <tr> 
    <td colspan=2><br /><hr><br /></td>
  </tr> 
  <tr> 
    <td align=right>Rubrique : </td>
    <td> 
   
    
	<?php
	
	$sql="SELECT id,iduser,nom FROM rubrique WHERE iduser='".$_SESSION['iduser']."' ORDER BY nom";
	assert ('mysqli_query($connex,$sql)');
	$rubrique=mysqli_query($connex,$sql);
	if (!$rubrique)     die('Requête invalide : ' . mysqli_error($connex));
	if (mysqli_num_rows($rubrique)!=0)
	{
		echo "<select name=\"idrubrique\">";
		echo ligne_selected(" ","-1","");
		while( $list=mysqli_fetch_object( $rubrique) )       
		{
		 	echo ligne_selected($list->nom,$list->id,'');
		}
		echo "</select>";
	}   
	mysqli_free_result($rubrique);
	mysqli_close($connex);
	?>
    </td>
  </tr>
 <tr> 
    <td align=right>Observation : </td>
    <td><textarea name="observation" cols="40" rows="3"><?php if (isset($frm['observation'])) echo stripslashes($frm['observation']); ?></textarea></td>
  </tr>
   <tr> 
    <td colspan=2><br /><hr><br /></td>
  </tr> 
   <tr> 
    <td>Captcha</td> 
	<td><img src="include/fct_captcha.inc.php">
 <input type='text' name='textCaptcha' size='10'> * 
 <?php if (isset($erreurs['textCaptcha'])) echo $message_erreur['textCaptcha'] ?></td>
  </tr> 
   <tr> 
    <td colspan=2><br /><hr><br /></td>
  </tr> 
  <tr> 
    <td colspan="2" align=center>
        <input type="submit" name="action" value="Confirmer">
        <input type="submit" name="action" value="Annuler">
      </td>
  </tr>
</table></form>
</body></html>
<?php require ("footer.inc.php"); ?>