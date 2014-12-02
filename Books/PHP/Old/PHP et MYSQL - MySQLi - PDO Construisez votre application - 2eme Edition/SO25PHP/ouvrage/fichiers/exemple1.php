<?php
$texte1="Bonjour des Editions ENI";

$fp = fopen("exemple.txt", "a");
 
fputs($fp,$texte1);                         
fclose ($fp); 
?>

