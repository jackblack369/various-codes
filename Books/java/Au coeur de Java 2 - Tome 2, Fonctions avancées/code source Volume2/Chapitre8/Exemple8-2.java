Exemple 8.2 : DateFormatTest.java

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;
import javax.swing.*;

/**
   Ce programme montre la mise en forme des dates
   selon divers paramétrages régionaux.
*/
public class DateFormatTest
{
   public static void main(String[] args)
   {
      JFrame frame = new DateFormatFrame();
      frame.setDefaultCloseOperation(JFrame_EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Ce bloc contient les listes modifiables pour choisir
   un paramétrage local, une date et des formats 
   d'heure, des champs de texte pour afficher la date et   
   l'heure mises en forme pour analyser le contenu du 
   champ de texte ainsi qu'une case à cocher indulgente.
*/
class DateFormatFrame extends JFrame
{
   public DateFormatFrame()
   {
      setTitle("DateFormatTest");

      setLayout(new GridBagLayout());
      add(new JLabel("Param. rég."), new GBC(0, 0).setAnchor(GBC.EAST));
      add(new JLabel("Style date"), new GBC(0, 1).setAnchor(GBC.EAST));
      add(new JLabel("Style heure"), new GBC(2, 1).setAnchor(GBC.EAST));
      add(new JLabel("Date"), new GBC(0, 2).setAnchor(GBC.EAST));
      add(new JLabel("Heure"), new GBC(0, 3).setAnchor(GBC.EAST));
      add(localeCombo, new GBC(1, 0, 2, 1).setAnchor(GBC.WEST));
      add(dateStyleCombo, new GBC(1, 1).setAnchor(GBC.WEST));
      add(timeStyleCombo, new GBC(3, 1).setAnchor(GBC.WEST));
      add(dateParseButton, new GBC(3, 2).setAnchor(GBC.WEST));
      add(timeParseButton, new GBC(3, 3).setAnchor(GBC.WEST));
      add(lenientCheckbox, new GBC(0, 4, 2, 1).setAnchor(GBC.WEST));
      add(dateText, new GBC(1, 2, 2, 1).setFill(GBC.HORIZONTAL));
      add(timeText, new GBC(1, 3, 2, 1).setFill(GBC.HORIZONTAL));

      locales = DateFormat.getAvailableLocales();
      for (Locale loc : locales) 
         localeCombo.addItem(loc.getDisplayName());
      localeCombo.setSelectedItem(
         Locale.getDefault().getDisplayName());
      currentDate = new Date();
      currentTime = new Date();
      updateDisplay();

      ActionListener listener = new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               updateDisplay();
            }
         };

      localeCombo.addActionListener(listener);
      dateStyleCombo.addActionListener(listener);
      timeStyleCombo.addActionListener(listener);

      dateParseButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               String d = dateText.getText().trim();
               try
               {
                  currentDateFormat.setLenient
                     (lenientCheckbox.isSelected());
                  Date date = currentDateFormat.parse(d);
                  currentDate = date;
                  updateDisplay();
               }
               catch (ParseException e)
               {
                  dateText.setText("Erreur analyse : " + d);
               }
               catch (IllegalArgumentException e)
               {
                  dateText.setText("Erreur argument : " + d);
               }
            }
         });

      timeParseButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               String t = timeText.getText().trim();
               try
               {
                  currentDateFormat.setLenient
                     (lenientCheckbox.isSelected());
                  Date date = currentTimeFormat.parse(t);
                  currentTime = date;
                  updateDisplay();
               }
               catch (ParseException e)
               {
                  timeText.setText("Erreur analyse : " + t);
               }
               catch (IllegalArgumentException e)
               {
                  timeText.setText("Erreur argument : " + t);
               }
            }
         });
      pack();
   }

   /**
      Met à jour l'affichage et formate la date selon
      les réglages de l'utilisateur.
   */
   public void updateDisplay()
   {
      Locale currentLocale = locales[
         localeCombo.getSelectedIndex()];
      int dateStyle = dateStyleCombo.getValue();
      currentDateFormat
         = DateFormat.getDateInstance(dateStyle,
         currentLocale);
      String d = currentDateFormat.format(currentDate);
      dateText.setText(d);
      int timeStyle = timeStyleCombo.getValue();
      currentTimeFormat
         = DateFormat.getTimeInstance(timeStyle,
         currentLocale);
      String t = currentTimeFormat.format(currentTime);
      timeText.setText(t);
   }

   private Locale[] locales;

   private Date currentDate;
   private Date currentTime;
   private DateFormat currentDateFormat;
   private DateFormat currentTimeFormat;

   private JComboBox localeCombo = new JComboBox();
   private EnumCombo dateStyleCombo
      = new EnumCombo(DateFormat.class,
        new String[] { "Default", "Full", "Long",
        "Medium", "Short" });
   private EnumCombo timeStyleCombo
      = new EnumCombo(DateFormat.class,
        new String[] { "Default", "Full", "Long",
        "Medium", "Short" });
   private JButton dateParseButton = new JButton("Analyse date");
   private JButton timeParseButton = new JButton("Analyse heure");
   private JTextField dateText = new JTextField(30);
   private JTextField timeText = new JTextField(30);
   private JTextField parseText = new JTextField(30);
   private JCheckBox lenientCheckbox
      = new JCheckBox("Analyse indulgente", true);
}


