<?php 
     $chaine = 'Un éditeur de qualité Editions ENI'; 

     if (strpos($chaine,"ENI")!== FALSE && strpos($chaine,"Hello")!==FALSE) 
     { 
          echo "Vrai"; 
     } 
     else 
     { 
          echo "Faux"; 
     } 
     echo "<hr>";
 
     if(preg_match("[ENI&Hello]", $chaine)) 
     { 
          echo "Vrai"; 
     } 
     else 
     { 
          echo "Faux"; 
     } 

     
?>
