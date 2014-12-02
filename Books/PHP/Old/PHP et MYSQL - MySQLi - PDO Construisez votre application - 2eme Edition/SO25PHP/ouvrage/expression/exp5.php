<?php
$texte="[b]Editions ENI[/b]";

$cherche=array('/\[/','/\]/');
$remplace=array('<','>');

echo preg_replace ($cherche,$remplace,$texte);

?>
