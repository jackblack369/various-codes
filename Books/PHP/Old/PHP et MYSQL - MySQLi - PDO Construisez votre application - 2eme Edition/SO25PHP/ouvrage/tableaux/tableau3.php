<?php
if (sizeof($_POST) > 0) 
{
    $erreurs = array();
    $msg = array();
	if (empty($_POST['nom']) && isset($_POST['nom'])) 
	{
			$erreurs['nom'] = true;
			$msg['nom'] = "Le Champ NOM est obligatoire";		
	}
	
	if (empty($_POST['prenom']) && isset($_POST['prenom'])) 
	{
			$erreurs['prenom'] = true;
			$msg['prenom'] = "Le Champ PRENOM est obligatoire";
	}
	
    if (empty($erreurs)) 
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
<form name="saisie" method="POST" action="<?php echo $_SERVER['PHP_SELF']?>">
Votre nom <input name="nom" type="text" value="<?php if (isset($_POST['nom'])) echo stripslashes($_POST['nom']); ?>" /> (*) 
<?php if (isset($erreurs['nom'])) echo $msg['nom']; ?><br /><br />
Votre prénom <input name="prenom" type="text" value="<?php if (isset($_POST['prenom'])) echo stripslashes($_POST['prenom']); ?>" />  (*) 
<?php if (isset($erreurs['prenom'])) echo $msg['prenom']; ?> <br /><br />
  <input name="Confirmer" type="submit" value="Confirmer">
</form>
</body>
</html>