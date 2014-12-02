/**
 * @(#)OptionDialogTest.java
 *
 *
 * @author 
 * @version 1.00 2007/3/2
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;
import javax.border.*;

public class OptionDialogTest {
public static void main(String[] args)
   {  
      OptionDialogFrame frame = new OptionDialogFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/** 
    Un panneau contenant des boutons radio 
       dans une zone encadrée avec un titre.
*/
class ButtonPanel extends JPanel
{  
   /**
      Construit un panneau avec des boutons.
      @param title Le titre du panneau
      @param options Un tableau de libellés de boutons radio
   */
   public ButtonPanel(String title, String[] options)
   {  
      setBorder(BorderFactory.createTitledBorder
            (BorderFactory.createEtchedBorder(), title));
      setLayout(new BoxLayout(this, 
         BoxLayout.Y_AXIS));
      group = new ButtonGroup();
       
      // créer un bouton radio pour chaque option
      for (int i = 0; i < options.length; i++)
      {  
         JRadioButton b = new JRadioButton(options[i]);
         b.setActionCommand(options[i]);
         add(b);
         group.add(b);
         b.setSelected(i == 0);
      }
   }

   /**
      Extrait l'option sélectionnée.
      @return Le libellé du bouton radio sélectionné.
   */
   public String getSelection()
   {  
      return group.getSelection().getActionCommand();
   }
    
   private ButtonGroup group;
}
    
