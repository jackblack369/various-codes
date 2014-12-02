<?php
$serveur = "localhost";
$user    = "root";
$passwd  = "";
$bdd     = "eni";

$connex = mysql_pconnect($serveur, $user, $passwd) or trigger_error(mysql_error(),E_USER_ERROR);
mysql_select_db($bdd,$connex);

?>