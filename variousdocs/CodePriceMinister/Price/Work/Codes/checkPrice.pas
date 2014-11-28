#set($prixO=$tr.multiply($line.get(3),100,0))
#if ($prixO<=100)
1
#else
$tr.divide($prixO,100,2)
#end