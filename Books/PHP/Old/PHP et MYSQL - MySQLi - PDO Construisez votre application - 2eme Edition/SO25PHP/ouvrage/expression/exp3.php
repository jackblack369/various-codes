<?php 
$chaine = 'Un éditeur de qualité Editions ENI'; 

   echo fct_preg_match("#Edition#",$chaine)."<br>";
   echo fct_preg_match("#edition#",$chaine)."<br>";
   echo fct_preg_match("#edition#i",$chaine)."<br>";
   echo fct_preg_match("#Edition|ENI#",$chaine)."<br>";
   echo fct_preg_match("#^Un#",$chaine)."<br>";
   echo fct_preg_match("#Edition$#",$chaine)."<br>";
   echo fct_preg_match("#[a-z]#",$chaine)."<br>";
     
   echo fct_preg_match("#[0-9]#","01 23 45 67 89")."<br>";
   echo fct_preg_match("#[a-z]#","01 23 45 67 89")."<br>";
   echo fct_preg_match("#[a-z]#","http://hello-design.fr/")."<br>";
$chaine = '1 éditeur de qualité Edition ENI'; 
   echo fct_preg_match("#([a-z]{2,4})#",$chaine)."<br>";



function fct_preg_match($critere,$chaine)
{
	
     if(preg_match($critere, $chaine)) 
     { 
          echo $chaine."...Vrai<br>"; 
     } 
     else 
     { 
          echo $chaine."...Faux<br>"; 
     } 
     
}
     
?>
