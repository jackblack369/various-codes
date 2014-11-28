#if(!$line.get("presence_produit_ean"))
	#if($line.get(18) && $!tr.extract($line.get(18),"([0-9]+)[ ]*(gramme[s]?|gr?[.]?|g)","[1]",true))
	
	#set($poids = $!tr.extract($line.get(18),"([0-9]+)[ ]*(gramme[s]?|gr?[.]?|g)","[1]",true))
	
		#if($tr.toint($poids) > 0)
		#set($poids = $tr.toint($poids))
		#set($poids =$!tr.extract($poids,"[0-9]+"))
		$!poids
		#end
		
	#elseif($line.get(18) && $!tr.extract($line.get(18),"([0-9,.]+)[ ]*(Kilogramme[s]?|kg?[.]?)","[1]",true))
	
	#set($poids=$!tr.extract($line.get(18),"([0-9,.]+)[ ]*(Kilogramme[s]?|kg?[.]?)","[1]",true))
		
	#set($poids = $tr.multiply($poids,1000))
    #set($poids =$!tr.extract($poids,"[0-9]+"))
	$!poids
		
	#end
	
#end


