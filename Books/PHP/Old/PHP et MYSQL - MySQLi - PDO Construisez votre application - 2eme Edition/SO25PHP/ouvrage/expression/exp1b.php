<?php 
     $chaine = 'Un éditeur de qualité Editions ENI'; 

     if(preg_match("[Editions]i", $chaine)) 
     { 
          echo "Vrai<br>"; 
     } 
     else 
     { 
          echo "Faux<br>"; 
     } 

?>
