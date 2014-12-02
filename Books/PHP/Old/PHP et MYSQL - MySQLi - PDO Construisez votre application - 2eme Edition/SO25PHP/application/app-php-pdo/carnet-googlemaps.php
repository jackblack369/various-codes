<?php
$menu="carnet";
require_once "include/config.inc.php";

$carnetclef = verif_GetPost($_GET['carnetclef']);


require_once('include/GoogleMapAPI.class.php');

$clef="ABQIAAAAIbuN8Mgv-QLbcJrQi8lkpBRzSlXxZPSkwHZSYnFaVh_87PJ47RT1LEJX2eVomAWSThx374GwcuRM3A";
      
$map = new GoogleMapAPI('map','map');
$map->setAPIKey($clef);
$map->setWidth("600");
$map->setHeight("400");

$map->setMapType('map');
$map->enableOverviewControl();       
  
//------------------------------------------------------------  
  
$sql="select carnet.*,user.id,user.idclef 
	 FROM carnet,user 
	 WHERE user.id=carnet.iduser 
	 	AND carnet.carnetclef='".$carnetclef."' 
	 	AND carnet.iduser='".$_SESSION['iduser']."' 
	 	AND user.idclef='".$_SESSION['idclef']."'
	 ";

assert ('$cnx->prepare($sql)');
$qid = $cnx->prepare($sql);
$qid->execute();

$row=$qid->fetch(PDO::FETCH_OBJ);
$titre="";
$adresse=$row->adresse1.", ".$row->codepostal.", ".$row->ville;
$autre=$row->nom." ".$row->prenom."<br>Tel : ".$row->tel;
$map->addMarkerByAddress($adresse,$titre,$autre);
?>
 
<html>
<head>
</head>
 
<body onload="onLoad()">
<?php require_once "head.inc.php"; ?>

<?php 
$map->printHeaderJS(); 
$map->printMapJS(); 
$map->printMap(); 
?>
<br><br>
<a href="javascript:history.go(-1)" class=links>Retour page Précédente</a>
<?php require ("footer.inc.php"); ?>
</body>
</html>


