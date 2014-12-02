Exemple 9.4 : TextAreaTest.java

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TextAreaTest 
{
   public static void main(String[] args)
   {  
      TextAreaFrame frame = new TextAreaFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Un cadre avec une zone de texte et des boutons
        pour la mise à jour d'un texte 
*/
class TextAreaFrame extends JFrame
{  
   public TextAreaFrame()
   {  
      setTitle("TextAreaTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      buttonPanel = new JPanel();

      // un bouton pour ajouter du texte à la fin de la zone 

      JButton insertButton = new JButton("Insert");
      buttonPanel.add(insertButton);
      insertButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               textArea.append("The quick brown fox 
                  jumps over the lazy dog. ");
            }
         });

      /* bouton pour activer/désactiver le retour 
         automatique à la ligne */

      wrapButton = new JButton("Wrap");
      buttonPanel.add(wrapButton);
      wrapButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {  
               boolean wrap = !textArea.getLineWrap();
               textArea.setLineWrap(wrap);
               scrollPane.revalidate();
               wrapButton.setText(wrap ? "No wrap" : "Wrap");
            }
         });
     
      add(buttonPanel, BorderLayout.SOUTH);

      // ajouter une zone de texte avec défilement 
       
      textArea = new JTextArea(8, 40);
      scrollPane = new JScrollPane(textArea);

      add(scrollPane, BorderLayout.CENTER);
   }

   public static final int DEFAULT_WIDTH = 300;
   public static final int DEFAULT_HEIGHT = 300;  

   private JTextArea textArea;
   private JScrollPane scrollPane;
   private JPanel buttonPanel;
   private JButton wrapButton;
}
