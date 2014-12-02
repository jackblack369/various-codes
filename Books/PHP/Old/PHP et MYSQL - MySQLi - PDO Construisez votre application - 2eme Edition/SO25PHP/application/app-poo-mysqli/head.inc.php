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
<?php if ($_SESSION['niveau']=="admin") { ?>
<br>
<a href=admin.php class=links>Administration</a><br>
<?php if (isset($menu) && $menu=="admin") { ?>
<a href=admin-add.php class=links2>Ajout</a><br>
<a href=admin-view.php class=links2>Consult/Modif</a><br>
<?php } ?>
<?php } ?>

<br><br>
<a href=deconnection.php class=links>Deconnection</a><br>

<?php } ?>
<hr>
<a href="contact.php" class="links">Contact</a><br>


</td><td valign=top>

