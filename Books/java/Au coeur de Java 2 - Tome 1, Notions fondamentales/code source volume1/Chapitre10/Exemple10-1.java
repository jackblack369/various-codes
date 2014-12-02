Exemple 10.1 : NotHelloWorldApplet.java

/*
  Les balises HTML suivantes sont nécessaires pour afficher cet applet 
  dans un navigateur :
  <applet code="NotHelloWorldApplet.class" width="300" height="100">
  </applet>
*/
import javax.swing.*;

public class NotHelloWorldApplet extends JApplet
{
   public void init()
   {
      JLabel label = new JLabel("Not a Hello, World applet",
         SwingConstants.CENTER);
      add(label);
   }
}
