<?php
$serveur = "localhost";
$user= "root";
$passwd = "";
$bdd = "ouvrage";


if (sizeof($_POST) > 0) 
{
	$connex = mysql_pconnect($serveur, $user, $passwd);
	if (!$connex) echo ("Impossible de se connecter : " . mysql_error());

    $sql = "
    INSERT INTO exemple (
             `nom`
            , `prenom`
    ) VALUES (
            '".htmlspecialchars($_POST['nom'], ENT_QUOTES)."'
            ,'".htmlspecialchars($_POST['prenom'], ENT_QUOTES)."'
    )";


    $qid = mysql_query($sql);
	if(! $qid ) 	die ('Requête invalide : ' . mysql_error());       
    
    mysql_close($connex);
    
	echo "Insertion... oki";
	exit;

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