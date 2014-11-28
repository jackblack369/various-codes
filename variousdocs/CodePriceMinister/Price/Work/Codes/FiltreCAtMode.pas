#if($!line.get(10))

	#set($public="$!line.get(10).toLowerCase()")

	#if($tr.extract($public,"hommes?",true))
	Homme
	#elseif($tr.extract($public,"femmes?",true))
	Femme
	#elseif($tr.extract($public,"adolescents?",true))
	adolescent
	#elseif($tr.extract($public,"adolescentes?",true))
	adolescente
	#elseif($tr.extract($public,"Adultes?[ ]*.?Mixtes?.?",true))
	Adulte (Mixte)
	#elseif($tr.extract($public,"B.b.s?[ ]*.?Filles?.?",true))
	Bébé (Fille)
	#elseif($tr.extract($public,"B.b.s?[ ]*.?Gar.ons?.?",true))
	Bébé (Garçon) 
	#elseif($tr.extract($public,"B.b.s?[ ]*.?Mixtes?.?",true))
	Bébé (Mixte)
	#elseif($tr.extract($public,"Enfants?[ ]*.?Filles?.?",true))
	Enfant (Fille)
	#elseif($tr.extract($public,"Enfants?[ ]*.?Gar.ons?.?",true))
	Enfant (Garçon)
	#elseif($tr.extract($public,"Enfants?[ ]*.?Mixtes?.?",true))
	Enfant (Mixte)
	#elseif($tr.extract($public,"Tou(t|s)[ ]*public?",true))
	Tout public
	#elseif($tr.extract($public,"Future?s?[ ]*Mamans?",true))
	Future Maman 
	#end
	
#elseif($!line.get(8)||$!line.get(5))

	#set($public="$!line.get(8).toLowerCase() $!line.get(5).toLowerCase()")
	
	#if($tr.extract($public,"hommes?",true))
	Homme
	#elseif($tr.extract($public,"femmes?",true))
	Femme
	#elseif($tr.extract($public,"adolescents?",true))
	adolescent
	#elseif($tr.extract($public,"adolescentes?",true))
	adolescente
	#elseif($tr.extract($public,"Adultes?[ ]*.?Mixtes?.?",true))
	Adulte (Mixte)
	#elseif($tr.extract($public,"B.b.s?[ ]*.?Filles?.?",true))
	Bébé (Fille)
	#elseif($tr.extract($public,"B.b.s?[ ]*.?Gar.ons?.?",true))
	Bébé (Garçon) 
	#elseif($tr.extract($public,"B.b.s?[ ]*.?Mixtes?.?",true))
	Bébé (Mixte)
	#elseif($tr.extract($public,"Enfants?[ ]*.?Filles?.?",true))
	Enfant (Fille)
	#elseif($tr.extract($public,"Enfants?[ ]*.?Gar.ons?.?",true))
	Enfant (Garçon)
	#elseif($tr.extract($public,"Enfants?[ ]*.?Mixtes?.?",true))
	Enfant (Mixte)
	#elseif($tr.extract($public,"Tou(t|s)[ ]*public?",true))
	Tout public
	#elseif($tr.extract($public,"Future?s?[ ]*Mamans?",true))
	Future Maman 
	#end
	
#end