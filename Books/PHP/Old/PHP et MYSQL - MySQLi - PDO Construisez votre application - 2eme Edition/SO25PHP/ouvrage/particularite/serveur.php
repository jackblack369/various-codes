<?php
echo "Hote : ".$_SERVER['HTTP_POST']."<br>";
echo "Provenance : ".$_SERVER['HTTP_REFERER']."<br>";
echo "Racine du serveur : ".$_SERVER['DOCUMENT_ROOT']."<br>";
echo "Paramètres : ".$_SERVER['QUERY_STRING']."<br>";
echo "Methode : ".$_SERVER['REQUEST_METHOD']."<br>";
echo "IP : ".$_SERVER['REMOTE_ADDR']."<br>";
echo "Chemin : ".$_SERVER['REQUEST_URI']."<br>";
?>
