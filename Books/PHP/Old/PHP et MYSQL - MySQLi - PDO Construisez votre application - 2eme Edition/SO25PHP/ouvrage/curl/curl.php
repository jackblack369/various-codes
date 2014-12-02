<?php
$curl=curl_version();
if (!$curl['version']) die ("La librairie cURL est absente. Merci de l'activer dans php.ini");

$chemin = curl_init("http://www.php.net");
curl_setopt($chemin, CURLOPT_RETURNTRANSFER, true);
$data = curl_exec($chemin);

if(curl_errno($chemin))
    echo 'Erreur Curl : ' . curl_error($chemin);

curl_close($chemin);

print_r($data);
?>
