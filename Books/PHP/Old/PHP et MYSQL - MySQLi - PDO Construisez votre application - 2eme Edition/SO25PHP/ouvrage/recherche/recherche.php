<?php
$serveur = "localhost";
$user= "root";
$passwd = "";
$bdd = "ouvrage";

$connex = mysql_pconnect($serveur, $user, $passwd);
if (!$connex) echo ("Impossible de se connecter : " . mysql_error());

mysql_select_db($bdd,$connex);

?>
<html>
<head></head>
<body>

     <form name=fselect action="<?php echo $_SERVER['PHP_SELF']; ?>"  method="post">
       RECHERCHER un nom :<br><input type="text" name="mots">
       <input type="submit" value="Rechercher" name="action">
   </form>   
<br><br>
<?php
if (!empty($_POST['action'])) $action=$_POST['action'];
if (!empty($_POST['mots'])) $mots=$_POST['mots']; else $mots="";

if ($mots=="") exit;

$sql="SELECT nom,prenom	FROM exemple WHERE nom LIKE '%" . $mots . "%' ";

$qid = mysql_query($sql);  
if (!$qid)  die ("Probleme :  " . mysql_error());

if (mysql_num_rows($qid) == 0)
{
echo "Nous n'avons pas trouvé de résultats";
} else {

while($recherche=mysql_fetch_object( $qid) )  
{
echo "$recherche->nom ";    
echo "$recherche->prenom";
echo "<br>";
}
mysql_free_result($qid);
mysql_close();
} ?>

</body></html>