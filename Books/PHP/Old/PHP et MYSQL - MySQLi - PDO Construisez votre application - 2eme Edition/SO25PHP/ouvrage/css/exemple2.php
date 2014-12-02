<?php

if (isset($_GET['css']) && $_GET['css']=='1') 
	$fichier="css.css";
else 
	$fichier="css2.css";

echo "<link rel='stylesheet' href='$fichier' type='text/css'>";

echo "<span class=texte>";
echo "Bonjour, tout le monde";
echo "</span>";

echo "<br><br>";
echo "<a href='exemple2.php?css=1'>Police de caractère 1</a> ";
echo "<a href='exemple2.php?css=2'>Police de caractère 2</a> ";

?>
