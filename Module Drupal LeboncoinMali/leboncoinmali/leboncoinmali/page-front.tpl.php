<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="<?php print $language->language ?>" lang="<?php print $language->language ?>" dir="<?php print $language->dir ?>">
 <head>
  <?php print $head; ?>
  <title><?php print $head_title; ?></title>
    <meta http-equiv="Content-Style-Type" content="text/css" />
  <?php print $styles; ?>
   <!--[if IE 6]><link rel="stylesheet" href="<?php echo $base_path . $directory; ?>/style.ie6.css" type="text/css" /><![endif]-->
  <?php print $scripts; ?>

<!--[if IE 6]>
        <script type="text/javascript" src="<?php print $base_path . $directory; ?>/scripts/jquery.pngFix.js"></script>
<![endif]-->
<!--[if IE 6]>
<script type="text/javascript">
    jQuery(document).ready(function($)  {
        $(document).pngFix();
    });
</script>
<![endif]-->
        <script type="text/javascript" language="JavaScript">
            preload_image("<?php print $base_path.$directory.'/images/CarteMali.png'; ?>");
            preload_image("<?php print $base_path.$directory.'/images/Tombouctou.png'; ?>");
            preload_image("<?php print $base_path.$directory.'/images/Kidal.png'; ?>");
            preload_image("<?php print $base_path.$directory.'/images/Gao.png'; ?>");
            preload_image("<?php print $base_path.$directory.'/images/Mopti.png'; ?>");
            preload_image("<?php print $base_path.$directory.'/images/Segou.png'; ?>");
            preload_image("<?php print $base_path.$directory.'/images/Koulikoro.png'; ?>");
            preload_image("<?php print $base_path.$directory.'/images/Sikasso.png'; ?>");
            preload_image("<?php print $base_path.$directory.'/images/Kayes.png'; ?>");
        </script>
 </head>

<body<?php print phptemplate_body_class($left, $right); ?>>

<div style="clear:both"></div>

 <?php if($preface_first || $preface_middle || $preface_last) : ?>
    <div style="clear:both"></div>
    <div id="preface-wrapper" class="in<?php print (bool) $preface_first + (bool) $preface_middle + (bool) $preface_last; ?>">
          <?php if($preface_first) : ?>
          <div class="column A">
            <?php print $preface_first; ?>
          </div>
          <?php endif; ?>
          <?php if($preface_middle) : ?>
          <div class="column B">
            <?php print $preface_middle; ?>
          </div>
          <?php endif; ?>
          <?php if($preface_last) : ?>
          <div class="column C">
            <?php print $preface_last; ?>
          </div>
          <?php endif; ?>
      <div style="clear:both"></div>
    </div>
    <?php endif; ?>

<div style="clear:both"></div>
<div id="wrapper">
<?php if ($left): ?>
			<div id="sidebar-left" class="sidebar">
				<?php print $left ?>
			</div>
		<?php endif; ?>
