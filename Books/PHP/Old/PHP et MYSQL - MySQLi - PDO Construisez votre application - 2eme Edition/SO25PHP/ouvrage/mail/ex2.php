<?php

$emailexp="mail@votreemail.com";
$emaildest="email@destinataire.com";
$mailmessage = "Un petit message pour notre 2eme exemple \n" ;
$mailmessage .= "avec fonction mail() \n";
$titre="test email simple";

if (mail ("$emaildest","$titre","$mailmessage","From: $emailexp"))
	echo "Envoi du mail réussi.";
else 
	echo "Echec de l’envoi du mail."; 


?>
