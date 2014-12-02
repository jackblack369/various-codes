Exemple 7.3 : NotHelloWorld.java

import javax.swing.*;
import java.awt.*;

public class NotHelloWorld
{  
   public static void main(String[] args)
   {  
      NotHelloWorldFrame frame = new NotHelloWorldFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Un cadre qui contient un panneau de message 
*/
class NotHelloWorldFrame extends JFrame
{
   public NotHelloWorldFrame()
   {
      setTitle("NotHelloWorld");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      // ajouter le panneau au cadre

      NotHelloWorldPanel panel = new NotHelloWorldPanel();
      add(panel);
   }

   public static final int DEFAULT_WIDTH = 300;
   public static final int DEFAULT_HEIGHT = 200;  
}

/**
   Un panneau qui affiche un message. 
*/
class NotHelloWorldPanel extends JPanel
{  
   public void paintComponent(Graphics g)
   {  
      super.paintComponent(g);

       g.drawString("Not a Hello, World program", 
         MESSAGE_X, MESSAGE_Y);
   }

   public static final int MESSAGE_X = 75;
   public static final int MESSAGE_Y = 100;
}
