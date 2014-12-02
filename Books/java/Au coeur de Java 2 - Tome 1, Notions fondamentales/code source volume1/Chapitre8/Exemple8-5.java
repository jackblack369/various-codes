Exemple 8.5 : ActionTest.java

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ActionTest
{  
   public static void main(String[] args)
   {  
      ActionFrame frame = new ActionFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Un cadre avec un panneau pour faire la démonstration
      des actions de changement de couleur.
*/
class ActionFrame extends JFrame
{
   public ActionFrame()
   {
      setTitle("ActionTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
       
      // ajouter un panneau au cadre

      ActionPanel panel = new ActionPanel();
      add(panel);
   }

   public static final int DEFAULT_WIDTH = 300;
   public static final int DEFAULT_HEIGHT = 200;  
}

/**
   Un panneau avec des boutons et des raccourcis clavier 
      pour modifier la couleur d'arrière-plan.
*/
class ActionPanel extends JPanel
{  
   public ActionPanel()
   {  
      // définir les actions

      Action yellowAction = new ColorAction("Yellow",
         new ImageIcon("yellow-ball.gif"),
         Color.YELLOW);
      Action blueAction = new ColorAction("Blue",
         new ImageIcon("blue-ball.gif"),
         Color.BLUE);
      Action redAction = new ColorAction("Red",
         new ImageIcon("red-ball.gif"),
         Color.RED);

      // ajouter les boutons pour ces actions
       
      add(new JButton(yellowAction));
      add(new JButton(blueAction));
      add(new JButton(redAction));

      // associer des noms aux touches Y, B et R 

      InputMap imap = getInputMap(
         JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT); 
       
      imap.put(KeyStroke.getKeyStroke("ctrl Y"), "panel.yellow");
      imap.put(KeyStroke.getKeyStroke("ctrl B"), "panel.blue");
      imap.put(KeyStroke.getKeyStroke("ctrl R"), "panel.red");

      // associer les noms aux actions

      ActionMap amap = getActionMap();
      amap.put("panel.yellow", yellowAction);
      amap.put("panel.blue", blueAction);
      amap.put("panel.red", redAction);
   }

   public class ColorAction extends AbstractAction
   {  
      /**
         Construit une action couleur.
         @param name Le nom du bouton
         @param icon L'icône du bouton
         @param c La couleur d'arrière-plan 
      */
      public ColorAction(String name, Icon icon, Color c)
      {  
         putValue(Action.NAME, name);
         putValue(Action.SMALL_ICON, icon);
         putValue(Action.SHORT_DESCRIPTION, 
            "Set panel color to " + name.toLowerCase());
         putValue("color", c);
      }

      public void actionPerformed(ActionEvent event)
      {  
         Color c = (Color)getValue("color");
         setBackground(c);
         repaint();
      }
   }
}
