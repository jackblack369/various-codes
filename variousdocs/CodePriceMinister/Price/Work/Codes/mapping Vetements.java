#if(!$line.get(3))

	
	#if($tr.extract($line.get(5),"Chemise",true))
	chemise longue
	#end
	
	#if($tr.extract($line.get(5),"Doudoune",true))
	doudoune manches longues
	#end
	
	#if($tr.extract($line.get(5),"Ceinture",true))
	ceinture
	#end
	
	#if($tr.extract($line.get(5),"Polo",true))
	polo
	#end
	
	#if($tr.extract($line.get(5),"short",true))
	short
	#end
	
	#if($tr.extract($line.get(5),"d.bardeur[s]?|^d[eé]*bardeur[s]? ajusté[s]?$|^d[ée]*bardeur[s]? sans manche[s]?$ ",true))
	débardeur
	#end
	
	#if($tr.extract($line.get(5),"^[ ]*t[ e-]*shirt[s]?[ ]*|t-shirt",true))
	t-shirt 
	#end
	
	#if($tr.extract($line.get(5),"[ ]*jeans?[ ]*",true))
	jean
	#end
	
	#if($tr.extract($line.get(5),"pantacourt[s]?|^pantalon[s]? raccourci[s]?$|^.*pantacourt$",true))
	pantacourt
	#end
	
	#if($tr.extract($line.get(5),"pantalon[s]?|^pantalon[s]?[0-9 ]*p[ôo]che[s]?.*$",true))
	pantalon
	#end
		
	#if($tr.extract($line.get(5),"^porte[s]?[ -]*feuille[s]?.*$|^[ ]*autre.*portefeuille[ ]*$",true))
	portefeuille
	#end
	
	#if($tr.extract($line.get(5),"^pull[s]?|^pull[s]? et polo[s]?$|^[ ]*polaire[s]?[ ]*$",true))
	pull
	#end
	
	#if($tr.extract($line.get(5),"sacoche[s]?",true))
	sacoche
	#end
	
	#if($tr.extract($line.get(5),"porte[s]?.?document[s]?",true))
	porte-documents
	#end
	
	#if($tr.extract($line.get(5),"^ensemble jog[g]?ing$|jogging",true))
	jogging
	#end
	
	#if($tr.extract($line.get(5),"^[ ]*surv.tement?[ ]*",true))
	Survêtement
	#end
	
	#if($tr.extract($line.get(5),"swea.?s?[ ]*",true))
	sweat
	#end
	
	#if($tr.extract($line.get(5),"basket[s]?|chaussure[s]?[ ]*sport[s]?^baske[t]+[es]*|basket",true))
	basket
	#end
	
	#if($tr.extract($line.get(5),"^veste[s]?[ ]*(zipp[éeè]+[s]?|kaki|beige|marron gris|saharien+e[s]?)?$|^jacket[s]?$|[ ]*vestes?[ ]*",true))
	veste
	#end
	
#end