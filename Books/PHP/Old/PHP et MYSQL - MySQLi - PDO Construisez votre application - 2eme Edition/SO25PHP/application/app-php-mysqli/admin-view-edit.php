<?php $menu="admin"; 
require_once "include/config.inc.php";
if ($_SESSION['niveau']!="admin")	header("Location:index.php");
?>
<?php require_once "head.inc.php"; ?>
<?php
if (sizeof($_POST) > 0) 
{
   $frm = $_POST;
   $message_erreur = valide_form($frm, $erreurs);

    if (empty($message_erreur)) 
	{
       update_compte($frm);
       afficher_enregistrement_succes();
       include("footer.inc.php");
       exit;
	}       
}
else
{
$clef = verif_GetPost($_GET['clef']);

$sql="SELECT * FROM user WHERE idclef='$clef'";
assert ('mysqli_query($connex, $sql)');
$qid = mysqli_query($connex, $sql);  
if (!$qid)  die ("Requête invalide :  " . mysqli_error($connex));
$list=mysqli_fetch_object( $qid);
}

function valide_form(&$frm, &$erreurs) 
{

        $erreurs = array();
        $msg = array();

	$email=htmlentities($frm['email']);
	
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

	$niveau=array ('user','admin');
	if (!in_array ($frm['niveau'],$niveau))
	{
		$erreurs['niveau'] = true;
		$msg['niveau'] = " Problème avec la liste déroulante";
	}
	
    return $msg;
}

function update_compte(&$frm)
{
Global $connex;

$sql = "UPDATE user SET 
	niveau='".$frm['niveau']."'
	,email='".htmlentities($frm['email'])."'
	WHERE idclef='".$frm['clef']."'
	";

assert ('mysqli_query($connex,$sql)');
$qid = mysqli_query($connex, $sql);  
if (!$qid)  die ("Requête invalide :  " . mysqli_error($connex));

}

function afficher_enregistrement_succes()
{
	echo "<h1><center>";
	echo "Mise à jour...Termine";
	echo "</center></h1>";
}
?>
<html>

<body>
Compte de : 
<form name="fcompte" method="post" action="<?php echo htmlentities($_SERVER['PHP_SELF']); ?>">
<input name="clef" type="hidden" value="<?php echo $clef; ?>">
<table width="500" border="0" cellspacing="2" cellpadding="2" align=center>
  <tr>
    <td align=right>Login : </td>
    <td><?php echo $list->login;?></td>
  </tr>
  <tr>
    <td align=right>Mot de Passe : </td>
    <td>
<?php for ($i=0;$i<strlen($list->password);$i++) 
{
	echo "*";
}
?>
 </td>
  </tr>  <tr> 
    <td>Email : </td>
    <td><input name="email" type="text" value="<?php echo stripslashes($list->email) ?>">
        * <?php  if (isset($erreurs['email'])) echo $message_erreur['email'] ?></td>
  </tr>
  <tr> 
    <td>Niveau : </td>
    <td>
    <select name="niveau" > 
    <?php
    echo ligne_selected('Utilisateur','user',$list->niveau);
    echo ligne_selected('Administrateur','admin',$list->niveau);
	?>        
    </select><?php  if (isset($erreurs['niveau'])) echo $message_erreur['niveau'] ?></td>
  </tr>
  <tr> 
    <td colspan="2" align=center>
        <input type="submit" name="Submit" value="Mise A Jour"> 
      </td>
  </tr>
</table></form>
</body></html>
<?php require ("footer.inc.php"); ?>