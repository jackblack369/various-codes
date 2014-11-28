function setCookie(mySem) 
{
	var now = new Date();
    var time = now.getTime();
    var uid = '<?php echo $_SESSION['projectUser']->getUid();?>';
    time += 3600 * 1000;
    
    now.setTime(time);
    document.cookie = 
    	'nav_info=' +
    	'uid=' + uid +
    	',mySem=' + mySem +
    	'; expires=' + now.toUTCString() + 
    	'; path=/';
}

function getCookie(name) {
	var nameEQ = name + "=";
	var ca = document.cookie.split(';');

	for ( var i = 0; i < ca.length; i++) {
		var c = ca[i];

		while ( c.charAt(0) == ' ' ) {
			  c = c.substring(1,c.length);
		}
		
		if ( c.indexOf(nameEQ) == 0 ) {
			return c.substring(nameEQ.length,c.length);
		}
	}
	return null;
}

function checkCookie(mySem) 
{
	var stringCookie = getCookie('nav_info');

    if ( stringCookie ) {
    	stringCookie = stringCookie.match(/mySem=[0-9]*/i);
    	var mySemCookie = stringCookie[0].match(/[0-9]*/i);

    	if ( mySemCookie != mySem ) {
    		$("#semaine_record[value='"+ mySem+"']").attr('selected', 'selected');
    		setCookie(mySem);
        }
    }
    else {
        setCookie(mySem);
    }
	
	
	console.log(stringCookie[0]);
}