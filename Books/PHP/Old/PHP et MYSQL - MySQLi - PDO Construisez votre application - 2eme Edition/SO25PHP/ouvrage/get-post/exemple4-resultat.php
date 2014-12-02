<?php

if (isset($_GET['message'])) 
{
	echo "Message : ".$_GET['message']." <br />";
}
else
{
	echo "Pas de message <br />";
}
?>
