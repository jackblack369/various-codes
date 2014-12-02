<?php $menu="admin"; 
require_once "include/config.inc.php";
if ($_SESSION['niveau']!="admin")	header("Location:index.php");
?>
<?php require_once "head.inc.php"; ?>

<?php
echo "Les Logs : <br>";
echo "<center>";
$fp = opendir ( $destLog );
while ( $fichier = readdir($fp) )
{
  if ( $fichier!='.' && $fichier!='..' && $fichier!='index.php' )
  {
      echo "<a href=$destLog$fichier class=links>".$fichier."<br>";
  }
}
closedir($fp) ;
echo "</center>";
require_once "footer.inc.php"; 

?>