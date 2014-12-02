<?php
echo "DNS: ".gethostbyaddr($_SERVER['SERVER_ADDR'])."<br>";
echo "Serveur ip : ".$_SERVER['SERVER_ADDR']."<br>";
echo "adresse ip visiteur: ".$_SERVER['REMOTE_ADDR'] ."<br>";
echo "adresse de la page : ".$_SERVER['REQUEST_URI']."<br>"; 
echo "chemin complet du script: ".$_SERVER['PATH_TRANSLATED'] ."<br>";
echo "navigateur: ".$_SERVER['HTTP_USER_AGENT'] ."<br>";
echo "nom de domane: ".$_SERVER['HTTP_HOST'] ."<br>";
echo "langage: ".$_SERVER['HTTP_ACCEPT_LANGUAGE'] ."<br>";
echo "DOCUMENT_ROOT : ".$_SERVER['DOCUMENT_ROOT']."<br>";
echo "Protocol : ".$_SERVER['SERVER_PROTOCOL']."<br>";
echo "Accept : ".$_SERVER['HTTP_ACCEPT']."<br>";
?>
