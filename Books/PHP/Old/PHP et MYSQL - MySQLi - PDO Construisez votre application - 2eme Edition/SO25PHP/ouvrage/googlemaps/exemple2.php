<?php $clef="VOTRE CLEF"; ?>
<html>
<head>
<title>Exemple 2 avec Google Maps</title>
<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=<?php echo $clef; ?>" type="text/javascript"></script>
</head>
<body>
<div id="map" style="width: 500px; height: 400px"></div>

<script type="text/javascript">
   
//<![CDATA[
    var map = new GMap2(document.getElementById("map"));
    var point = new GLatLng(48.86, 2.3486);
    map.setCenter(point, 12);
    map.addControl(new GLargeMapControl());       
    
    map.addOverlay(new GMarker(point));
 
    
//]]>

</script>
</body>
</html>