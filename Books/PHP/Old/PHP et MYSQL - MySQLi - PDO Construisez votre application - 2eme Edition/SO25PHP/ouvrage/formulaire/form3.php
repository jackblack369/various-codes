<?php
if (sizeof($_POST) > 0) 
{
	if (empty($_POST['prenom']) && isset($_POST['prenom'])) 
	{
		$msg = "Champ obligatoire";
		if (strlen($_POST['prenom'])<1)
			$msg="Votre champ doit comporter au moins 1 caractere";		
	}
	else
	{
		$nom=htmlspecialchars($_POST['nom'], ENT_QUOTES);
		$prenom=htmlspecialchars($_POST['prenom'], ENT_QUOTES);
		echo "Bonjour : ";
		echo stripslashes($prenom);
		echo " ";
		echo stripslashes($nom);
		echo "<br />";
	}
}
?>
<html>
<body>
<form name="saisie" method="POST" action="form3.php">
Votre nom <input name="nom" type="text"><br /><br />
Votre prénom <input name="prenom" type="text">  (*) <?php if (isset($msg)) echo $msg; ?> <br /><br />
  <input name="Confirmer" type="submit" value="Confirmer">
</form>
</body>
</html>