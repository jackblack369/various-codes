<?php
$destDir = "upload/";
$taille_max="500000"; //500 Ko


if (sizeof($_POST) > 0 && $_FILES['fichier'] && $_FILES['fichier'] != "none") 
{
if (!is_dir($destDir))
{
	if (!@mkdir($destDir))
	{
		echo "Erreur lors de la création du dossier $destDir";
	}
}
@chmod($destDir,0777);


// Test fichier transfert
if (!file_exists($_FILES['fichier']['tmp_name']))
{
   die ("Le fichier n'est pas passé. Vérifier les critères");
}


// Test taille du fichier
$taille_max=$_POST['MAX_FILE_SIZE'];
$taille_fichier = filesize($_FILES['fichier']['tmp_name']);
if  ($taille_max && ($taille_fichier > $taille_max))
{
       die ("La taille est trop importante");
}

   

// Test l'extension
$ext = strrchr($_FILES['fichier']['name'], '.');
$ext = substr($ext, 1);
$ext = strtolower($ext);
if ($ext!="jpg" && $ext!="jpeg" && $ext!="png" && $ext!="gif" )  
{
    die("Le fichier n'est pas une image");
}


// Test les caractères
$fichier_destination = strtr($_FILES[fichier]['name'],
			'ÀÁÂÃÄÅÇÈÉÊËÌÍÎÏÒÓÔÕÖÙÚÛÜÝàáâãäåçèéêëìíîïðòóôõöùúûüýÿ',
			'AAAAAACEEEEIIIIOOOOOUUUUYaaaaaaceeeeiiiioooooouuuuyy');

$fichier_destination = preg_replace(
         '/[^a-zA-Z0-9\.\$\%\'\`\-\@\{\}\~\!\#\(\)\&\_\^]/'
         ,'',str_replace(array(' ','%20'),array('_','_'),$fichier_destination));

$fichier_destination=strtolower($fichier_destination);

// Deplace le fichier
if (move_uploaded_file($_FILES['fichier']['tmp_name'], $destDir.$fichier_destination))
{
	die ("Le fichier est correctement passé");
}
else
{     
	echo "Probleme de transfert";
}


}




?> 