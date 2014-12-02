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
       insere_compte($frm);
       afficher_enregistrement_succes();
       include("footer.inc.php");
       exit;
	}
}

function valide_form(&$frm, &$erreurs) 
{
GLOBAL $connex;

        $erreurs = array();
        $msg = array();

	if (empty($frm['nlogin']) ) 
	{
			$erreurs['nlogin'] = true;
			$msg['nlogin'] = "Absence de Login";
	}

	if  (mysqli_num_rows(mysqli_query($connex,"SELECT 1 FROM user WHERE login = '".$frm['nlogin']."'")) > 0)
	{
		$erreurs['nlogin'] = true;
		$msg['nlogin'] = " Cet identifiant existe déjà";
	} 


	if (empty($frm['npassword'])) 
	{
			$erreurs['npassword'] = true;
			$msg['npassword'] = "Absence de mot de passe";
	}
	if (empty($frm['npassword2'])) 
	{
			$erreurs['npassword2'] = true;
			$msg['npassword2'] = "Absence de mot de passe";
	}
    if ($frm['npassword']<>$frm['npassword2']) 
	{
	    $erreurs['npassword2'] = true;
	    $msg['npassword2'] = " Mots de passes différents";
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
		$msg['email'] = " Mauvais format d'email";
	}
	
	elseif  ((mysqli_num_rows(mysqli_query($connex, "SELECT 1 FROM user WHERE email = '".$frm['email']."'")) > 0))
	{
		$erreurs['email'] = true;
		$msg['email'] = "L'email existe déjà";
	} 
	
	$niveau=array ('user','admin');
	if (!in_array ($frm['niveau'],$niveau))
	{
		$erreurs['niveau'] = true;
		$msg['niveau'] = " Problème avec la liste déroulante";
	}

    return $msg;
}

function insere_compte(&$frm)
{
global $connex;

		$creat_compte=date("Y-m-j");

        $sql = "
        INSERT INTO user (
                `niveau`
                , `idclef`
                , `login`
                , `password`
                , `email`
                , `date_creation`	
                , `page`	
        ) VALUES (
                '$frm[niveau]'
		        ,'".recup_clef()."'
                ,'".htmlentities($frm['nlogin'])."'
                ,'".md5($frm['npassword'])."'
                ,'".htmlentities($frm['email'])."'
				,'$creat_compte'
				,'compte.php'
        )";
$qid = mysqli_query($connex, $sql);  
if (!$qid)  die ("Requête invalide :  " . mysqli_error($connex));
}

function afficher_enregistrement_succes()
{
	echo "<h1><center>";
	echo "Enregistrement a été correctement enregistré.";
	echo "</center></h1>";
}
?>
<html>

<body>
Nouveau compte : 
<form name="fcompte" method="post" action="<?php echo htmlentities($_SERVER['PHP_SELF']); ?>">

<table width="500" border="0" cellspacing="2" cellpadding="2" align=center>
    <tr>
      <td>Login :</td>
      <td><input type="text" name="nlogin"  value="<?php  if (isset($frm['nlogin'])) echo stripslashes($frm['nlogin']) ?>">
        * <?php if (isset($erreurs['nlogin'])) echo $message_erreur['nlogin'] ?></td>
    </tr>
    <tr>
      <td>Mot de passe  :</td>
      <td><input type="password" name="npassword"  value="<?php  if (isset($frm['npassword'])) echo stripslashes($frm['npassword']) ?>"> * 
      <?php if (isset($erreurs['npassword'])) echo $message_erreur['npassword'] ?></td>
    </tr>
    <tr>
      <td>Confirmez Mot de passe :</td>
      <td><input type="password" name="npassword2"  value="<?php  if (isset($frm['npassword2'])) echo stripslashes($frm['npassword2']) ?>"> * 
      <?php if (isset($erreurs['npassword2'])) echo $message_erreur['npassword2'] ?></td>
    </tr>
  <tr> 
    <td>Email : </td>
    <td><input name="email" type="text" value="<?php  if (isset($frm['email'])) echo stripslashes($frm['email']) ?>">
        * <?php  if (isset($erreurs['email'])) echo $message_erreur['email'] ?></td>
  </tr>
  <tr> 
    <td>Niveau : </td>
    <td><select name="niveau" >
        <option value="user">Utilisateur</option>
        <option value="admin">Administrateur</option>
          </select><?php  if (isset($erreurs['niveau'])) echo $message_erreur['niveau'] ?></td>
  </tr>
  <tr> 
    <td colspan="2" align=center>
        <input type="submit" name="Submit" value="Confirmer"> &nbsp;
         <input type="reset" name="Submit" value="Annuler">
      </td>
  </tr>
</table></form>
</body></html>
<?php require ("footer.inc.php"); ?>