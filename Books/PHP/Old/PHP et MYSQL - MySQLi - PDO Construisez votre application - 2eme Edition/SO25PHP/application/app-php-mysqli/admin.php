<?php $menu="admin"; 
require_once "include/config.inc.php";
if ($_SESSION['niveau']!="admin")	header("Location:index.php");
?>
<?php require_once "head.inc.php"; ?>
 
<html>
<body>
Sélectionner un menu de la partie Administration
</body>
</html>
<?php require_once "footer.inc.php"; ?>