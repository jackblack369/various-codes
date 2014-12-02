<?php

function url_test($url)
{
	$curl = curl_version();
	if (!$curl['version'])
	{
	echo ("La librairie cURL est absente");
	return "";
	}

	$chemin = curl_init();
	curl_setopt($chemin, CURLOPT_URL, $url);
	curl_setopt($chemin, CURLOPT_HEADER, true);
	curl_setopt($chemin, CURLOPT_NOBODY, true);
	curl_setopt($chemin, CURLOPT_RETURNTRANSFER, true);
	curl_exec($chemin);
	$ret = curl_getinfo($chemin, CURLINFO_HTTP_CODE);
	curl_close($chemin); 
	 
	if ($ret==200) 
		return "URL oki";
	else
		return "";
}

function export($champ,$separateur='')
{
global $fp;

	fputs($fp,$champ);
	
    if ($separateur!='')
        fputs($fp,$separateur);
    else
         fputs ($fp,"\n");	
}

function datefr($ladate)
{

setlocale (LC_TIME, "fr_FR");
$date = strftime("%d-%m-%Y",strtotime($ladate));

return $date;

}


function make_seed()
{
    list($usec, $sec) = explode(' ', microtime());
    return (float) $sec + ((float) $usec * 100000);
}

function recup_clef()
{
$idcle="";
$taille = 20;
$lettres = "abcdefghijklmnopqrstuvwxyz0123456789";
srand(make_seed());
for ($i=0;$i<$taille;$i++)
{
 $idcle.=substr($lettres,(rand()%(strlen($lettres))),1);
}
return $idcle;
}

function form_controle_obligatoire($champ)
{
	if ( empty($champ) && isset($champ) ) 
		return false;
	elseif (strlen($champ)<1)
		return false;	
	elseif (intval(0 + $champ) == $champ)
		return false;
	else
		return true;
}
 
function verif_GetPost($clef='')
{
	
	if ( empty($clef) && isset($clef) ) 
		return false;
	elseif (strlen($clef)<1)
		return false;

	$clef=htmlentities($clef, ENT_QUOTES,'UTF-8');
	return $clef;
	
}
function ligne_selected($nom,$value,$entree)
{
	$resultat="<option value='".$value."' ";
	if ($value==$entree) $resultat .= " SELECTED ";
	$resultat .= ">".$nom."</option>";
	return $resultat;
}     
   

function creation_password()
{
	$alphabet = "abcdefghjkmnpqrstuvwxyz";
	$alphabet .= "ABCDEFGHJKMNPQRSTUVWXYZ";
	$alphabet .= "23456789";
	
	$nbcara = 8; 
	$i = 0;
	$pass = "";
	
	srand((double)microtime()*1000000);
	while ($i<$nbcara)
	{
	  $valcara = rand(0, strlen($alphabet));
	  $pass .= substr("$alphabet",$valcara,1);
	  $i++;
	}
	return $pass;
}

?>