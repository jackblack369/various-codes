<?php 
header('Content-type: image/png'); 
$img = imagecreatetruecolor(320,200); 

imagepng($img); 
//imagepng($img,"image1"); 
imagedestroy($img); 
?>
