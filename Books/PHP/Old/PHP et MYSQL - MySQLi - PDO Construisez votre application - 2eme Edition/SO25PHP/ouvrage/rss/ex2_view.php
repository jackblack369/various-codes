<?php
$nom_fichier="ouvrage.rss";

$_xml = simplexml_load_file($nom_fichier);

foreach($_xml->channel->item as $item) 
{
	echo "* ";
	echo "<a href='".$item->link."' target='_blank'>";
	echo $item->title;
	echo "</a><br />";
	echo "&nbsp;&nbsp;".$item->description;
	echo "<br />"; 
}
?>
