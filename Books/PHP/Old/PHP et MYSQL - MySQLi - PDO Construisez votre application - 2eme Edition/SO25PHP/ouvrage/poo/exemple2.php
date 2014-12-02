<?php

class MaClasse
{
  var $nom;

function __construct($nom)
{
	$this->nom=$nom;
	echo "constructeur : ".$this->nom."<br>";	
}
  
}

$MonObject=new MaClasse('Les éditions ENI');
echo $MonObject->nom."<br />";
unset ($MonObject);

?>