<?php
function make_mms()
{
    list($usec, $sec) = explode(' ', microtime());
    return (float) $sec + ((float) $usec * 100000);
}

function recup_clef()
{
	$idcle="";
	$taille = 20;
	$lettres = "abcdefghijklmnopqrstuvwxyz0123456789";
	srand(make_mms());
	for ($i=0;$i<$taille;$i++)
	{
	 $idcle.=substr($lettres,(rand()%(strlen($lettres))),1);
	}
	return $idcle;
}


echo "Clef numero 1 : ".recup_clef()."<br>";
echo "Clef numero 2 : ".recup_clef()."<br>";
?>