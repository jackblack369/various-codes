<?php session_start(); ?>
<html>
<head></head>
<body>
<?php require_once "head.inc.php"; ?>
<center>
<form name="identif" method="POST" action="login.inc.php">
<table cellpadding="5"  cellspacing="5" border=0 >
    <tr>
      <td align=center>
Login<br>
         <input type="text" name="login">
  <br>
       <br>
      Mot de passe<br>
          <input type="password" name="password" size=20>     

  <p align="center"><b>
      <?php 
      if(isset($_GET['erreur']) && ($_GET['erreur'] == "login")) 
      {
      	echo "login ou mot de passe incorrect";
      }
      if(isset($_GET['erreur']) && ($_GET['erreur'] == "intru")) 
      {
      	echo "Echec d'identification !!!";
      }
      if(isset($_GET['erreur']) && ($_GET['erreur'] == "session")) 
      {
      	echo "Session expirée";
      }
      ?>
      </b></p>


        <div align="center">
          <input type="submit" name="action" value="OK"><br>
        admin - admin<br>test - test <br>
          
        </div>

</td></tr>
   </table>
</form>
</center>

<?php
require_once "footer.inc.php";
?>
</body></html>