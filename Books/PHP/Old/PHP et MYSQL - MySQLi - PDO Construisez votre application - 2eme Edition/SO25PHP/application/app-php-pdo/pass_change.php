<?php $menu="compte";
require_once "include/config.inc.php";
?>

<?php
if (sizeof($_POST) > 0) 
{
$action = verif_GetPost($_POST['action']);

switch($action)
{
        case "Confirmer":
      
$oldpassword=htmlentities($_POST['oldpassword']);
$newpassword=htmlentities($_POST['newpassword']);
$newpassword2=htmlentities($_POST['newpassword2']);

$msg="";
if (empty($oldpassword)) $msg .= "Vous n'avez pas entré votre ancien mot de passe.<br>";
if (empty($newpassword))    $msg .=  "Vous n'avez pas entré de nouveau mot de passe.<br>";
if (empty($newpassword2))    $msg .= "Vous n'avez pas confirmé le nouveau mot de passe.<br>";
if ($newpassword!=$newpassword2)    $msg .= "Vos nouveaux mot de passe sont différents.<br>";

if ($msg == "")
{
$sql="select * from user where idclef='".$_SESSION['idclef']."' ";
assert ('$cnx->prepare($sql)');
$qid=$cnx->prepare($sql);
$qid->execute();
$row=$qid->fetch(PDO::FETCH_OBJ);
$memoPass=$row->password;

 if ($memoPass != md5($oldpassword))
 {
 	$msg="Votre ancien mot de passe n'est pas valide.<br>";
 }
 else
 {
	$newpassword=md5($newpassword);
	$newpassword=$cnx->quote($newpassword);

  	$sql="update user set password=$newpassword where idclef='".$_SESSION['idclef']."'";
	assert ('$cnx->prepare($sql)');
	$qid=$cnx->prepare($sql);
	if ($qid->execute())
  {
	  	require_once "head.inc.php";
	  echo "Votre nouveau mot de passe a été pris en compte.";
	  include("footer.inc.php");
		$qid->closeCursor();
		$cnx = null;
  	  exit;
  }
 }
}

        break;


     default:
     break;
}

}
?>
<html>
<head></head>
<body>
<?php require_once "head.inc.php"; ?>
<span class="title">Changer de mot de passe</span> <br><br>

<p><?php if (isset($msg)) echo $msg; ?></p>
<form name="fpassword" method="post" action="<?php echo htmlentities($_SERVER['PHP_SELF']); ?>" >
  <table width=500 border="0" align="center" cellpadding="5" cellspacing="5">
    <tr>
        
      <td class=services_form>Ancien mot de passe:</td>
        <td><input class="texte" type="password" name="oldpassword" size=25>

        </td>
</tr>
<tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
</tr>
<tr>
        
      <td class=services_form>Nouveau mot de passe:</td>
        <td><input class="texte" type="password" name="newpassword" size=25>

        </td>
</tr>
<tr>
        
      <td class=services_form>Confirmation:</td>
        <td><input class="texte" type="password" name="newpassword2" size=25>

        </td>
</tr>
<tr>
        <td></td>
        <td>
        <INPUT name="action" TYPE="SUBMIT" class="texte" value="Confirmer">
           </td>
</table>
</form>

                


<?php include("footer.inc.php"); ?>
</body></html>