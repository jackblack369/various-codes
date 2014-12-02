<?php
if (sizeof($_POST) > 0) 
{
	if ( is_numeric($_POST['codepostal']) && (intval(0 + $_POST['codepostal']) == $_POST['codepostal'])) 
	{
	    echo 'Code postal : Saisie correcte <br>';
	} else {
	    echo 'Code postal : Saisie incorrecte <br>';
	}
}
?>
<html>
<body>
<form name="saisie" method="POST" action="form4.php">
Votre code postal <input name="codepostal" type="text">  <br />
  <input name="Confirmer" type="submit" value="Confirmer">
</form>
</body>
</html>