<html>
<body>
<table border="1" width="500">
    <tr>
        <td> <?php require('head.inc.php'); ?> </td>
    </tr>
    <tr>
        <td> <?php
    $i=0;
    $fin=10;
    while($i != $fin)
    {
    echo "Ligne exemple numero : ".$i."<br>";
    $i++;  
    } 
        ?> </td>
    </tr>
    <tr>
        <td> <?php require('footer.inc.php'); ?> </td>
    </tr>
</table>
</body>
</html>