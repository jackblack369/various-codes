<?php $menu="compte";
require_once "include/config.inc.php";
?>

<html>
<head></head>
<body>
<?php require_once "head.inc.php"; ?>
<span class="title"> Votre compte : <?php if ($_SESSION['login']) echo $_SESSION['login']; ?></span><br><br>
<?php
$cnx = new ESPACEdeNOM\cnxBDD($serveur,$user,$passwd,$bdd);
$cnx->connect(); 

$sql="select * FROM `user` WHERE idclef='".$_SESSION['idclef']."'";
$row=$cnx->requeteSelect($sql); 

?>
<table width="500" border="0" cellspacing="2" cellpadding="2">
  <tr>
    <td align=right>Login : </td>
    <td><?php echo $row[0]->login;?></td>
  </tr>
  <tr>
    <td align=right valign=top>Mot de Passe : </td>
    <td>
<?php for ($i=0;$i<strlen($row[0]->password);$i++) 
{
	echo "*";
}
?>

 </td>
  </tr>
  <tr>
    <td align=right><br></td>
    <td><br></td>
  </tr>
  <tr>
    <td align=right>Email : </td>
    <td><?php echo $row[0]->email;?></td>
  </tr>
  <tr>
    <td align=right>Date de Création : </td>
    <td><?php echo datefr($row[0]->date_creation);?></td>
  </tr>
</table>


<?php
include "footer.inc.php";
unset ($cnx);
?>
</body></html>