<?php 
require_once('admin/connex.inc.php');
require_once('include/fct.inc.php');

if (sizeof($_POST) > 0) 
{

if (isset($_POST['action'])) $action=verif_GetPost($_POST['action']);

switch($action)
{
	
    case "Confirmer":
	$action="";
	$emaildest=htmlentities($_POST['email'], ENT_QUOTES,'UTF-8');
	$emaildest=mysqli_real_escape_string($connex,$emaildest);
	
	$sql="select * from user where email='$emaildest' ";
	assert ('mysqli_query($connex, $sql)');
	$qid=mysqli_query($connex, $sql);
	if( ! $qid )  die ("Probleme de requete :  " . mysqli_error($connex));
	
	if (!mysqli_num_rows($qid))
	{
	 	require_once "head.inc.php";
		echo "Cet Email n'existe pas dans nos comptes, Merci de recommencer<br>";
		echo "<a href= ".htmlentities($_SERVER['PHP_SELF'])." class=links>Cliquer ici</a>";
		include "footer.inc.php";
		exit;
	} 
	else
	{
		$row=mysqli_fetch_object($qid);
		$login=$row->login;
		$mot_de_passe=creation_password();
		
		$sql = "UPDATE user SET password='".MD5($mot_de_passe)."' WHERE email='$emaildest' ";
		assert ('mysqli_query($connex, $sql)');
		$qid = mysqli_query($connex, $sql);
		if (!$qid)     die('Requête invalide : ' . mysqli_error($connex));
		
		
		$emailexp="votreemail@votresite.com";
		$titre="Votre mot de passe";
		$mailmessage="Bonjour,\n Vous avez demandé vos codes d'accès pour le site ... : \n\n\n";
		$mailmessage.=" Identifiant : ".$login."\n";
		$mailmessage.=" Mot de passe : $mot_de_passe\n\n";
		$mailmessage.="Pour information, il vous a ete attribue un nouveau mot de passe, le précédent est perdu\n";
		
		require_once ("head.inc.php");
		if (mail ("$emaildest","$titre","$mailmessage","From: $emailexp"))
		{
			echo "<br>Un email vous a été envoyé<br>";
			echo "<a href=identification.php class=links>Suite</a>";
			exit;
		} else {
			echo "Echec de l’envoi du mail <br>";
			echo "<a href= ".htmlentities($_SERVER['PHP_SELF'])." class=links>Cliquer ici</a>";
			exit;		
		}
		include "footer.inc.php";
	}
    break;
    default:
     break;
}

}
?>
<html>
<body>
<?php require_once ("head.inc.php"); ?>
<form method="post" name="form"  action="<?php echo htmlentities($_SERVER['PHP_SELF']); ?>">
Votre adresse e-mail : 
<input type="text" name="email">
<input type=submit name=action value="Confirmer">
</form>
<?php require_once "footer.inc.php"; ?>
</body>
</html>
