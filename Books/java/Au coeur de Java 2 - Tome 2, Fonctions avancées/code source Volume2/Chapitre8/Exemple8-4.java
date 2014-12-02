Exemple 8.4 : CollationTest.java

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

/**
   Ce programme montre le tri des chaînes en fonction
   de divers paramètres régionaux.
*/
public class CollationTest
{
   public static void main(String[] args)
   {
      JFrame frame = new CollationFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Ce bloc contient des zones de liste modifiables 
   permettant de choisir un paramètre régional, une 
   force de tri et des règles de décomposition, un champ 
   de texte ainsi qu'un bouton pour ajouter de nouvelles 
   chaînes et une zone de texte pour recenser les 
   chaînes triées.
*/
class CollationFrame extends JFrame
{
   public CollationFrame()
   {
      setTitle("CollationTest");

      setLayout(new GridBagLayout());
      add(new JLabel("Param. Rég."), new GBC(0, 0).setAnchor(GBC.EAST));
      add(new JLabel("Force"), new GBC(0, 1).setAnchor(GBC.EAST));
      add(new JLabel("Décomposition"), new GBC(
         0, 2).setAnchor(GBC.EAST));
      add(addButton, new GBC(0, 3).setAnchor(GBC.EAST));
      add(localeCombo, new GBC(1, 0).setAnchor(GBC.WEST));
      add(strengthCombo, new GBC(1, 1).setAnchor(GBC.WEST));
      add(decompositionCombo, new GBC(1, 2).setAnchor(GBC.WESt));
      add(newWord, new GBC(1, 3).setFill(GBC.HORIZONTAL));
      add(new JScrollPane(sortedWords), new GBC(1, 4).setFill(GBC.BOTH));

      strings.add("America");
      strings.add("ant");
      strings.add("Zulu");
      strings.add("zebra");
      strings.add("\u00C5ngstr\u00F6m");
      strings.add("Angstrom");
      strings.add("Ant");
      updateDisplay();

      addButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               strings.add(newWord.getText());
               updateDisplay();
            }
         });

      ActionListener listener = new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
               { updateDisplay(); }
         };

      localeCombo.addActionListener(listener);
      strengthCombo.addActionListener(listener);
      decompositionCombo.addActionListener(listener);
      pack();
   }

   /**
      Actualise l'affichage et trie les chaînes en 
      fonction des chaînes de l'utilisateur.
   */
   public void updateDisplay()
   {
      Locale currentLocale = (Locale)
         localeCombo.getSelectedIndex();
      localeCombo.setLocale(currentLocale);

      currentCollator
         = Collator.getInstance(currentLocale);
      currentCollator.setStrength(strengthCombo.getValue());
      currentCollator.setDecomposition(
         decompositionCombo.getValue());

      Collections.sort(strings, currentCollator);

      sortedWords.setText("");
      for (int i = 0; i < strings.size(); i++)
      {
         String s = strings.get(i);
         if (i > 0
            && currentCollator.compare(s, strings.get(i - 1)) == 0)
            sortedWords.append("= ");
         sortedWords.append(s + "\n");
      }
      pack();
   }

   private Locale[] locales;
   private List<String> strings = new ArrayList<String>();
   private Collator currentCollator;
   private JComboBox localeCombo = 
      new LocaleCombo(Collator.getAvailableLocales());

   private EnumCombo strengthCombo
      = new EnumCombo(Collator.class,
        new String[] { "Primaire", "Secondaire", "Tertiaire" });
   private EnumCombo decompositionCombo
      = new EnumCombo(Collator.class,
        new String[] { "Décomposition Canonique ",
        "Décomposition complète", "Pas de décomposition" });
   private JTextField newWord = new JTextField(20);
   private JTextArea sortedWords = new JTextArea(10, 20);
   private JButton addButton = new JButton("Ajouter");
}
