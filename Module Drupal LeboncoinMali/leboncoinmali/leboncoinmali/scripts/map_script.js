function preload_image(a)
{
    var b=new Image;
    b.src=a;
}

function change_image(image,link_index){
	var a=document.getElementById("area_image");
	var b=document.getElementById("county_"+link_index);
	a.style.backgroundImage="url("+image+")";
	b.style.textDecoration="underline";
	return true
}

function hide_image(original_image,link_index){
	var a=document.getElementById("area_image");
	var b=document.getElementById("county_"+link_index);
	a.style.backgroundImage="url("+original_image+")";
	b.style.textDecoration="none";
	return true
}

