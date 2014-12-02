<?php
namespace ESPACEdeNOM;


class MaClasse
{
 	var $retour;		//retour de données
	
   function position()
   {
   		$retour ="";
   		$retour .= "Classe : ".__CLASS__."<br />";
		$retour .= "Namespace : ".__NAMESPACE__."<br />";
		$retour .= "Function : ".__FUNCTION__."<br />";
	   	return $retour;
   }	

}



?>