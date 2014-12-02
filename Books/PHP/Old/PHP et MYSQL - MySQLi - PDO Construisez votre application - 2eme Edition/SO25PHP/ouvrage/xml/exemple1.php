<?php
 $_xml ="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n";
 $_xml .="<racine>\r\n";
 $_xml .="<page title=\"exemple TITRE\">\r\n";
 $_xml .="<menu>\"Menu test\"</menu>\r\n";
 $_xml .="</page>\r\n";

 $_xml .="<ligne>\r\n";
 $_xml .="<titre>Editions</titre>\r\n";
 $_xml .="<soustitre>ENI</soustitre>\r\n";
 $_xml .="</ligne>\r\n";
 $_xml .="</racine>\r\n";

header("Content-type: text/xml");
echo $_xml;

$file = fopen("exemple1.xml", "w");
fwrite($file, $_xml); 
fclose($file);
?>