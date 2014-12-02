Exemple 8.7 : RetireResources.java

import java.util.*;
import java.awt.*;

/**
   Voici les ressources anglaises différentes des 
   chaînes pour le calcul de la retraite.
*/
public class RetireResources
   extends java.util.ListResourceBundle 
{
   public Object[][] getContents() { return contents; }
   static final Object[][] contents = 
   {
      // DEBUT LOCALISATION
      { "colorPre", Color.blue },
      { "colorGain", Color.white },
      { "colorLoss", Color.red }
      // FIN LOCALISATION
   };
}
