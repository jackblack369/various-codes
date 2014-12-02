Exemple 8.9 : RetireResources_zh.java

import java.util.*;
import java.awt.*;

/**
   Voici les ressources chinoises différentes des 
   chaînes pour le calcul de la retraite.
*/
public class RetireResources_zh
   extends java.util.ListResourceBundle 
{
   public Object[][] getContents() { return contents; }
   static final Object[][] contents = 
   {
      // DEBUT LOCALISATION
      { "colorPre", Color.red },
      { "colorGain", Color.blue },
      { "colorLoss", Color.yellow }
      // FIN LOCALISATION
   };
}
