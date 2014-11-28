<?php
/*
if ($_FILES["file"]["error"] > 0)
  {
  echo "Error: " . $_FILES["file"]["error"] . "<br />";
  }
else
  {
  echo "Upload: " . $_FILES["file"]["name"] . "<br />";
  echo "Type: " . $_FILES["file"]["type"] . "<br />";
  echo "Size: " . ($_FILES["file"]["size"] / 1024) . " Kb<br />";
  echo "Stored in: " . $_FILES["file"]["tmp_name"];
  
  }

 */ 
  //début analyse du fichier
  
 
// Read the file into an array
$myfile=$_FILES["file"]["tmp_name"];
  // Read the file into an array
$users = file($myfile);

foreach ($users as $user) {
/*
list($name)=explode(";",$line);

print($name."\n");*/
echo(html_entity_decode($user));


}
  
  
  
  
  
  
  //echo var_dump($_FILES);
?>