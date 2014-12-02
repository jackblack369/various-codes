<?php
if (sizeof($_POST) > 0) 
{
	echo "Resultat : ".$_POST['codepostal']."<br>";
		
	if (empty($_POST['codepostal']) && isset($_POST['codepostal'])) 
	 echo "champ obligatoire";
	elseif ( !is_numeric($_POST['codepostal']) && (intval(0 + $_POST['codepostal']) == $_POST['codepostal'])) 
	{
	    echo ("Le champ ne correspond pas au format demandé");		
	} else {
		echo  "test réussi";
	}
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