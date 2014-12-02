<?php
$email="livre@hello-design.fr";

if (!preg_match('`^[[:alnum:]]([-_.]?[[:alnum:]])*@[[:alnum:]]([-_.]?[[:alnum:]])*.([a-z]{2,4})$`',$email)) 
{
	echo "Mauvais format d'Email";
}
else
{
	echo "Format correct";
}

?>
