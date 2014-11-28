<style type="text/css">
ul#tabs {
	list-style-type: none;
}

ul#tabs li {
	display: inline;
}

ul#tabs a {
	padding-left: 0px;
}

div.tabContent.hide {
	display: none;
}

#carto_poi {
	width: 620px;
	margin-right: auto;
	margin-left: auto;
}


#poi_tab.selected {
    background: url("/images/pictos/pin-icon-blue-hover.png") no-repeat scroll 50px 20px #FFFFFF;
    border: medium none;
    font-size: 14px;
    width: 256px;
    padding: 25px 0 0 77px;
}


#poi_tab.disabled {
   	background: url("/images/pictos/pin-icon-blue.png") no-repeat scroll 50px 20px #F5F5F5;
    border-bottom: 1px solid #EBEBEB;
    border-right: 1px solid #EBEBEB;
    padding: 25px 0 0 77px;
    width: 247px;
}

#infos_tab.selected{
 background: url("/images/pictos/list-icon-blue-hover.png") no-repeat scroll 95px 20px #FFFFFF;
    border: medium none;
    font-size: 14px;
    padding-left: 127px;
    padding-top: 25px;
    width: 198px;
}

#infos_tab.disabled {
   background: url("/images/pictos/list-icon-blue.png") no-repeat scroll 95px 20px #F5F5F5;
    border-bottom: 1px solid #EBEBEB;
    border-left: 1px solid #EBEBEB;
    border-right: medium none;
    font-size: 14px;
    padding-left: 127px;
    padding-top: 25px;
    width: 198px;
}

#carto{
	margin-top:0px;
}

#carto_tabs_improved a{
	height: 60px;
    margin-bottom: 20px;
    color: #757575;
    display: block;
    float: left;
    font-size: 14px;
    height: 40px;
    text-decoration: none;
    text-transform: uppercase;
}
</style>

<script type="text/javascript">
//<![CDATA[
var tabLinks = new Array();
var contentDivs = new Array();

function init() {
  // Fix l'affichage des iframes trop rapides à s'afficher sur l'évènement 
  // Car peut poluer un peu les premières milisecondes ( bavures )
  fixIframe('infoQuartier','1355px');
	
  // Grab the tab links and content divs from the page
  var tabListItems = document.getElementById('tabs').childNodes;
  for ( var i = 0; i < tabListItems.length; i++ ) {
	if ( tabListItems[i].nodeName == "LI" ) {
	  var tabLink = getFirstChildWithTagName( tabListItems[i], 'A' );
	  var id = getHash( tabLink.getAttribute('href') );
	  tabLinks[id] = tabLink;
	  contentDivs[id] = document.getElementById( id );
	}
  }

  // Assign onclick events to the tab links, and
  // highlight the first tab
  var i = 0;

  for ( var id in tabLinks ) {
	tabLinks[id].onclick = showTab;
	tabLinks[id].onfocus = function() { this.blur() };
	if ( i == 0 ) {
			tabLinks[id].className = 'selected';
	}
	i++;
  }

  // Hide all content divs except the first
  var i = 0;

  for ( var id in contentDivs ) {
	if ( i != 0 ) {
		contentDivs[id].className = 'tabContent hide';
	}
	i++;
  }
}

function showTab() {
  var selectedId = getHash( this.getAttribute('href') );

  // Highlight the selected tab, and dim all others.
  // Also show the selected content div, and hide all others.
  for ( var id in contentDivs ) {
	if ( id == selectedId ) {
	  tabLinks[id].className = 'selected';
	  contentDivs[id].className = 'tabContent';
	} else {
	  tabLinks[id].className = 'disabled';
	  contentDivs[id].className = 'tabContent hide';
	}
  }

  // Stop the browser following the link
  return false;
}

function getFirstChildWithTagName( element, tagName ) {
  for ( var i = 0; i < element.childNodes.length; i++ ) {
	if ( element.childNodes[i].nodeName == tagName ) {
		return element.childNodes[i];
	}
  }
}

function getHash( url ) {
  var hashPos = url.lastIndexOf ( '#' );
  return url.substring( hashPos + 1 );
}

function fixIframe(id,size){
	if(id && size){
		document.getElementById(id).style.height = size;
	}
}

window.onload = init;
//]]>
</script>

<br/>
<div id="carto">
	<div id="carto_tabs_improved">
		<ul id="tabs">
			<li><a href="#poi"
				
				id="poi_tab"
				class="selected"
				>Points d'intérêts autour</a></li>
			<li><a href="#carto_infos_quartiers"
				
				id="infos_tab"
				class="disabled"
				>Infos Locales</a></li>
		</ul>
	</div>
	<div id="carto_poi">
		<div class="tabContent" id="poi">

			<iframe id="infoPoi" framespacing="0" frameborder="0"
				marginheight="0" marginwidth="0" scrolling="no" target="_blank"
				height="630px" width="620px"
				src="<?php echo add_parameters(($_REQUEST['map'] == 'b' ? '/ve_map_poi' : '/gmap_poi') . '.php?referer=licom&page=' . $_global['self'] . '&name=' . $agency['name'] . '&address=' . $ad['default_town_2'] . '&lzip=' . $ad['lzip'] . '&tel=' . $agency['tel'] . '&town=' . $agency['town_agency'] . '&disid1=' . $ad['district_id_1'] . '&disid2=' . $ad['district_id_2'] . '&disid3=' . $ad['district_id_3']); ?>";
			> </iframe>

		</div>
	</div>
	<div id="carto_infos_quartiers">
		<div class="tabContent" id="quartiers">

			<iframe id="infoQuartier" framespacing="0" frameborder="0"
				marginheight="0" marginwidth="0" scrolling="no" target="_blank"
				width="650px" height="0px"
				src="<?php echo add_parameters('infos_geo.php?referer=licom&page=' . $_global['self'] . '&name=' . $agency['name'] . '&address=' . $ad['default_town_2'] . '&lzip=' . $ad['lzip'] . '&tel=' . $agency['tel'] . '&town=' . $town . '&disid1=' . $ad['district_id_1'] . '&disid2=' . $ad['district_id_2'] . '&disid3=' . $ad['district_id_3']); ?>";
			> </iframe>

		</div>
	</div>
</div>