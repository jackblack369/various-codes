<?php
//$expr = '/(\d{4})-(\d{2})-(\d{2})/';
//$item = 'Event date: 2011-05-01';
//$matches=array();
//
//if (preg_match($expr, $item,$matches)) {
//    
//    foreach(range(0,count($matches)-1) as $i) {
//        printf("%d:-->%s\n",$i,$matches[$i]);
//    }
//    
//    list($year,$month,$day)=array_splice($matches,1,3);
//    print "Year:$year Month:$month Day:$day\n";
//    
//} else {
//    
//    print "Doesn't match.\n";
//}

//for ($i = 0 ; $i <100 ; $i++){
//    if( !($i%3) ){
//        echo "i : $i","->boo","<br>";
//    }
//    
//    if( !($i%5) ){
//        echo "i : $i","->baa","<br>";
//    }
//}

function fuckPhp($a){
    $a++;
    
}

$b = 1;
fuckPhp(&$b);
echo $b;
        

?>
