<?php
if (isset($_POST['err'])||!empty($_POST['err']))     $err = $_POST['err'];
$url="http://www.notre_site.com";

switch($err)
{
case "401";
	$msg  = "Authetification nécessaire<br>";
    $msg .= "Votre IP : ".$_SERVER['REMOTE_ADDR']."<br>";
break;

case "403":
	$msg  = "La page ".$_SERVER['REQUEST_URI']." est en accès interdit<br>";
    $msg .= "Votre IP : ".$_SERVER['REMOTE_ADDR']."<br>";
	$msg .= "<ADDRESS>Apache/1.3.33 Server at ".$_SERVER['HTTP_HOST']." Port 80</ADDRESS>";
break;

case "404";
$msg  = "Erreur<br>";
$msg .= "La page ".$_SERVER['REQUEST_URI']." demandée n'existe pas<br>";
$msg .= "Votre IP : ".$_SERVER['REMOTE_ADDR']."<br>";
break;

case "500";
$msg  = "Erreur interne du serveur<br>";
$msg .= "Votre IP : ".$_SERVER['REMOTE_ADDR']."<br>";

break;
}
?>
<html>
<head></head>
<body>
<?php echo $msg."<br>"; ?>
<br>
Retour sur le site :  <a href="<?php echo $url; ?>"><?php echo $url; ?></a>
</body></html>