<?php
if (sizeof($_POST) > 0) 
{

$email=htmlentities($_POST['email']);
if (!preg_match('`^[[:alnum:]]([-_.]?[[:alnum:]])*@[[:alnum:]]([-_.]?[[:alnum:]])*.([a-z]{2,4})$`',$email)) 
{
    echo 'Votre email est incorrect !';
} else {
    echo 'Votre email est correct !';
}
}
?>
<html>
<body>
<form name="saisie" method="POST" action="form5.php">
Votre email <input name="email" type="text">  <br />
  <input name="Confirmer" type="submit" value="Confirmer">
</form>
</body>
</html>