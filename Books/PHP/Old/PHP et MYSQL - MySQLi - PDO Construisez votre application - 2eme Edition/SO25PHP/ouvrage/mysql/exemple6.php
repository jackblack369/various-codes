<?php
$serveur = "localhost";
$user= "root";
$passwd = "";
$bdd = "ouvrage";

if (sizeof($_POST) > 0) 
{
	$connex = mysql_pconnect($serveur, $user, $passwd);
	if (!$connex) echo ("Impossible de se connecter : " . mysql_error());
	
	
	$sql="SELECT * FROM exemple WHERE (`nom` LIKE '%".$_POST['motclef']."%' OR `prenom` LIKE '%".$_POST['motclef']."%' ) ";
	$resultat = mysql_query($sql);
	if (!$resultat)     echo ('Requête invalide: ' . mysql_error());
	
	if (mysql_num_rows($resultat) == 0)
	{
		echo "Nous n'avons pas trouvé de résultats";
	} else {
		while($row = mysql_fetch_object($resultat) )
		{
			echo $row->nom." ".$row->prenom."<br>";		
		}
	}
    
    mysql_close($connex);   
	exit;
}
?>
<html>
<body>


<form name="fsearch" method="POST" action="<?php echo $_SERVER['PHP_SELF']?>">
Rechercher :  <input type="text" name="motclef">&nbsp;
          <input type="submit" name="action" value="Recherche">
</form>

</body>
</html>