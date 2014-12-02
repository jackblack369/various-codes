<?php 
header('Content-type: image/png'); 
$img = imagecreatetruecolor(320,200); 


$col=imagecolorallocate($img, 255,0,0); 
imagerectangle($img,10,10,310,180, $col);      

imagepng($img); 
imagedestroy($img); 
?>
