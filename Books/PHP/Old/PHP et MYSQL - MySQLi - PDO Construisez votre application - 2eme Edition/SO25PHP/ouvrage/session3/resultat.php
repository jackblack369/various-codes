<?php
session_start();

if (time()-$_SESSION['dernier_passage']>$_SESSION['duree']) 
{
echo "Session expirée"; 
exit();
}
else
{
	$_SESSION['dernier_passage'] = time() ;
	echo "Session... toujours valide";
}
?>