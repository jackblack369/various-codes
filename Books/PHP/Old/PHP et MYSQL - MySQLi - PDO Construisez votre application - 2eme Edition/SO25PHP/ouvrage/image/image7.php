<?php 
header('Content-type: image/png'); 
$img = imagecreatetruecolor(320,200); 

$blanc=imagecolorallocate($img, 255,255,255); 
    
imagettftext($img, 22, 0, 10, 90, $blanc, "Swiss 721 BT.ttf", "Les Editions ENI");

imagepng($img); 
imagedestroy($img); 
?>
