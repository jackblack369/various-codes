<?php
//$url="http://hellodesign.free.fr/eni/";

session_start();
unset($_SESSION);
session_destroy();

header("Location: index.php");


?>