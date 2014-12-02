<?php
if (sizeof($_POST) > 0) 
{
	if (strlen($_POST['prenom'])<1) 
	{
			$msg = "Le champ doit contenir au moins 1 caractere";
	}
	else
	{
		echo "Bonjour : ".$_POST['prenom']." ".$_POST['nom']."<br />";	
	}
}
?>
<html>
<body>
<form name="saisie" method="POST" action="form2.php">
Votre nom <input name="nom" type="text"><br /><br />
Votre prénom <input name="prenom" type="text">  (*) <?php if (isset($msg)) echo $msg; ?> <br /><br />
  <input name="Confirmer" type="submit" value="Confirmer">
</form>
</body>
</html>