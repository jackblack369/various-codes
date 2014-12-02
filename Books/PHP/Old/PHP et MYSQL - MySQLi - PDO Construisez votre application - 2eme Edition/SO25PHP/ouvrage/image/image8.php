<?php
header('Content-type: image/png'); 
$img = imagecreatetruecolor(320,200); 

$img = imagecreatefromjpeg("elephpant.jpg");

imagepng($img); 
imagedestroy($img); 
?>