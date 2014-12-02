<?php require_once('admin/connex.inc.php'); ?>
<?php 
include "include/fct.inc.php";

if (isset($_POST['login']) && !empty($_POST['login']) )	
{ 
$login=htmlentities($_POST['login'], ENT_QUOTES,'UTF-8');
$login=mysqli_real_escape_string($connex,$login);

$password=htmlentities($_POST['password'], ENT_QUOTES,'UTF-8');
$password=md5($password);
$password=mysqli_real_escape_string($connex,$password);

$sql="SELECT * FROM user WHERE login='$login' AND password='$password' ";
$req=mysqli_query($connex, $sql);
if(! $req ) echo ('Requête invalide : ' . mysqli_error($connex));       
if (mysqli_num_rows($req)==0)  header("Location:identification.php?erreur=login");
 

$idclef=recup_clef();
$date=DATE("Y-m-d");
$sql="UPDATE user SET idclef='$idclef',date_lastpass='$date'  WHERE login='$login' AND password='$password' ";
$query = mysqli_query($connex, $sql) OR die("Mise à jour de la clé impossible <br />". mysqli_error($connex));

$sql="SELECT * FROM user WHERE login='$login' AND password='$password'  AND idclef='$idclef' ";
$qid=mysqli_query($connex, $sql);
if(! $qid ) echo ('Requête invalide : ' . mysqli_error($connex));       
$row = mysqli_fetch_object($qid); 

        if (mysqli_num_rows($qid)!=0) 
        {
         session_start();
         $_SESSION['login'] = $row->login; 
         $_SESSION['idclef'] = $row->idclef;
         $_SESSION['niveau'] = $row->niveau;
		 $_SESSION['ip'] = $_SERVER['REMOTE_ADDR'];
         $_SESSION['iduser'] = $row->id;
		 $destination=$row->page; 
         header("Location:$destination"); 
        }
        else
        {
             header("Location:identification.php?erreur=intru"); 
        }
}
else
{
  header("Location: identification.php?erreur=login") ;
}


mysqli_free_result($qid);
mysqli_close($connex);

?>
