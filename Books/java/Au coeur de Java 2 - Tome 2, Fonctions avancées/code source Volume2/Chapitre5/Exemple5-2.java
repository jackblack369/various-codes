Exemple 5.2 : LongListTest.java

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
   Ce programme montre une liste qui calcule des entrées de liste de  
   manière dynamique.
*/
public class LongListTest
{  
   public static void main(String[] args)
   {  
      JFrame frame = new LongListFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Ce bloc contient une longue liste de mots ainsi qu'une étiquette qui
   montre une phrase constituée du mot choisi. 
*/
class LongListFrame extends JFrame
{  
   public LongListFrame()
   {  
      setTitle("LongListTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      wordList = new JList(new WordListModel(3));
      wordList.setSelectionMode
         (ListSelectionModel.SINGLE_SELECTION);
      wordList.setPrototypeCellValue("www");
      JScrollPane scrollPane = new JScrollPane(wordList);

      JPanel p = new JPanel();
      p.add(scrollPane);
      wordList.addListSelectionListener(new
         ListSelectionListener()
         {
            public void valueChanged(ListSelectionEvent evt)
            {  
               StringBuilder word 
                  = (StringBuilder) wordList.getSelectedValue();
               setSubject(word.toString());
            }

         });

      Container contentPane = getContentPane();
      contentPane.add(p, BorderLayout.NORTH);
      label = new JLabel(prefix + suffix);
      contentPane.add(label, BorderLayout.CENTER);
      setSubject("fox");
   }

   /**
      Définit le sujet de l'étiquette.
      @param word le nouveau sujet de la phrase
   */
   public void setSubject(String word)
   {
      StringBuilder text = new StringBuilder(prefix);
      text.append(word);
      text.append(suffix);
      label.setText(text.toString());
   }

   private static final int DEFAULT_WIDTH = 400;
   private static final int DEFAULT_HEIGHT = 300;
   private JList wordList;
   private JLabel label;
   private String prefix = "The quick brown ";
   private String suffix = " jumps over the lazy dog.";
}

/**
   Un modèle qui génère un mot à n lettres de manière dynamique.
*/
class WordListModel extends AbstractListModel
{ 
   /**
      Construit le modèle.
      @param n la longueur du mot
   */
   public WordListModel(int n) { length = n; }

   public int getSize()
   {  
      return (int)Math.pow(LAST - FIRST + 1, length);
   }

   public Object getElementAt(int n)
   {      
      StringBuilder r = new StringBuilder();;
      for (int i = 0; i < length; i++)
      {  
         char c = (char)(FIRST + n % (LAST - FIRST + 1));
         r.insert(0, c);
         n = n / (LAST - FIRST + 1);
      }
      return r;
   }

   private int length;
   public static final char FIRST = 'a';
   public static final char LAST = 'z';
}
