<?php
if (sizeof($_POST) > 0) 
{
	$nom=htmlentities($_POST['nom'], ENT_QUOTES,'UTF-8');
	$prenom=htmlentities($_POST['prenom'], ENT_QUOTES,'UTF-8');

	if (empty($_POST['prenom']) && isset($_POST['prenom'])) 
	{
		$msg = "Champ obligatoire";
		if (strlen($_POST['prenom'])<1)
			$msg="Votre champ doit comporter au moins 1 caractere";		
	}
	else
	{
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
<form name="saisie" method="POST" action="form6.php">
Votre nom <input name="nom" type="text" value="<?php if (isset($nom)) echo addslashes($nom); ?>" /><br /><br />
Votre prénom <input name="prenom" type="text">  (*) <?php if (isset($msg)) echo $msg; ?> <br /><br />
  <input name="Confirmer" type="submit" value="Confirmer">
</form>
</body>
</html>