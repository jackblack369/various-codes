<?php
$destinataire = "email@votresite.com";
$objet = "1er exemple" ;
$message = "Un petit message pour notre 1er exemple \n" ;
$message .= "avec fonction mail() \n";


if ( mail($destinataire, $objet, $message) ) 
	echo "Envoi du mail réussi.";
else 
	echo "Echec de l’envoi du mail."; 
?>