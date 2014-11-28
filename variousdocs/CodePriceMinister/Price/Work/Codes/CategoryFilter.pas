#if($tr.extract($line.get(4),"cr.m.|bronz"))
	#set($prod ="autobronzant > soin beauté"  )
	#end
	

    #if($tr.extract($line.get(4),"parfum|eau.toil.tt."))
	#set($prod ="parfum > parfum" )
	#end

    #if($tr.extract($line.get(4),"s.l.|bain"))
	#set($prod ="sels > hygiène et santé"  )
	#end


    #if($tr.extract($line.get(4),"shamp."))
	#set($prod ="shampooing > hygiène et santé" )
	#end


    #if($tr.extract($line.get(4),"g.l|d.m.llant"))
	#set($prod ="gel shampooing douche > hygiène et santé" )
	#end
	
	$!prod