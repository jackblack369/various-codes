<?php $clef="VOTRE CLEF"; ?>
<html>
<head>
<title>Exemple 3 avec Google Maps</title>
<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=<?php echo $clef; ?>" type="text/javascript"></script>
</head>
<body>
<div id="map" style="width: 500px; height: 400px"></div>

<script type="text/javascript">
   
//<![CDATA[
    var map = new GMap2(document.getElementById("map"));
    var point = new GLatLng(48.86, 2.3486);
    map.setCenter(point, 14,G_SATELLITE_MAP);
    map.addControl(new GLargeMapControl());   

    map.openInfoWindow(point, document.createTextNode("Nous nous trouvons ici"));
    
//]]>

</script>
</body>
</html>