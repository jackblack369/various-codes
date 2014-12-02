<?php
$serveur = "localhost";
$user= "root";
$passwd = "";

$connex = mysql_pconnect($serveur, $user, $passwd);
$champ="Aujourd'hui, il fait beau";

echo  mysql_real_escape_string($champ);
?>
