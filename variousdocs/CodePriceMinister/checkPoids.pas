#if($line.get(21))

#set($poids = $line.get(21))

#if($tr.toint($poids)<=500&&!$tr.extract($line.get(21),"P|petit|^250[ ]*[a‡][ ]*500[ ]*g$|^[ ]*(de)?[ ]*250[ ]*(g)?[ ]*(‡)?[ ]*500[ ]*g[ ]*$|0 . 500 g|250[ ]*g?[ ]*-[ ]*500[ ]*g?",true))
250 ‡ 500 g

#elseif($tr.toint($poids)<=3000&&!$tr.extract($line.get(21),"M|moyen|500[ ]*.[ ]*3000[ ]*g|^[ ]*500g ‡ 3000g[ ]*$|^[ ]*(de)?[ ]*500[ ]*(g)?[ ]*(‡)?[ ]*3000[ ]*g[ ]*$|500-3[kK][gG]|500[ ]*g[ ]*.[ ]*3[ ]*kg|500 ‡ 3000 g",true))
500 g ‡ 3 kg

#elseif($tr.toint($poids)>=3000&&!$tr.extract($line.get(21),"^3([ ]*[kK][gG])?[ ]*[a‡A-][ ]*7[ ]*[kK][gG]$|3000 ‡ 7000 g",true))
3 ‡ 7 kg

#end

#else
500 g ‡ 3 kg

#end