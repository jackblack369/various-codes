<?php
$serveur = "localhost";
$user= "root";
$passwd = "";
$bdd = "ouvrage";

if (sizeof($_POST) > 0) 
{
	$connex = mysqli_connect($serveur, $user, $passwd, $bdd);
	
	if (mysqli_connect_errno()) 
	    die ("Echec de la connexion : ". mysqli_connect_error());
	
	$sql="SELECT * FROM exemple WHERE (`nom` LIKE '%".$_POST['motclef']."%' OR `prenom` LIKE '%".$_POST['motclef']."%' ) ";

    $result = mysqli_query($connex, $sql);  
	if (!$result)  die ("Probleme :  " . mysqli_error($connex));

	if (mysqli_num_rows($result) == 0)
	{
		echo "Nous n'avons pas trouvé de résultats";
	} else {
		while( $row=mysqli_fetch_object($result) )       

		{
			echo $row->nom." ".$row->prenom."<br>";
		}
	}
	mysqli_free_result($result);		
	mysqli_close($connex);
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