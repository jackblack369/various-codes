<html>
<head>
<title>2eme exemple</title>
</head>
<body>
<?php
echo "Bonjour, tout le monde<br />";

if (isset($_GET['action']) && $_GET['action']=="suite")
{
	echo "Bravo... ";
	echo "<a href=html2.php?action=recommence>recommencer</a>";	
}

if ( (isset($_GET['action']) && $_GET['action']=="recommence") 
	  || empty($_GET['action'])
	)
{
	echo "Etape 1... ";
	echo "<a href=html2.php?action=suite>Suite</a>";
} 
?>
</body>
</html>