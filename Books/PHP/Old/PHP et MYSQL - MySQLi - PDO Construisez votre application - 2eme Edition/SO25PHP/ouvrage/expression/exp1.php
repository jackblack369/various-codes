<?php 
     $chaine = 'Un éditeur de qualité Editions ENI'; 

     if(preg_match("[Editions]", $chaine)) 
     { 
          echo "Vrai<br>"; 
     } 
     else 
     { 
          echo "Faux<br>"; 
     } 

?>
