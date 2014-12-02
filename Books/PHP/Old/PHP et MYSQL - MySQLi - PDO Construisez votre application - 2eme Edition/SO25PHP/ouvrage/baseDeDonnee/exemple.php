<?php
$serveur="localhost";
$user="root";
$password="";
$bdd="ouvrage";

$connex = mysql_pconnect($serveur, $user, $password) or trigger_error(mysql_error(),E_USER_ERROR);
mysql_select_db($bdd,$connex);

?>
