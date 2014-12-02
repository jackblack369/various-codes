<?php require_once "head.inc.php"; ?>
<html>
<head></head>
<body>
Votre compte : <br>
<span class="title"> Votre compte : <?php echo $login; ?></span><br><br>

<table width="500" border="0" cellspacing="2" cellpadding="2">
  <tr>
    <td align=right>Login : </td>
    <td><?php echo $row->login;?></td>
  </tr>
  <tr>
    <td align=right>Mot de Passe : </td>
    <td>
<?php for ($i=0;$i<strlen($row->password);$i++) 
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
    <td align=right>Societe : </td>
    <td><?php echo $row->societe;?></td>
  </tr>

  <tr>
    <td align=right>nom : </td>
    <td><?php echo $row->nom;?></td>
  </tr>

  <tr>
    <td align=right>Prenom : </td>
    <td><?php echo $row->prenom;?></td>
  </tr>

  <tr>
    <td align=right>Email : </td>
    <td><?php echo $row->email;?></td>
  </tr>

  <tr>
    <td align=right>Date de Création : </td>
    <td><?php echo datefr($row->date_creat);?></td>
  </tr>
  <tr>
    <td align=right>Dernier passage : </td>
    <td><?php echo datefr($row->date_lastpass);?></td>
  </tr>
  <tr>
    <td align=right>Privilege : </td>
    <td><?php echo $row->privilege;?></td>
  </tr>
  <tr>
    <td align=right>Ip machine : </td>
    <td><?php echo $row->ip;?></td>
  </tr>

</table>

</body>
<?php
require_once "footer.inc.php";
?>
