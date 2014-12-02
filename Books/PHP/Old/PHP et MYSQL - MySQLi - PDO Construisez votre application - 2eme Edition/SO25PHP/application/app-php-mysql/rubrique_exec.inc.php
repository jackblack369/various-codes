<html><body>
<form action="<?php echo htmlentities($_SERVER['PHP_SELF']); ?>"  method="post">
Veuillez saisir votre mot de passe pour confirmer la suppression de la ligne : <br>
<input name="idclef" type="hidden" value=<?php echo $_SESSION['idclef'];?> ><br>
<input name="id" type="hidden" value=<?php echo $id;?> ><br>
<input type="password" name="pass" size=20>  
<input type="submit" name="action" value="Confirmer"> 
</form>


</body></html>
