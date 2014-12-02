<?php
session_start();
$file=$_SESSION['login'].".csv";


if (!isset($_SESSION['dernier_passage'])  )
{
	session_regenerate_id();		
} 
	
if (!isset($_SESSION['login'])  )
{
	header("Location:index.php");
}

header('Content-type: application/csv');
header('Content-Disposition: attachment; filename='.basename($file));
header('Accept-Ranges: bytes');
header('Content-Length: '.filesize($file) );
readfile($file);
unlink($file);


?>