<?php $clef="VOTRE CLEF"; ?>
<html>
<head>
<title>Exemple 1 avec Google Maps</title>
<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=<?php echo $clef; ?>" type="text/javascript"></script>
</head>
<body>
<div id="map" style="width: 400px; height: 300px"></div>

<script type="text/javascript">
   
//<![CDATA[
    var map = new GMap2(document.getElementById("map"));
    var point = new GLatLng(48.86, 2.3486);
    map.setCenter(point, 6);
    map.addControl(new GLargeMapControl());   
    
//]]>

</script>
</body>
</html>