<?php
$dirroot  = dirname(__FILE__);
$config   = "admin/connex.inc.php";

if (file_exists($config)) {  require_once $config; }


//-----------------------------
$active_var=true;				// active debuggage variable
$active_assert=1;				// Actif = 1 et Non actif=0
//-----------------------------
$taille_max="500000"; 			// Taille maxi fichier image 500 ko
$destDir = "images/";			// Dossier images
$destLog = "log/";				// Dossier des Logs
$destDL = "dl/";				// dossier de telechargement
$separateur=";";				// Séparateur pour le CVS
$separateurLog="-";				// Separateur des Logs
$duree_session=10 * 60;			// durée de la session
$nb_pages=5;					// nombre de ligne par pages
//-----------------------------


session_start();
if (!isset($_SESSION['dernier_passage'])  )
{
	session_regenerate_id();		
	$_SESSION['dernier_passage']=time(); 
	$_SESSION['duree']=$duree_session; 
	$_SESSION['ip'] = $_SERVER['REMOTE_ADDR'];
} 
else
{
	$_SESSION['dernier_passage']=time(); 
}	
if (!isset($_SESSION['idclef'])  )
{
	header("Location:index.php");
}

if (time()-$_SESSION['dernier_passage']>$_SESSION['duree']) 
{
	header("Location:identification.php?erreur=session");
}
else
{
	$_SESSION['dernier_passage'] = time() ;
}

if($_SERVER['REMOTE_ADDR'] != $_SESSION['ip'])
{
		header("Location:identification.php?erreur=session");
}

require_once "include/fct_debug.inc.php";
require_once "include/fct.inc.php";
require_once "include/fct_log.inc.php";

?>