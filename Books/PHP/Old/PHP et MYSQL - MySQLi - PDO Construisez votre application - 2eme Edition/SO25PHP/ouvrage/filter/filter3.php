<?php
$email = "livre@exemple.com";

if(!filter_var($email, FILTER_VALIDATE_EMAIL))
 echo "Erreur format Email";
else
 echo "Email valide";

?>