Exemple 7.3 : VerifierTest.java

import java.awt.*;
import java.applet.*;

/**
   Cette application montre le vérificateur de bytecode de la
   machine virtuelle. Si vous utilisez un éditeur hexa pour modifier
   le fichier de classe, la machine virtuelle devrait détecter
   la falsification.
*/
public class VerifierTest extends Applet
{
   public static void main(String[] args)
   {
      System.out.println("1 + 2 == " + fun());
   }

   /**
      Une fonction qui calcule 1 + 2
      @return 3, si le code n'a pas été corrompu
   */
   public static int fun()
   {
      int m;
      int n;
      m = 1;
      n = 2;
      // éditeur hexa utilisé pour remplacer par "m = 2" 
      // dans le fichier de classe
      int r = m + n;
      return r;
   }

   public void paint(Graphics g)
   {
      g.drawString("1 + 2 == " + fun(), 20, 20);
   }
}
