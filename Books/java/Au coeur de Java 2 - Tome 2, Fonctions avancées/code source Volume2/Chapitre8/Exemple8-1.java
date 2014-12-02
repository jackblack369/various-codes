Exemple 8.1 : NumberFormatTest.java

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;
import javax.swing.*;

/**
   Ce programme montre la mise en forme des nombres
   en fonction de divers paramétrages régionaux.
*/
public class NumberFormatTest
{
   public static void main(String[] args)
   {
      JFrame frame = new NumberFormatFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Ce bloc contient des boutons permettant de 
   sélectionner un format de nombre, une zone de liste
   modifiable pour choisir un paramètre régional, un 
   champ de texte pour afficher un numéro mis en forme
   ainsi qu'un bouton pour analyser le contenu du champ 
   de texte.
*/  
class NumberFormatFrame extends JFrame
{
   public NumberFormatFrame()
   {
      setTitle("NumberFormatTest");
      setLayout(new GridBagLayout());

      ActionListener listener = new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
               { updateDisplay(); }
         };

      JPanel p = new JPanel();
      addRadioButton(p, numberRadioButton, rbGroup, listener);
      addRadioButton(p, currencyRadioButton, rbGroup, listener);
      addRadioButton(p, percentRadioButton, rbGroup, listener);

      add(new JLabel("Param. Rég."), new GBC(0, 0).setAnchor(GBC.EAST));
      add(p, new GBC(1, 1));
      add(parseButton, new GBC(0, 2).setInsets(2));
      add(localeCombo, new GBC(1, 0).setAnchor(GBC.WEST));
      add(numberText, new GBC(1, 2).setFill(GBC.HORIZONTAL));
      locales = NumberFormat.getAvailableLocales();
      for (Locale loc : locales)
        localeCombo.addItem(loc.getDisplayName());
      localeCombo.setSelectedItem(
         Locale.getDefault().getDisplayName());
      currentNumber = 123456.78;
      updateDisplay();

      localeCombo.addActionListener(listener);

      parseButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               String s = numberText.getText().trim();
               try
               {
                  Number n = currentNumberFormat.parse(s);
                  if (n != null)
                  {
                     currentNumber = n.doubleValue();
                     updateDisplay();
                  }
                  else
                  {
                     numberText.setText("Erreur analyse : " + s);
                  }
               }
               catch (ParseException e)
               {
                  numberText.setText("Erreur analyse : " + s);
               }
            }
         });
      pack();
   }

   /**
      Ajoute un bouton à un conteneur.
      @param p le conteneur dans lequel placer le bouton
      @param b le bouton
      @param g le groupe de boutons
      @param listener l'écouteur de boutons
   */
   public void addRadioButton(Container p, JRadioButton b,
      ButtonGroup g, ActionListener listener)
   {
      b.setSelected(g.getButtonCount() == 0);
      b.addActionListener(listener);
      g.add(b);
      p.add(b);
   }

   /**
      Actualise l'affichage et formate le nombre 
      en fonction des réglages de l'utilisateur.
   */
   public void updateDisplay()
   {
      Locale currentLocale = locales[
         localeCombo.getSelectedIndex()];
      currentNumberFormat = null;
      if (numberRadioButton.isSelected())
         currentNumberFormat
            = NumberFormat.getNumberInstance(currentLocale);
      else if (currencyRadioButton.isSelected())
         currentNumberFormat
            = NumberFormat.getCurrencyInstance(currentLocale);
      else if (percentRadioButton.isSelected())
         currentNumberFormat
            = NumberFormat.getPercentInstance(currentLocale);
      String n = currentNumberFormat.format(currentNumber);
      numberText.setText(n);
   }

   private Locale[] locales;

   private double currentNumber;

   private JComboBox localeCombo = new JComboBox();
   private JButton parseButton = new JButton("Analyse");
   private JTextField numberText = new JTextField(30);
   private JRadioButton numberRadioButton = new JRadioButton("Nombre");
   private JRadioButton currencyRadioButton = new 
      JRadioButton("Monnaie");
   private JRadioButton percentRadioButton = new 
      JRadioButton("Pourcentage");
   private ButtonGroup rbGroup = new ButtonGroup();
   private NumberFormat currentNumberFormat;
}


