<?php
if (sizeof($_POST) > 0) 
{

$resultat=filter_input(INPUT_POST, 'codepostal', FILTER_VALIDATE_INT); 
echo "Resultat : ".$resultat."<br>";


if(empty($_POST['codepostal']) && isset($resultat))
  echo "Champ obligatoire";
elseif($resultat === false) 
  echo "Le champ ne correspond pas au format demandé";
else
	echo  "test réussi";


}
?>
<html>
<body>
<form name="saisie" method="POST" action="filter1.php">
Code Postal <input name="codepostal" type="text"><br /><br />
 <input name="Confirmer" type="submit" value="Confirmer">
</form>
</body>
</html>