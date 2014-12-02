Exemple 9.8 : ComboBoxTest.java

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ComboBoxTest
{
   public static void main(String[] args)
   {  
      ComboBoxFrame frame = new ComboBoxFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Un cadre avec un label pour l'exemple de texte et une zone de liste
      déroulante pour sélectionner les types de police.
*/
class ComboBoxFrame extends JFrame 
{  
   public ComboBoxFrame()
   {  
      setTitle("ComboBoxTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      // ajouter le libellé d'exemple de texte

      label = new JLabel("The quick brown fox jumps over the lazy dog.");
      label.setFont(new Font("Serif", Font.PLAIN, 
         DEFAULT_SIZE));
      add(label, BorderLayout.CENTER);

      // créer une liste déroulante et ajouter les types de polices 

      faceCombo = new JComboBox();
      faceCombo.setEditable(true);
      faceCombo.addItem("Serif");
      faceCombo.addItem("SansSerif");
      faceCombo.addItem("Monospaced");
      faceCombo.addItem("Dialog");
      faceCombo.addItem("DialogInput");

      // L'écouteur de la liste déroulante remplace la police du 
      // libellé contenant l'exemple par celle sélectionnée

      faceCombo.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               label.setFont(new Font(
                  (String)faceCombo.getSelectedItem(), 
                  Font.PLAIN, 
                  DEFAULT_SIZE));         
            }
         });

      // ajouter une liste déroulante à un panneau dans la
      // partie sud du cadre 

      JPanel comboPanel = new JPanel();
      comboPanel.add(faceCombo);
      add(comboPanel, BorderLayout.SOUTH);
   }

   public static final int DEFAULT_WIDTH = 300;
   public static final int DEFAULT_HEIGHT = 200;  
    
   private JComboBox faceCombo;
   private JLabel label;
   private static final int DEFAULT_SIZE = 12;
}
