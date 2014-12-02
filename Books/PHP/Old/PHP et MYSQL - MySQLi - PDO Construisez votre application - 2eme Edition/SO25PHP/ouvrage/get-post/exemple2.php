<?php
if (sizeof($_GET) > 0) 
{
echo "Bonjour : ".$_GET['prenom']." ".$_GET['nom']."<br />";          
}
?>
<html>
<body>
<form name="saisie" method="GET" action="exemple2.php">
Votre nom <input name="nom" type="text"><br /><br />
Votre prénom <input name="prenom" type="text"><br /><br />
  <input name="Confirmer" type="submit" value="Confirmer">
</form>
</body>
</html>