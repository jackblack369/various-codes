<?php 


echo "</td></tr>";

echo "<tr><td colspan=2 align=center>";
echo "<i>Application - Php - Mysqli</i> - (c) Hello-Design - Les éditions ENI";
echo "</td></tr>";
echo "</table>";
?>
</body>
</html>
<?php
if (isset($active_var) && ($active_var==TRUE))
{
echo "<hr>Debug<br>";
echo "<pre>";
print_r ($_SESSION);
echo "<br>";
print_r ($_GET);
echo "<br>";
print_r ($_POST);

echo "</pre>";
}
?>