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
	
    $sql = "
    INSERT INTO exemple (
             `nom`
            , `prenom`
    ) VALUES (
            '".htmlspecialchars($_POST['nom'], ENT_QUOTES)."'
            ,'".htmlspecialchars($_POST['prenom'], ENT_QUOTES)."'
    )";

    $result = mysqli_query($connex, $sql);  
	if (!$result)  die ("Probleme :  " . mysqli_error($connex));
	else
		 	echo "Ajout...oki";

	mysqli_close($connex); 
}

?>
<html>
<body>
<form name="saisie" method="POST" action="<?php echo $_SERVER['PHP_SELF']?>">
Votre nom <input name="nom" type="text" /> <br /><br />
Votre prénom <input name="prenom" type="text" /> <br /><br />
  <input name="Confirmer" type="submit" value="Confirmer">
</form>
</body>
</html>