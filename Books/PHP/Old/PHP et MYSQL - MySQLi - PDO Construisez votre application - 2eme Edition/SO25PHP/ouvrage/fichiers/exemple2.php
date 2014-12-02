<?php
$fp = fopen("exemple.txt", "r");
while(!feof($fp)) 
{
	$variable= fgets($fp,4096);
}
echo $variable;
fclose ($fp);  
?>
