<?php

$emailexp="mail@votreemail.com";
$emaildest="email@destinataire.com";
$mailmessage = "<html><body>";
$mailmessage .= "Les <h1>Editions ENI</h1>" ;
$mailmessage .= "vous remercie d'avoir utilisé ce 3eme exemple <br>" ;
$mailmessage .= "avec la fonction <b>mail()</b> au format HTML <br>";
$mailmessage .= "</body></html>";

$titre="test email au format HTML";

if (mail ("$emaildest","$titre","$mailmessage","From: $emailexp"))
	echo "Envoi du mail réussi.";
else 
	echo "Echec de l’envoi du mail."; 


?>
