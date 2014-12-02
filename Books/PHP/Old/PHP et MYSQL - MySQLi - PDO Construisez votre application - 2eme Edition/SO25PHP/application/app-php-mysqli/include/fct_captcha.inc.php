<?php
session_start();
//-----------------------------
$largCaptcha=130;				// largeur captcha 
$hautCaptcha=65;				// hauteur captcha
$longChaineCaptcha=6;			// nombre de caractères pour le captcha
$fontTTF="Swiss 721 BT.ttf";	// police de caractères pour le captcha
//-----------------------------

function chaine_captcha($longueur)
{
	$md5 = md5(microtime() * mktime());
	$chaine = substr($md5,0,$longueur);
	return $chaine;
}

$_SESSION['textCaptcha']=chaine_captcha($longChaineCaptcha);

header('Content-type: image/png');
$img = imagecreatetruecolor($largCaptcha,$hautCaptcha);


$fondCol = imagecolorallocate($img, 255,255,255);
imagefilledrectangle($img,0,0,$largCaptcha,$hautCaptcha,$fondCol);

$position=0;
for($ligne=-30;$ligne<$largCaptcha+30;$ligne+=10)
{
	$color = imagecolorallocate($img, rand(80,250), rand(18,250), rand(100,250));
	
	if ($position==0)
	{
		imageline($img,$ligne,rand(1,10),$largCaptcha-rand(20,60),$hautCaptcha, $color);                     
		imageline($img,$largCaptcha-$ligne,5,rand($hautCaptcha-10,$hautCaptcha+10),$largCaptcha, $color);                     
		$position=1;
	}
	else
	{
		imageline($img,$ligne,5,rand(10,30),$hautCaptcha, $color);                     
		imageline($img,$largCaptcha-$ligne,rand(5,20),$hautCaptcha-rand($hautCaptcha-10,$hautCaptcha+10),$largCaptcha, $color);                     
		$position=0;
	}
} 


$x=rand(4,35);

for($c=0;$c<$longChaineCaptcha;$c++)
{
	$angle=mt_rand(10, 50);
	$size=mt_rand(14, 36);
	$text=$_SESSION['textCaptcha'][$c];
	$y=30+rand(1,40);

	$color=imagecolorallocate($img, rand(10,99),rand(10,99),rand(10,99)); 
	imagettftext($img, $size, $angle, $x+18*$c, $y, $color, $fontTTF, $text);
}

imagepng($img);
imagedestroy($img);
?>