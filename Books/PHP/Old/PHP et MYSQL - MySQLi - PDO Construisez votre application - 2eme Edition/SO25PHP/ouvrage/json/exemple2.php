<?php
$json = '{"a":"Les editions ENI","b":"Christophe Villeneuve","c":"2007-2010"}';

echo "<pre>";
var_dump(json_decode($json, true));
echo "</pre>";
?>