/**
   Un cadre contenant des paramètres pour la sélection 
      de différentes boîtes de dialogue d'options.
*/
class OptionDialogFrame extends JFrame 
{  
   public OptionDialogFrame()
   {  
      setTitle("OptionDialogTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      JPanel gridPanel = new JPanel();
      gridPanel.setLayout(new GridLayout(2, 3));
       
      typePanel = new ButtonPanel("Type", 
         new String[] 
         {  
            "Message", 
            "Confirm", 
            "Option", 
            "Input" 
         });

       messageTypePanel = new ButtonPanel("Message type", 
         new String[] 
         {  
            "ERROR_MESSAGE", 
            "INFORMATION_MESSAGE", 
            "WARNING_MESSAGE", 
            "QUESTION_MESSAGE",
            "PLAIN_MESSAGE" 
         });
          
      messagePanel = new ButtonPanel("Message", 
         new String[] 
         {  
            "String", 
            "Icon", 
            "Component", 
            "Other", 
            "Object"
         });
       
      optionTypePanel = new ButtonPanel("Confirm", 
         new String[] 
         {  
            "DEFAULT_OPTION", 
            "YES_NO_OPTION", 
            "YES_NO_CANCEL_OPTION", 
            "OK_CANCEL_OPTION" 
         });
          
      optionsPanel = new ButtonPanel("Option", 
         new String[] 
         {  
            "String[]", 
            "Icon[]", 
            "Object[]" 
         });
          
      inputPanel = new ButtonPanel("Input",
         new String[]
         {  
            "Text field",
            "Combo box"
         });
          
      gridPanel.add(typePanel);
      gridPanel.add(messageTypePanel);
      gridPanel.add(messagePanel);
      gridPanel.add(optionTypePanel);
      gridPanel.add(optionsPanel);
      gridPanel.add(inputPanel);

      // ajouter un panneau avec un bouton Afficher (Show)

      JPanel showPanel = new JPanel();
      JButton showButton = new JButton("Show");
      showButton.addActionListener(new ShowAction());
      showPanel.add(showButton);
       
      add(gridPanel, BorderLayout.CENTER);
      add(showPanel, BorderLayout.SOUTH);
   }

   /**
      Récupère le message sélectionné.
      @return Une chaîne, une icône, un composant ou un tableau
         d'objets, selon la sélection dans le panneau Message
   */
   public Object getMessage()
   {  
      String s = messagePanel.getSelection();
      if (s.equals("String"))
         return messageString;
      else if (s.equals("Icon"))
         return messageIcon;
      else if (s.equals("Component"))
         return messageComponent;
      else if (s.equals("Object[]"))
         return new Object[]
         {  
            messageString,
            messageIcon,
            messageComponent,
            messageObject
         };
      else if (s.equals("Other"))
         return messageObject;
      else return null;
   }
    
   /**
      Récupère les options actuellement sélectionnées 
      @return Un tableau de chaînes, d'icônes ou d'objets, 
         selon la sélection dans le panneau Option 
   */
   public Object[] getOptions()
   {  
      String s = optionsPanel.getSelection();
      if (s.equals("String[]"))
         return new String[] { "Yellow", "Blue", "Red" };
      else if (s.equals("Icon[]"))
         return new Icon[]
         {  
            new ImageIcon("yellow-ball.gif"),
            new ImageIcon("blue-ball.gif"),
            new ImageIcon("red-ball.gif")
         };
      else if (s.equals("Object[]"))
         return new Object[]
         {  
            messageString,
            messageIcon,
            messageComponent,
            messageObject
         };
      else 
         return null;
   }

   /**
      Récupère le message ou le type d'option sélectionné
      @param panel Le panneau Message Type ou Confirm
      @return La constante XXX_MESSAGE ou XXX_OPTION 
         de la classe JOptionPane 
   */
   public int getType(ButtonPanel panel)
   {  
      String s = panel.getSelection();
      try
      {
         return JOptionPane.class.getField(s).getInt(null);
      }
      catch(Exception e)
      {  
         return -1;
      }
   }

   /**
      L'écouteur d'action pour le bouton Show affiche une 
         boîte de dialogue Confirm, Input, Message ou Option 
         selon la sélection dans le panneau Type.
   */
   private class ShowAction implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {  
         if (typePanel.getSelection().equals("Confirm"))
            JOptionPane.showConfirmDialog(
               OptionDialogFrame.this,
               getMessage(),
               "Title",
               getType(optionTypePanel),
               getType(messageTypePanel));
         else if (typePanel.getSelection().equals("Input"))
         {  
            if (inputPanel.getSelection().equals("Text field"))
               JOptionPane.showInputDialog(
                  OptionDialogFrame.this,
                  getMessage(),
                  "Title",
                  getType(messageTypePanel));
            else
               JOptionPane.showInputDialog(
                  OptionDialogFrame.this,
                  getMessage(),
                  "Title",
                  getType(messageTypePanel),
                  null,
                  new String[] { "Yellow", "Blue", "Red" },
                  "Blue");
         }
         else if (typePanel.getSelection().equals("Message"))
            JOptionPane.showMessageDialog(
               OptionDialogFrame.this,
               getMessage(),
               "Title",
               getType(messageTypePanel));
         else if (typePanel.getSelection().equals("Option"))
            JOptionPane.showOptionDialog(
               OptionDialogFrame.this,
               getMessage(),
               "Title",
               getType(optionTypePanel),
               getType(messageTypePanel),
               null,
               getOptions(),
               getOptions()[0]);           
      }
   }

   public static final int DEFAULT_WIDTH = 600;
   public static final int DEFAULT_HEIGHT = 400;  

   private ButtonPanel typePanel;
   private ButtonPanel messagePanel;
   private ButtonPanel messageTypePanel;
   private ButtonPanel optionTypePanel;
   private ButtonPanel optionsPanel;
   private ButtonPanel inputPanel;
    
   private String messageString = "Message";
   private Icon messageIcon = new ImageIcon("blue-ball.gif");
   private Object messageObject = new Date();
   private Component messageComponent = new SamplePanel();
 }

/**
   Un panneau avec un fond coloré 
*/

class SamplePanel extends JPanel
{  
   public void paintComponent(Graphics g)
   {  
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D)g;
      Rectangle2D rect = new Rectangle2D.Double(0, 0,
         getWidth() - 1, getHeight() - 1);
      g2.setPaint(Color.YELLOW);
      g2.fill(rect);
      g2.setPaint(Color.BLUE);
      g2.draw(rect);
   }

   public Dimension getMinimumSize()
   {  
      return new Dimension(10, 10);
   }
} 

    
}