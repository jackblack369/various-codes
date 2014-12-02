<?php

if (!is_dir($destLog))
{
	if (!@mkdir($destLog))
	{
	    return array(false,'Erreur lors de la création du dossier $destDir');
	}
}
@chmod($destLog,0777);


$nom_fichier=DATE("Y-m-d");
$fp = fopen($destLog."".$nom_fichier.".txt", "a");  

export($_SERVER['REMOTE_ADDR'],$separateurLog);	// IP
export(DATE("Y-m-d H:i:s"),$separateurLog);	// Date et Heure
export($_SERVER['REQUEST_URI'],$separateurLog);	// nom de la page
export($_SERVER['HTTP_USER_AGENT'],$separateurLog);	// Navigateur
export($_SERVER['HTTP_REFERER']);	// provenance

fclose($fp); 

?>