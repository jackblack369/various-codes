<?php 
header('Content-type: image/png'); 
$img = imagecreatetruecolor(320,200); 


$col=imagecolorallocate($img, 255,0,0); 
imageline($img,10,10,400,150, $col);      

imagepng($img); 
imagedestroy($img); 
?>