<div id="content">
<!-- begin of map -->
 <div class="page_align">
            <div class="page_width">

                <div class="clearer" id="clearer">&nbsp;</div>

                <div id="ContainerIndex">

                    <div id="Page_sky">
                        <div id="Page">
                            <table id="TableContentTop" cellpadding="0" cellspacing="0">
                                <tbody><tr>
                                        <td class="Logo">
                                            <img src="<?php print $base_path.$directory.'/images/logo_big_new.png'; ?>" alt="Petites annonces gratuites d'occasion - leboncoin.fr">  
                                        </td>
                                        <td class="Text"> 
                                            Leboncoinmali part d'une idée simple : la bonne affaire<br>
                                            est au coin de la rue ! Pour passer ou chercher des<br>
                                            annonces, cliquez sur la région de votre choix et<br>
                                            trouvez la bonne affaire parmi <strong>17 080 311 annonces</strong>.
                                        </td>
                                    </tr>
                                </tbody></table>
                            <div>
                                <span class="SeparatorText">Simple, rapide et efficace !</span><img src="<?php print $base_path.$directory.'/images/line.png'; ?>" alt="">
                            </div>		<table id="TableContentBottom" >
                                <tbody><tr>
                                        <td align="right">
                                            <div class="Deposer">
                                                <!-- image 'deposer une annonce' -->
                                                <a href="#Deposer">
                                                    <img id="Deposer_annonce" 
                                                         onMouseOver="this.src='<?php print $base_path.$directory ?>/images/deposer_roll.png'<?php ;?>" 
                                                         onMouseOut="this.src='<?php print $base_path.$directory ?>/images/deposer.png'<?php ;?>" 
                                                         onClick="xt_med('C','23','deposer_bouton','N')" 
                                                         src="<?php print $base_path.$directory ?>/images/deposer.png<?php ;?>" alt="deposer.png"></a>

                                            </div>	
                                            <!--Carte du Mali -->
                                            <div class="MapContainer">
                                                <div id="area_image"  >
                                                    <img src="<?php print $base_path.$directory ?>/images/transparent.png<?php ;?>" alt="transparent" usemap="#Map" class="Image_Transparent_Map"/>

                                                    <map name="Map" id="Map" >
                                                        <area shape="poly" coords="251,13,299,12,433,108,424,114,422,124,378,162,411,193,413,227,390,238,390,260,420,264,427,315,406,306,394,302,371,306,362,292,344,287,335,296,320,290,304,298,279,297,283,278,276,270" href="#" alt="Tombouctou" onmouseover="change_image('<?php print $base_path.$directory.'/images/Tombouctou.png'; ?>','8')" onmouseout="hide_image('<?php print $base_path.$directory.'/images/CarteMali.png'; ?>','8')"/>
                                                        <area shape="poly" coords="436,110,470,130,471,141,483,146,491,155,498,156,501,153,507,161,529,168,531,179,527,186,533,193,550,188,559,187,560,219,539,222,505,221,496,228,477,232,461,225,452,215,444,202,430,197,417,194,405,185,391,172,384,163,412,137,430,122" href="#" alt="Kidal" onmouseover="change_image('<?php print $base_path.$directory.'/images/Kidal.png'; ?>','3')" onmouseout="hide_image('<?php print $base_path.$directory.'/images/CarteMali.png'; ?>','3')"/>
                                                        <area shape="poly" coords="417,200,439,204,453,224,469,236,484,237,495,233,505,226,560,224,562,261,560,271,552,292,544,298,543,302,529,301,484,306,469,317,432,314,421,262,399,255,396,245,407,236,420,225" href="#" alt="Gao" onmouseover="change_image('<?php print $base_path.$directory.'/images/Gao.png'; ?>','1')" onmouseout="hide_image('<?php print $base_path.$directory.'/images/CarteMali.png'; ?>','1')"/>
                                                        <area shape="poly" coords="343,371,326,368,323,352,318,348,307,356,298,357,299,349,288,347,286,336,277,330,277,321,292,315,288,307,291,303,307,304,315,298,322,296,325,301,332,303,339,302,349,293,356,293,363,308,373,309,392,307,406,316,415,315,416,317,407,324,399,326,390,332,380,333,381,343,367,339,356,346,353,353,345,356,341,363" href="#" alt="Mopti" onmouseover="change_image('<?php print $base_path.$directory.'/images/Mopti.png'; ?>','5')" onmouseout="hide_image('<?php print $base_path.$directory.'/images/CarteMali.png'; ?>','5')"/>
                                                        <area shape="poly" coords="253,303,282,303,287,312,281,315,274,321,273,330,276,336,281,340,286,348,294,352,293,360,300,363,309,363,315,356,319,360,321,367,313,373,314,380,315,387,306,380,301,385,295,389,286,385,277,385,256,389,245,384,235,383,238,374,246,367,239,356,243,345,251,339,257,331,250,321,255,313" href="#" alt="Segou" onmouseover="change_image('<?php print $base_path.$directory.'/images/Segou.png'; ?>','6')" onmouseout="hide_image('<?php print $base_path.$directory.'/images/CarteMali.png'; ?>','6')"/>
                                                        <area shape="poly" coords="172,302,248,303,248,311,244,317,248,326,250,332,237,344,237,364,231,372,232,379,236,386,246,389,255,397,262,404,253,408,248,416,238,416,229,407,228,396,223,397,219,402,214,408,206,412,192,418,184,421,177,409,171,395,180,391,186,382,189,371,192,362,190,354,189,346,182,348,181,341,198,339,206,335,196,329,193,319,185,315,179,307" href="#" alt="Koulikoro" onmouseover="change_image('<?php print $base_path.$directory.'/images/Koulikoro.png'; ?>','4')" onmouseout="hide_image('<?php print $base_path.$directory.'/images/CarteMali.png'; ?>','4')"/>
                                                        <area shape="poly" coords="184,426,203,416,216,409,226,404,225,405,232,415,239,422,243,422,252,420,257,413,265,406,263,399,257,394,272,388,276,391,281,391,286,388,288,393,294,393,301,392,303,389,306,391,307,394,306,400,302,407,292,409,283,412,279,415,278,418,281,421,283,429,280,434,275,440,274,445,274,452,271,457,262,458,260,463,257,450,253,446,246,448,242,449,239,452,237,457,233,458,228,462,223,461,218,455,214,454,209,457,206,460,201,458,195,454,194,445,194,437,188,434,185,437,191,431" href="#" alt="Sikasso" onmouseover="change_image('<?php print $base_path.$directory.'/images/Sikasso.png'; ?>','7')" onmouseout="hide_image('<?php print $base_path.$directory.'/images/CarteMali.png'; ?>','7')"/>
                                                        <area shape="poly" coords="101,295,117,310,125,301,135,302,160,303,163,296,169,302,176,309,180,314,185,319,191,323,194,330,198,334,184,336,178,338,180,348,183,353,187,358,187,368,183,381,176,388,167,393,160,392,157,398,151,402,144,404,134,399,127,400,121,406,112,396,106,401,101,401,100,385,103,375,99,366,96,359,90,357,86,352,86,339,81,326,90,320,97,311,94,303" href="#" alt="Kayes" onmouseover="change_image('<?php print $base_path.$directory.'/images/Kayes.png'; ?>','2')" onmouseout="hide_image('<?php print $base_path.$directory.'/images/CarteMali.png'; ?>','2')"/>
                                                    </map>
                                                </div>
                                            </div>
                                        </td>
                                        <td class="CountyList">
                                            <a id="county_1" href="#" title="Gao" onmouseover="change_image('<?php print $base_path.$directory.'/images/Gao.png'; ?>','1')" onMouseOut="hide_image('<?php print $base_path.$directory.'/images/CarteMali.png'; ?>','1');">Gao</a><br> 

                                            <a id="county_2" href="#" title="Kayes" onmouseover="change_image('<?php print $base_path.$directory.'/images/Kayes.png'; ?>','2')" onMouseOut="hide_image('<?php print $base_path.$directory.'/images/CarteMali.png'; ?>','2');">Kayes</a><br> 

                                            <a id="county_3" href="#" title="Kidal" onmouseover="change_image('<?php print $base_path.$directory.'/images/Kidal.png'; ?>','3')" onMouseOut="hide_image('<?php print $base_path.$directory.'/images/CarteMali.png'; ?>','3');">Kidal</a><br> 

                                            <a id="county_4" href="#" title="Koulikoro" onmouseover="change_image('<?php print $base_path.$directory.'/images/Koulikoro.png'; ?>','4')" onMouseOut="hide_image('<?php print $base_path.$directory.'/images/CarteMali.png'; ?>','4');">Koulikoro</a><br> 

                                            <a id="county_5" href="#" title="Mopti" onmouseover="change_image('<?php print $base_path.$directory.'/images/Mopti.png'; ?>','5')" onMouseOut="hide_image('<?php print $base_path.$directory.'/images/CarteMali.png'; ?>','5');">Mopti</a><br> 

                                            <a id="county_6" href="#" title="Segou" onmouseover="change_image('<?php print $base_path.$directory.'/images/Segou.png'; ?>','6')" onMouseOut="hide_image('<?php print $base_path.$directory.'/images/CarteMali.png'; ?>','6');">Segou</a><br> 

                                            <a id="county_7" href="#" title="Sikasso" onmouseover="change_image('<?php print $base_path.$directory.'/images/Sikasso.png'; ?>','7')" onMouseOut="hide_image('<?php print $base_path.$directory.'/images/CarteMali.png'; ?>','7');">Sikasso</a><br> 

                                            <a id="county_8" href="#" title="Tombouctou" onmouseover="change_image('<?php print $base_path.$directory.'/images/Tombouctou.png'; ?>','8')" onMouseOut="hide_image('<?php print $base_path.$directory.'/images/CarteMali.png'; ?>','8');">Tombouctou</a><br><br> 
                                        </td>
                                    </tr>
                                </tbody></table>
                            <div id="Footer">
                                Sur le site leboncoinmali.com, passez des annonces gratuites et sans commission. Vous pouvez consulter des petites annonces de particuliers et de professionnels partout en  France, que vous cherchiez des annonces immobilières, des voitures d'occasion, des offres d'emploi, des meubles, du matériel électronique ou tout autre type de produits d'occasion.
                                <div class="border">
                                    Infos légales
                                    Qui sommes-nous
                                    Contact
                                    Publicité
                                    Règles de diffusion
                                    Conditions Générales de Vente
                                    <a class="Link" href="javascript:add_bookmark()">Ajouter à mes favoris</a>
                                    <br>
                                </div>
                                Vous êtes à l'étranger ?
                                aggeliopolis.gr
                                ayosdito.ph
                                bomnegocio.com
                                berniaga.com
                                bikhir.ma
                                blocket.se
                                custojusto.pt
                                dinkos.com.au
                                jofogas.hu
                                kapaza.be
                                kufar.by
                                mudah.my
                                roloeganga.com
                                sahipasand.com
                                segundamano.es
                                segundamano.com.ar
                                segundamano.com.mx
                                subito.it
                                soov.ee
                                tori.fi
                                tutti.ch
                                vende.pe
                                willhaben.at
                                yapo.cl
                                zakroma.com.ua </div>
                        </div>
                        <div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
