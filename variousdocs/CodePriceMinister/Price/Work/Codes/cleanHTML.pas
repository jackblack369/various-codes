#if($!line.get(5))
#set($edito = $!line.get(5).replaceAll("</?[^>]*>"," "))
#trunc($edito 490 '...')
#end