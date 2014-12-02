<?php $menu="carnet";
require_once "include/config.inc.php";
?>
<html>
<head></head>
<body>
<?php require_once "head.inc.php"; ?>


     <form name=fselect action="<?php echo htmlentities($_SERVER['PHP_SELF']); ?>"  method="post">
       
       <table width="200" border="0" cellspacing="0" cellpadding="0" class=petit>
          <tr> 
            <td colspan=2 align=center class=title>RECHERCHE</td>
          </tr>
          <tr> 
            <td align=center class=texte>Nom<br>Prenom<br>Email</td>
            <td><input class="formulaire1" type="text" name="mots"></td>
          </tr>
          <tr><td colspan=2 align=center><input type="submit" value="Rechercher" name="action"></td></tr>
        </table>              
   
   </form>   
<?php
if (isset($_POST['action'])) $action=verif_GetPost($_POST['action']);
if (isset($_POST['mots'])) $mots=verif_GetPost($_POST['mots']); else $mots=""; 

if (empty($mots) && isset($mots) && $mots=="") exit;

$mots=htmlentities($_POST['mots'], ENT_QUOTES,'UTF-8');
$sql="SELECT carnetclef,nom,prenom,email,tel,portable  
	FROM carnet
	WHERE (
	nom LIKE '%" . $mots . "%' 
	OR prenom LIKE '%" . $mots . "%'
	OR email LIKE '%" . $mots . "%'
	)
    AND iduser='".$_SESSION['iduser']."'
    ORDER BY nom,prenom ";

assert ('mysqli_query($connex,$sql)');
$qid = mysqli_query($connex, $sql);  
if (!$qid)  die ("Probleme :  " . mysqli_error($connex));

if (mysqli_num_rows($qid) == 0)
{
echo "Nous n'avons pas trouvé de résultats";
} else {
?>
<table border="1" width="100%">
<tr>
<td>Nom</td>
<td>Prenom</td>
<td>Email</td>
<td>Tel</td>
<td>Portable</td>
<td>Edition</td>
</tr>
<?php
while($recherche=mysqli_fetch_object( $qid) )  
{
echo "<tr>";
echo "<td>$recherche->nom";    
echo "<td>$recherche->prenom</td>";
echo "<td>$recherche->email</td>";
echo "<td>$recherche->tel</td>";
echo "<td>$recherche->portable</td>";
echo "<td><a href=carnet-view-edit.php?carnetclef=$recherche->carnetclef class=links>Cliquez ici</a></td>";
echo "</tr>";
}
mysqli_free_result($qid);
mysqli_close($connex);
?>
</table>
<?php } ?>

<?php require_once "footer.inc.php"; ?>
</body></html>