<?php
function tableau($valeur)
{
	if ($valeur=="1")
    	return array(TRUE,"Retour Vrai");
	else
   		return array(FALSE,"Retour Faux");
}

$rep=tableau(1);
echo $rep[0]."-".$rep[1]."<br>";

$rep=tableau(2);
echo $rep[0]."-".$rep[1]."<br>";
?>