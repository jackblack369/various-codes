<?php require_once('admin/connex.inc.php'); ?>
<?php 
include "include/fct.inc.php";
include "include/class.class.php";

if (isset($_POST['login']) && !empty($_POST['login']) )	
{ 
$cnx = new ESPACEdeNOM\cnxBDD($serveur,$user,$passwd,$bdd);
$cnx->connect(); 


$login=htmlentities($_POST['login'], ENT_QUOTES,'UTF-8');

$password=htmlentities($_POST['password'], ENT_QUOTES,'UTF-8');
$password=md5($password);
	

$sql="SELECT COUNT(*) as nbre FROM user WHERE login='$login' AND password='$password' ";
$nbre=$cnx->requeteSelect($sql); 
if ($nbre==0)	header("Location:identification.php?erreur=login");


$idclef=recup_clef();
$date=DATE("Y-m-d");

$sql="UPDATE user SET idclef='$idclef',date_lastpass='$date'  WHERE login='$login' AND password='$password' ";
$cnx->requeteOther($sql); 

$sql="SELECT * FROM user WHERE login='$login' AND password='$password'  AND idclef='$idclef' ";
$row=$cnx->requeteSelect($sql); 

		if ($row[0]->niveau)
        {

         session_start();
         $_SESSION['login'] = $row[0]->login; 
         $_SESSION['idclef'] = $row[0]->idclef;
         $_SESSION['niveau'] = $row[0]->niveau;
		 $_SESSION['ip'] = $_SERVER['REMOTE_ADDR'];
         $_SESSION['iduser'] = $row[0]->id;
		 $destination=$row[0]->page; 
         header("Location:$destination"); 
        }
        else
        {
             header("Location:identification.php?erreur=intru"); 
        }
}
else
{
  header("Location: identification.php?erreur=login");
}

$cnx->deconnect;
unset ($cnx);
?>
