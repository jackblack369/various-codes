<?php require_once('admin/connex.inc.php'); ?>
<?php 
include "include/fct.inc.php";

if (isset($_POST['login']) && !empty($_POST['login']) )	
{ 
$login=htmlentities($_POST['login'], ENT_QUOTES,'UTF-8');
$login=$cnx->quote($login);

$password=htmlentities($_POST['password'], ENT_QUOTES,'UTF-8');
$password=md5($password);
$password=$cnx->quote($password);

$sql="SELECT COUNT(*) FROM user WHERE login=$login AND password=$password ";
assert ('$cnx->prepare($sql)');
$qid = $cnx->prepare($sql);
$qid->execute();
$nbre = $qid->fetchColumn();
if ($nbre==0) header("Location:identification.php?erreur=login");



$idclef=recup_clef();
$date=DATE("Y-m-d");

$sql="UPDATE user SET idclef='$idclef',date_lastpass='$date'  WHERE login=$login AND password=$password ";
assert ('$cnx->prepare($sql)');
$qid=$cnx->prepare($sql);
$qid->execute();

$sql="SELECT * FROM user WHERE login=$login AND password=$password  AND idclef='$idclef' ";
assert ('$cnx->prepare($sql)');
$qid=$cnx->prepare($sql);
$qid->execute();
$row=$qid->fetch(PDO::FETCH_OBJ);
$nbre = $qid->fetchColumn();
		if ($qid->rowCount()!=0)
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

$qid->closeCursor();
$cnx = null;

?>
