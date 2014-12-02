Exemple 8.8 : RetireResources_fr.java

import java.util.*;
import java.awt.*;

/**
   Voici les ressources françaises différentes des 
   chaînes pour le calcul de la retraite.
*/
public class RetireResources_fr 
   extends java.util.ListResourceBundle 
{
   public Object[][] getContents() { return contents; }
   static final Object[][] contents = 
   {
      // DEBUT LOCALISATION
      { "colorPre", Color.yellow },
      { "colorGain", Color.black },
      { "colorLoss", Color.red }
      // FIN LOCALISATION
   };
}
