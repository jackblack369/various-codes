<?php 
header('Content-type: image/png'); 
$img = imagecreatetruecolor(320,200); 

$blanc=imagecolorallocate($img, 255,255,255); 
    
imagestring($img, 1, 10, 10, "PHP & MySQL - MySQLi - PDO", $blanc);
imagestring($img, 2, 10, 50, "PHP & MySQL - MySQLi - PDO", $blanc);
imagestring($img, 3, 10, 80, "PHP & MySQL - MySQLi - PDO", $blanc);
imagestring($img, 4, 10, 125, "PHP & MySQL - MySQLi - PDO", $blanc);
imagestring($img, 5, 10, 180, "PHP & MySQL - MySQLi - PDO", $blanc);

imagepng($img); 
imagedestroy($img); 
?>
