<?php
session_start();

$_SESSION['dernier_passage'] = time(); 
$_SESSION['duree'] = get_cfg_var("session.gc_maxlifetime"); 

echo "<a href='resultat.php'>Verifier la duree</a>"
?>
