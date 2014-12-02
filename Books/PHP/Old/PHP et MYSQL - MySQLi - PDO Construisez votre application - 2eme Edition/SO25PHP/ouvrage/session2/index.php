<?php 
include "admin/connex.inc.php";

session_start(); 

if (sizeof($_POST) > 0)		
{
	if (isset($_POST['login']))
	{ 
        $login=addslashes($_POST['login']);
        $password=MD5($_POST['password']);

		$sql="SELECT * FROM `user` WHERE login='$login' AND password='$password' ";
		$req=mysqli_query($connex,$sql);
		if(! $req ) echo ('Requête invalide : ' . mysql_error());       
        if (mysqli_num_rows($req))  
        {
		 $row = mysqli_fetch_object($req);
		 
         $_SESSION['login'] = $row->login;
         $_SESSION['password'] = $row->password;
         $_SESSION['page'] = $row->page;

         header("Location:resultat.php"); 
        }
        else
        	echo "Login ou mot de passe invalide";
	}
    else
    {
 		echo "erreur";
    }
}
?>
<html><body>
<center>

<form name="saisie" method="POST" action="index.php">
<table cellpadding="5"  cellspacing="5" border=0 >
    <tr>
      <td align=center>
login : <input type="text" name="login">
  <br>
       <br>
      Mot de passe : <input type="password" name="password" size=20>
<br>
        <div align="center">
          <input type="submit" name="action" value="Confirmer">
        </div>
</td></tr>
   </table>
</form>
</center>
</body></html>
