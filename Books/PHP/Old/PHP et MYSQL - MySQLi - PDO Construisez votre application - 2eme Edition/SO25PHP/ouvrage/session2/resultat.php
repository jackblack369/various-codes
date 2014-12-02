<?php

session_start();

if (!isset($_SESSION['login'])) 
{
	header("Location:index.php");
	exit;
}


echo "Bienvenue dans votre espace membre : ".$_SESSION['login']."<br><br>";
echo "Votre login : ".$_SESSION['login']."<br>";
echo "Votre Mot de Passe : ".$_SESSION['password']."<br>";

echo "Direction vers la page : ".$_SESSION['page']."<br>";

session_destroy();			
unset($_SESSION);
?>