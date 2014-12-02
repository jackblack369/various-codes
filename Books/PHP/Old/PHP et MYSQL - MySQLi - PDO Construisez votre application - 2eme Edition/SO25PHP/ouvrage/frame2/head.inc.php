<table border="1" cellspacing="2" cellpadding="2" width="100%">
<tr><td width=100 valign=top>
<a href=index.php>Accueil</a><br>
<a href=carnet.php>Votre Carnet</a><br>
<?php if (isset ($menu) && $menu=="carnet") { ?>
<a href=#>Ajout</a><br>
<a href=#>Consult/Modif</a><br>
<a href=#>Recherche</a><br>
<?php } ?>

</td><td valign=top>

