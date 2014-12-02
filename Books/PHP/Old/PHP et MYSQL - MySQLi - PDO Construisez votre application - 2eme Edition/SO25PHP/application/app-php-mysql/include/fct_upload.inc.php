<?php


function upload($dossierDestination,$fieldName)
{
GLOBAL $destDir,$taille_max;

if (!is_dir($destDir))
{
	if (!@mkdir($destDir))
	{
	    return array(false,'Erreur lors de la création du dossier $destDir');
	}
}
@chmod($destDir,0777);

if (!file_exists($_FILES['fichier']['tmp_name']))
{
    return array(false,'Le fichier n\'est pas passé. Vérifier les critères');
}


$taille_max=$_POST['MAX_FILE_SIZE'];
$taille_fichier = filesize($_FILES['fichier']['tmp_name']);
if  ($taille_max && ($taille_fichier > $taille_max))
{
    return array(false,'La taille est trop importante');
}


$ext = strrchr($_FILES['fichier']['name'], '.');
$ext = substr($ext, 1);
$ext = strtolower($ext);
if ($ext!="jpg" && $ext!="jpeg" && $ext!="png" && $ext!="gif" )  
{
    return array(false,'Le fichier n\'est pas une image');
}


$fichier_destination = strtr($_FILES['fichier']['name'],
			'ÀÁÂÃÄÅÇÈÉÊËÌÍÎÏÒÓÔÕÖÙÚÛÜÝàáâãäåçèéêëìíîïðòóôõöùúûüýÿ',
			'AAAAAACEEEEIIIIOOOOOUUUUYaaaaaaceeeeiiiioooooouuuuyy');

$fichier_destination = preg_replace(
         '/[^a-zA-Z0-9\.\$\%\'\`\-\@\{\}\~\!\#\(\)\&\_\^]/'
         ,'',str_replace(array(' ','%20'),array('_','_'),$fichier_destination));

$fichier_destination=strtolower($fichier_destination);

if (move_uploaded_file($_FILES['fichier']['tmp_name'], $destDir.$fichier_destination))
{
    return array(true,$fichier_destination);
}
else
{     
    return array(false,'Probleme de transfert');
}
	
}



function rename_fichier($newName,$fichier)
{
Global $destDir;
	
$ext = strrchr($fichier, '.');
$newName = strtolower($newName);

if ( !file_exists($destDir.$newName) )
{
	$i=0;
	while ( file_exists($destDir.$newName.$ext) )
	{
		$i++;
		if (file_exists($destDir.$newName.$ext))	
		{
			$decoupe = explode('_',$newName);
			$newName = $decoupe[0]."_".$i;	
		}
		else
			break;
	}
}

rename ($destDir.$fichier,$destDir.$newName.$ext); 

return $newName.$ext;
}

?>