<!-- end of map -->
</div> <!-- end content -->

<?php if ($right): ?>
			<div id="sidebar-right" class="sidebar">
				<?php print $right; ?>
			</div>
		<?php endif; ?>
<div style="clear:both"></div>
</div> <!-- end wrapper -->


<?php if($bottom_first || $bottom_middle || $bottom_last) : ?>
    <div style="clear:both"></div>
    <div id="bottom-teaser" class="in<?php print (bool) $bottom_first + (bool) $bottom_middle + (bool) $bottom_last; ?>">
          <?php if($bottom_first) : ?>
          <div class="column A">
            <?php print $bottom_first; ?>
          </div>
          <?php endif; ?>
          <?php if($bottom_middle) : ?>
          <div class="column B">
            <?php print $bottom_middle; ?>
          </div>
          <?php endif; ?>
          <?php if($bottom_last) : ?>
          <div class="column C">
            <?php print $bottom_last; ?>
          </div>
          <?php endif; ?>
      <div style="clear:both"></div>
    </div>
    <?php endif; ?>


 <?php if($bottom_1 || $bottom_2 || $bottom_3 || $bottom_4) : ?>
    <div style="clear:both"></div><!-- Do not touch -->
    <div id="bottom-wrapper" class="in<?php print (bool) $bottom_1 + (bool) $bottom_2 + (bool) $bottom_3 + (bool) $bottom_4; ?>">
          <?php if($bottom_1) : ?>
          <div class="column A">
            <?php print $bottom_1; ?>
          </div>
          <?php endif; ?>
          <?php if($bottom_2) : ?>
          <div class="column B">
            <?php print $bottom_2; ?>
          </div>
          <?php endif; ?>
          <?php if($bottom_3) : ?>
          <div class="column C">
            <?php print $bottom_3; ?>
          </div>
          <?php endif; ?>
          <?php if($bottom_4) : ?>
          <div class="column D">
            <?php print $bottom_4; ?>
          </div>
          <?php endif; ?>
      <div style="clear:both"></div>
    </div><!-- Bottom -->
    <?php endif; ?>

<div style="clear:both"></div>
<div id="footer-wrapper">
<div id="footer">

 <?php print $footer; ?>
</div>
<?php if($footer_message || $secondary_links) : ?>
<div id="subnav-wrapper">
 <ul><li><?php print $footer_message; ?></li>
<li><?php if (isset($secondary_links)) : ?><?php print theme('links', $secondary_links, array('class' => 'links', 'id' => 'subnav')); ?><?php endif; ?></li></ul>
</div>
<?php endif; ?>
</div> <!-- end footer wrapper -->

<div style="clear:both"></div>

<?php print $closure; ?>
</body>
</html>


