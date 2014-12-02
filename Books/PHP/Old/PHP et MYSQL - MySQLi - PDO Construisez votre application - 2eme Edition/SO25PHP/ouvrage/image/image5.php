<?php 
header('Content-type: image/png'); 
$img = imagecreatetruecolor(320,200); 

$vert=imagecolorallocate($img, 0,255,0); 
$bleu=imagecolorallocate($img, 0,0,255); 
    
imagefilledrectangle($img,200,150,231,180, $vert);      
imagefilledellipse($img, 150, 100, 50,50, $bleu);  

imagepng($img); 
imagedestroy($img); 
?>
