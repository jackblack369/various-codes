<?php
$connex = mysql_pconnect("localhost", "root", "");
if (!$connex) die ("Impossible de se connecter : " . mysql_error());
mysql_select_db("ouvrage",$connex);

$sql = "select titre,lien, description from `actualite` limit 5";
$qid = mysql_query($sql);

while ($row = mysql_fetch_array($qid))
{
     $return[] = $row;
}

$now = date("D, d M Y H:i:s T");

$_xml = "<?xml version=\"1.0\" ?>
<rss version=\"2.0\">
<channel>
    <title>Exemple Flux RSS</title>
    <link>http://www.hello-design.fr</link>
    <description>Le Flux RSS su site Hello pour exemple</description>
    <language>fr-fr</language>
    <pubDate>$now</pubDate>
    <lastBuildDate>$now</lastBuildDate>
";
            
foreach ($return as $line)
{
    $_xml .= "\t\t<item>";
	$_xml .= "\t\t<title>".htmlentities($line['titre'])."</title>\r\n";
    $_xml .= "\t\t<link>".htmlentities($line['lien'])."</link>\r\n";                  
	$_xml .= "\t\t<description>".htmlentities(strip_tags($line['description']))."</description>\r\n";
    $_xml .= "\t\t</item>";
}
$_xml .= "\t\t</channel>";
$_xml .= "\t\t</rss>";

file_put_contents ("ouvrage.rss",$_xml);
echo "Creation terminé";
?>
