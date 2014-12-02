<?php
require_once("magpierss/rss_fetch.inc");

function FluxRSS($url_feed, $nb_items_affiches=5)
{
  $rss = fetch_rss($url_feed);
  if (is_array($rss->items))
  {
    $items = array_slice($rss->items, 0, $nb_items_affiches);
   $html = "<ul>\n";
   foreach ($items as $item)
   {
    $html .= "<li>";
    $html .= "<a href=\"".$item['link']."\" >";
    $html .= $item['title']."</a></li>\n";
   }
   $html .= "</ul>\n<br><br>";
   
 }
 return $html;
}

echo "PHP<br>";
echo FluxRSS("http://www.php.net/news.rss");
echo "<br>AFUP <br>";
echo FluxRSS("http://afup.org/pages/site/rss.php");

?>


