Exemple 9.6 : RadioButtonTest.java

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
  
public class RadioButtonTest
{
   public static void main(String[] args)
   {  
      RadioButtonFrame frame = new RadioButtonFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.show();
   }
}

/**
   Un cadre avec un label pour l'exemple de texte et des 
      boutons radio pour la sélection de taille de police.
*/
class RadioButtonFrame extends JFrame 
{  
   public RadioButtonFrame()
   {  
      setTitle("RadioButtonTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      // ajouter le libellé d'exemple de texte

      label = new JLabel("The quick brown fox jumps over the lazy dog.");
      label.setFont(new Font("Serif", Font.PLAIN, 
         DEFAULT_SIZE));
      add(label, BorderLayout.CENTER);

      // ajouter les boutons radio 

      buttonPanel = new JPanel();
      group = new ButtonGroup();

      addRadioButton("Small", 8);
      addRadioButton("Medium", 12);
      addRadioButton("Large", 18);
      addRadioButton("Extra large", 36);

      add(buttonPanel, BorderLayout.SOUTH);
   }

   /**
      Ajoute un bouton radio qui définit la taille de police 
         de l'exemple de texte.
      @param name La chaîne de libellé du bouton 
      @param size La taille de police que définit ce bouton
   */
   public void addRadioButton(String name, final int size)
   {
      boolean selected = size == DEFAULT_SIZE;
      JRadioButton button = new JRadioButton(name, selected);
      group.add(button);
      buttonPanel.add(button);

      // Cet écouteur définit la taille de police de l'exemple 

      ActionListener listener = new 
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               // size fait référence au paramètre final de 
               // la méthode addRadioButton 
               label.setFont(new Font("Serif", Font.PLAIN, 
                  size));
            }
         }; 

       button.addActionListener(listener);
   }
  
   public static final int DEFAULT_WIDTH = 400;
   public static final int DEFAULT_HEIGHT = 200;  

   private JPanel buttonPanel;
   private ButtonGroup group;
   private JLabel label;

   private static final int DEFAULT_SIZE = 12;
}
