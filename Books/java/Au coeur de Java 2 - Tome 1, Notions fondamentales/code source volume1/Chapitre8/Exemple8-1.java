Exemple 8.1 : ButtonTest.java 

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ButtonTest
{  
   public static void main(String[] args)
   {  
      ButtonFrame frame = new ButtonFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Un cadre avec un panneau contenant des boutons
*/
class ButtonFrame extends JFrame
{
   public ButtonFrame()
   {
      setTitle("ButtonTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      // ajouter le panneau au cadre

      ButtonPanel panel = new ButtonPanel();
      add(panel);
   }

   public static final int DEFAULT_WIDTH = 300;
   public static final int DEFAULT_HEIGHT = 200;  
}

/**
   Un panneau avec trois boutons.
*/
class ButtonPanel extends JPanel
{  
   public ButtonPanel()
   {  
      // créer les boutons

      JButton yellowButton = new JButton("Yellow");
      JButton blueButton = new JButton("Blue");
      JButton redButton = new JButton("Red");

      // ajouter les boutons au panneau

      add(yellowButton);
      add(blueButton);
      add(redButton);

      // créer les actions des boutons

      ColorAction yellowAction = new ColorAction(Color.YELLOW);
      ColorAction blueAction = new ColorAction(Color.BLUE);
      ColorAction redAction = new ColorAction(Color.RED);

      // associer les actions aux boutons

      yellowButton.addActionListener(yellowAction);
      blueButton.addActionListener(blueAction);
      redButton.addActionListener(redAction);
   }

   /**
      Un écouteur d'action qui définit la couleur
          d'arrière-plan du panneau. 
   */
   private class ColorAction implements ActionListener
   {  
      public ColorAction(Color c)
      {  
         backgroundColor = c;
      }

      public void actionPerformed(ActionEvent event)
      {  
         setBackground(backgroundColor);
      }

      private Color backgroundColor;
   }
}
