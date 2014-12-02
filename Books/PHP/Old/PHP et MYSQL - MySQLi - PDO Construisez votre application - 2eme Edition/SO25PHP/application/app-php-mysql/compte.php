<?php $menu="compte";
require_once "include/config.inc.php";
?>

<html>
<head></head>
<body>
<?php require_once "head.inc.php"; ?>
<span class="title"> Votre compte : <?php if ($_SESSION['login']) echo $_SESSION['login']; ?></span><br><br>
<?php
$sql="select * FROM `user` WHERE idclef='".$_SESSION['idclef']."'";
$val=mysql_query($sql);
if (!$val)    echo ('Requête invalide : ' . mysql_error());

$row=mysql_fetch_object( $val);

?>
<table width="500" border="0" cellspacing="2" cellpadding="2">
  <tr>
    <td align=right>Login : </td>
    <td><?php echo $row->login;?></td>
  </tr>
  <tr>
    <td align=right valign=top>Mot de Passe : </td>
    <td>
<?php for ($i=0;$i<strlen($row->password);$i++) 
{
	echo "*";
}
?>
<br>
<a href="pass_change.php" class="links">Changer de mot de passe</a>

 </td>
  </tr>
  <tr>
    <td align=right><br></td>
    <td><br></td>
  </tr>
  <tr>
    <td align=right>Email : </td>
    <td><?php echo $row->email;?></td>
  </tr>
  <tr>
    <td align=right>Date de Création : </td>
    <td><?php echo datefr($row->date_creation);?></td>
  </tr>
</table>


<?php
include "footer.inc.php";
?>
</body></html>