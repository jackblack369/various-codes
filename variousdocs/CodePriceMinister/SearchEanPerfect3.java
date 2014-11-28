	#if(!$line.get("ref_ean"))

	#set($isbn = $line.get("ref_isbn").replaceAll("[^0-9A-Za-z]+",""))	
		 
		 #if($id.searchEan($isbn) && $id.checkIsbn($isbn))

		  #set($ean13 = $isbn.replaceAll("[^0-9]","") )
		  #set($ean13 = $tr.extract($ean13,"^.{9}") )
		  #set($ean13 = "978${ean13}" )

		  #set($c0  = $tr.toint( $tr.extract($ean13,"^.{0}(.)","[1]") ) )
		  #set($c1  = $tr.toint( $tr.extract($ean13,"^.{1}(.)","[1]") ) * 3 )
		  #set($c2  = $tr.toint( $tr.extract($ean13,"^.{2}(.)","[1]") ) )
		  #set($c3  = $tr.toint( $tr.extract($ean13,"^.{3}(.)","[1]") ) * 3 )
		  #set($c4  = $tr.toint( $tr.extract($ean13,"^.{4}(.)","[1]") ) )
		  #set($c5  = $tr.toint( $tr.extract($ean13,"^.{5}(.)","[1]") ) * 3 )
		  #set($c6  = $tr.toint( $tr.extract($ean13,"^.{6}(.)","[1]") ) )
		  #set($c7  = $tr.toint( $tr.extract($ean13,"^.{7}(.)","[1]") ) * 3 )
		  #set($c8  = $tr.toint( $tr.extract($ean13,"^.{8}(.)","[1]") ) )
		  #set($c9  = $tr.toint( $tr.extract($ean13,"^.{9}(.)","[1]") ) * 3 )
		  #set($c10 = $tr.toint( $tr.extract($ean13,"^.{10}(.)","[1]") ) )
		  #set($c11 = $tr.toint( $tr.extract($ean13,"^.{11}(.)","[1]") ) * 3 )

		  #set($tt = $c0 +$c1 +$c2 +$c3 +$c4 +$c5 +$c6 +$c7 +$c8 +$c9 +$c10 +$c11)
		  #set($mm = 10 - ($tt % 10) )
		  #set($mm = $tr.extract($mm,".$") )

		  #set($ean13 = "${ean13}${mm}")

		  #if($ean13.length() == 13)
		   $!ean13
		  #end
		  
		#end
		
	#else
		$!line.get("ref_ean")
	#end