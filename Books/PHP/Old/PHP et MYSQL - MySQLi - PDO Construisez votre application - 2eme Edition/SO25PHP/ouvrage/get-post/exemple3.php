<?php
if (sizeof($_GET) > 0) 
{
echo '<pre>';
print_r($_GET); 
echo '</pre>';
            
}
?>
<html>
<body>
<form name="saisie" method="GET" action="exemple3.php">
Votre nom <input name="nom" type="text"><br /><br />
Votre prénom <input name="prenom" type="text"><br /><br />
  <input name="Confirmer" type="submit" value="Confirmer">
</form>
</body>
</html>