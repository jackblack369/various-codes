<?php
if (sizeof($_POST) > 0) 
{
echo "Bonjour : ".$_POST['prenom']." ".$_POST['nom']."<br />";
}
?>
<html>
<body>
<form name="saisie" method="POST" action="exemple1.php">
Votre nom <input name="nom" type="text"><br /><br />
Votre prénom <input name="prenom" type="text"><br /><br />
  <input name="Confirmer" type="submit" value="Confirmer">
</form>
</body>
</html>