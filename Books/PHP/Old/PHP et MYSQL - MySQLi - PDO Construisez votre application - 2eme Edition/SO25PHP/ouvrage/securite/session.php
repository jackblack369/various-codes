<?php
session_start();
if (!isset($_SESSION['initialisation']) )
{
	session_regenerate_id();
	$_SESSION['initialisation']=time();
}

?>
