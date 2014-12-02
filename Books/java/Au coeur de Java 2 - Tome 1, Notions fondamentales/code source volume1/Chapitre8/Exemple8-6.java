Exemple 8.6 : MulticastTest.java

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MulticastTest
{
   public static void main(String[] args)
   {
      MulticastFrame frame = new MulticastFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Un cadre avec des boutons pour créer et fermer
       des cadres secondaires 
*/
class MulticastFrame extends JFrame
{
   public MulticastFrame()
   {
      setTitle("MulticastTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      // ajouter un panneau au cadre

      MulticastPanel panel = new MulticastPanel();
      add(panel);
   }

   public static final int DEFAULT_WIDTH = 300;
   public static final int DEFAULT_HEIGHT = 200;  
}

/**
   Un panneau avec des boutons pour créer et fermer des cadres.
*/
class MulticastPanel extends JPanel
{
   public MulticastPanel()
   {
      // ajouter un bouton "New" 

      JButton newButton = new JButton("New");
      add(newButton);
      final JButton closeAllButton = new JButton("Close all");
      add(CloseAllButton);

      ActionListener newListener = new 
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               BlankFrame frame = new BlankFrame(closeAllButton);
               frame.setVisible(true);
            }
         };

      newButton.addActionListener(newListener);
   }
}

/**
   Un cadre vide qui peut être fermé par un clic sur un bouton.
*/
class BlankFrame extends JFrame
{
   /**
      Construit un cadre vide
      @param closeButton Le bouton pour fermer ce cadre
   */
   public BlankFrame(final JButton closeButton)
   {
      counter++;
      setTitle("Frame " + counter);
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
      setLocation(SPACING * counter, SPACING * counter);     

      closeListener = new
         ActionListener()
         {
             closeButton.removeActionListener(closeListener);
             dispose();
          }
       };
     closeButton.addActionListener(closeListener);
   }

   Private ActionListener closeListener;
   public static final int DEFAULT_WIDTH = 200;
   public static final int DEFAULT_HEIGHT = 150;  
   public static final int SPACING = 40;
   private static int counter = 0;
}
