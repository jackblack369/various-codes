#if($line.get(3))

	
	#if($tr.extract($line.get(3),"[0-9]+[ ]*g"))
		#set($poids = $tr.extract($line.get(3),"[0-9]*{1}g"))
		$!poids
	#elseif($tr.extract($line.get(3),"[0-9]+[ ]*oz"))
	
		#set($oz=$!tr.extract($line.get(3),"[0-9,.]+"))
		#set($oz=$oz.replaceAll(",","."))
		#set($g=$tr.multiply($oz,2835,2))
		#set($g=$tr.divide("$g",100,2))
		#set($g="$g")
		#set($g=$g.replaceAll("[.]","£"))
		$!tr.extract($g,"([^£]+)","[1]")
		$!g
	
	#end
	

	
	
	
#end