<?php
echo "<link rel=\"stylesheet\" href=\"include/css.css\" type=\"text/css\">";
if (isset($nameSite)) echo "<title>$nameSite</title>";
?>
<table border="0"  cellspacing="3" cellpadding="3" width="100%">
<tr><td width="100" valign="top">
<a href=index.php class=links>Accueil</a><br>
<hr>
<?php if (!isset($_SESSION['dernier_passage']) || empty($_SESSION['idclef'])
		|| (time()-$_SESSION['dernier_passage']>$_SESSION['duree'])  ) { ?>
<a href=identification.php class=links>Identification</a><br>
<?php } else { ?>
<a href=compte.php class=links>Votre compte</a><br>
<br>
<a href=rubrique.php class=links>Rubriques</a><br>
<br>
<a href=carnet.php class=links>Votre Carnet</a><br>
<?php if (isset($menu) && $menu=="carnet") { ?>
<a href=carnet-add.php class=links2>Ajout</a><br>
<a href=carnet-view.php class=links2>Consult/Modif</a><br>
<a href=carnet-search.php class=links2>Recherche</a><br>
<?php } ?>
<?php if ($_SESSION['niveau']=="admin") { ?>
<br>
<a href=admin.php class=links>Administration</a><br>
<?php if (isset($menu) && $menu=="admin") { ?>
<a href=admin-add.php class=links2>Ajout</a><br>
<a href=admin-view.php class=links2>Consult/Modif</a><br>
<a href=admin-log.php class=links2>Les LOGs</a><br>
<?php } ?>
<?php } ?>
<br>
<a href=export.php class=links>Exportation</a><br>
<?php if (isset($menu) && $menu=="export") { ?>
<a href=export-csv.php class=links2>CSV</a><br>
<a href=export-pdf.php class=links2>PDF</a><br>
<a href=export-xml.php class=links2>XML</a><br>
<a href=export-json.php class=links2>json</a><br>
<?php } ?>

<br><br>
<a href=deconnection.php class=links>Deconnection</a><br>

<?php } ?>
<hr>
<a href=contact.php class=links>Contact</a><br>


</td><td valign=top>

