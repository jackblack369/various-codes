<?php 
header('Content-type: image/png'); 
$img = imagecreatetruecolor(320,200); 


$col=imagecolorallocate($img, 255,0,0); 
imageellipse($img, 150, 100, 50,50, $col);
    

imagepng($img); 
imagedestroy($img); 
?>
