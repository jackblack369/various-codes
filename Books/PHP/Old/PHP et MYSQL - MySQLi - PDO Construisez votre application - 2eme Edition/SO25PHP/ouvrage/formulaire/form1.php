<?php
if (sizeof($_POST) > 0) 
{
	if (empty($_POST['prenom']) && isset($_POST['prenom'])) 
	{
			$msg = "Champ obligatoire";
	}
	else
	{
		echo "Bonjour : ".$_POST['prenom']." ".$_POST['nom']."<br />";	
	}
}
?>
<html>
<body>
<form name="saisie" method="POST" action="form1.php">
Votre nom <input name="nom" type="text"><br /><br />
Votre prénom <input name="prenom" type="text">  (*) <?php if (isset($msg)) echo $msg; ?> <br /><br />
  <input name="Confirmer" type="submit" value="Confirmer">
</form>
</body>
</html>