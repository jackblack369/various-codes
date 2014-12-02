Exemple 10.11 : CalculatorAppletApplication.java

/*
  Le visualisateur d'applets lit les balises ci-après si 
  vous l'appelez avec 
      appletviewer CalculatorAppletApplication.java (!)
  Aucun fichier HTML n'est nécessaire.
  <applet code="CalculatorAppletApplication.class"
     width="200" height="200">
  </applet>
*/

import javax.swing.*;

public class CalculatorAppletApplication 
   extends CalculatorApplet
// C'est un applet. C'est une application. C'est les DEUX !
{  
   public static void main(String[] args)
   {  
      AppletFrame frame 
         = new AppletFrame(new CalculatorApplet());
      frame.setTitle("CalculatorAppletApplication");
      frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }

   public static final int DEFAULT_WIDTH = 200;
   public static final int DEFAULT_HEIGHT = 200;
}
