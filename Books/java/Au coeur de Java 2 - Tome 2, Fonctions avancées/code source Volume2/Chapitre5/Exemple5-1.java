Exemple 5.1 : ListTest.java

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
   Ce programme présente une liste de chaînes fixes.
*/
public class ListTest
{  
   public static void main(String[] args)
   {  
      JFrame frame = new ListFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Ce bloc contient une liste de mots et une étiquette présentant une
   phrase constituée des mots choisis. Vous remarquerez que vous pouvez 
   sélectionner plusieurs mots avec Ctrl+clic et Maj+clic.
*/
class ListFrame extends JFrame
{  
   public ListFrame()
   {  
      setTitle("ListTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      String[] words =
      {  
         "quick","brown","hungry","wild","silent",
         "huge","private","abstract","static","final"
      };

      wordList = new JList(words);
      wordList.setVisibleRowCount(4);
      JScrollPane scrollPane = new JScrollPane(wordList);

      listPanel = new JPanel();
      listPanel.add(scrollPane);
      wordList.addListSelectionListener(new
         ListSelectionListener()
         {
            public void valueChanged(ListSelectionEvent event)
            {  
               Object[] values = wordList.getSelectedValues();

               StringBuilder text = new StringBuilder(prefix);
               for (int i = 0; i < values.length; i++)
               {  
                  String word = (String) values[i];
                  text.append(word);
                  text.append(" ");
               }
               text.append(suffix);

               label.setText(text.toString());
            }
         });

   buttonPanel = new JPanel();
   group = new ButtonGroup();
   makeButton("Vertical", JList.VERTICAL);
   makeButton("Vertical Wrap", JList.VERTICAL_WRAP);
   makeButton("Horizontal Wrap", JList.HORIZONTAL_WRAP);

   add(listPanel, BorderLayout.NORTH);
   label = new JLabel(prefix + suffix);
   add(label, BorderLayout.CENTER);
   add(buttonPanel, BorderLayout.SOUTH);
   }

   /**
    Crée un bouton pour définir l'orientation de la liste.
    @param label L'intitulé du bouton
    @param orientation L'orientation de la liste
   */
   private void makeButton(String label, final int orientation)
   {
      JRadioButton button = new JRadioButton(label);
      buttonPanel.add(button);
      if (group.getButtonCount() == 0) button.setSelected(true);
      group.add(button);
      button.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               wordList.setLayoutOrientation(orientation);
               listPanel.revalidate();
            }
         });
   }

   private static final int DEFAULT_WIDTH = 400;
   private static final int DEFAULT_HEIGHT = 300;
   private JPanel listPanel;
   private JList wordList;
   private JLabel label;
   private JPanel buttonPanel;
   private ButtonGroup group;
   private String prefix = "The ";
   private String suffix = "fox jumps over the lazy dog.";
}
