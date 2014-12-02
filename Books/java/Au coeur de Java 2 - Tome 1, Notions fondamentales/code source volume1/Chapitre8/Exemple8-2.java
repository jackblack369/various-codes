Exemple 8.2 : PlafTest.java

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
public class PlafTest
{  
   public static void main(String[] args)
   {  
      PlafFrame frame = new PlafFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Un cadre avec un panneau et des boutons pour
         changer le "look and feel"
*/
class PlafFrame extends JFrame
{
   public PlafFrame()
   {
      setTitle("PlafTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      // ajouter un panneau au cadre

      PlafPanel panel = new PlafPanel();
      add(panel);
   }

   public static final int DEFAULT_WIDTH = 300;
   public static final int DEFAULT_HEIGHT = 200;  
}

/**
   Un panneau avec des boutons pour changer le look and feel
*/
class PlafPanel extends JPanel
{  
   public PlafPanel()
   {  
      UIManager.LookAndFeelInfo[] infos = 
          UIManager.getInstalledLookAndFeels();
      for (UIManager.LookAndFeelInfo info : infos)
         makeButton(info.getName(), info.getClassName());
   }

    /**
      Construit un bouton pour changer le look and feel.
      @param name Le nom du bouton
      @param plafName Le nom de la classe look and feel 
    */
   void makeButton(String name, final String plafName)
   {  
      // ajouter un bouton au panneau

      JButton button = new JButton(name);
      add(button);
       
      // définir l'action du bouton

       button.addActionListener(new 
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {  
               // action du bouton : passer au nouveau look and feel
               try
               {  
                  UIManager.setLookAndFeel(plafName);
                  SwingUtilities.updateComponentTreeUI
                     (PlafPanel.this);
               }
               catch(Exception e) { e.printStackTrace(); }
            }
         });
   }
}
