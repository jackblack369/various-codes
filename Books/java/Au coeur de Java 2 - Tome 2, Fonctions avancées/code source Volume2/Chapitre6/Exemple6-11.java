Exemple 6.11 : ChartBean2Customizer.java

package com.horstmann.corejava;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

/**
   Un customizer destiné au Chartbean et qui permet à l'utilisateur
   de modifier toutes les propriétés de chart dans une seule
   boîte de dialogue à onglets.
*/
public class ChartBean2Customizer extends JTabbedPane
   implements Customizer
{
   public ChartBean2Customizer()
   {
      data = new JTextArea();
      JPanel dataPane = new JPanel();
      dataPane.setLayout(new BorderLayout());
      dataPane.add(new JScrollPane(data), BorderLayout.CENTER);
      JButton dataButton = new JButton("Définir données");
      dataButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
               { setData(data.getText()); }
         });
      JPanel p = new JPanel();
      p.add(dataButton);
      dataPane.add(p, BorderLayout.SOUTH);

      JPanel colorPane = new JPanel();
      colorPane.setLayout(new BorderLayout());

      normal = new JCheckBox("Normal", true);
      inverse = new JCheckBox("Inverse", false);
      p = new JPanel();
      p.add(normal);
      p.add(inverse);
      ButtonGroup g = new ButtonGroup();
      g.add(normal);
      g.add(inverse);
      normal.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
               { setInverse(false); }
         });

      inverse.addActionListener(
         new ActionListener()
         {
            public void actionPerformed(ActionEvent event)
               { setInverse(true); }
         });
 
     colorEditor
         = PropertyEditorManager.findEditor(Color.class);
     colorEditor.addPropertyChangeListener(
         new PropertyChangeListener()
         {
            public void propertyChange(PropertyChangeEvent
               event)
            {
               setGraphColor((Color) colorEditor.getValue());
            }
         });

      colorPane.add(p, BorderLayout.NORTH);
      colorPane.add(colorEditor.getCustomEditor(),
         BorderLayout.CENTER);

      JPanel titlePane = new JPanel();
      titlePane.setLayout(new BorderLayout());

      g = new ButtonGroup();
      position = new JCheckBox[3];
      position[0] = new JCheckBox("Gauche", false);
      position[1] = new JCheckBox("Centré", true);
      position[2] = new JCheckBox("Droite", false);

      p = new JPanel();
      for (int i = 0; i < position.length; i++)
      {
         final int value = i;
         p.add(position[i]);
         g.add(position[i]);
         position[i].addActionListener(new
            ActionListener()
            {
               public void actionPerformed(ActionEvent event)
                  { setTitlePosition(value); }
            });
      }

      titleField = new JTextField();
      titleField.getDocument().addDocumentListener(
         new DocumentListener()
         {
            public void changedUpdate(DocumentEvent evt)
               { setTitle(titleField.getText()); }
            public void insertUpdate(DocumentEvent evt)
               { setTitle(titleField.getText()); }
            public void removeUpdate(DocumentEvent evt)
               { setTitle(titleField.getText()); }
         });

      titlePane.add(titleField, BorderLayout.NORTH);
      titlePane.add(p, BorderLayout.SOUTH);
      addTab("Couleur", colorPane);
      addTab("Titre", titlePane);
      addTab("Données", dataPane);

   /**
      Définit les données à afficher dans le graphique.
      @param s une chaîne contenant les nombres à afficher,
      séparés par un espace
   */
   public void setData(String s)
   {
      StringTokenizer tokenizer = new StringTokenizer(s);

      int i = 0;
      double[] values = new double[tokenizer.countTokens()];
      while (tokenizer.hasMoreTokens())
      {
         String token = tokenizer.nextToken();
         try
         {
            values[i] = Double.parseDouble(token);
            i++;
         }
         catch (NumberFormatException e)
         {
         }
      }
      setValues(values);
   }

   /**
      Définit le titre du graphique.
      @param newValue le nouveau titre
   */
   public void setTitle(String newValue)
   {
      if (bean == null) return;
      String oldValue = bean.getTitle();
      bean.setTitle(newValue);
      firePropertyChange("title", oldValue, newValue);
   }

   /**
      Définit la position de titre du graphique.
      @param i la nouvelle position de titre (ChartBean2.LEFT,
      ChartBean2.CENTER ou ChartBean2.RIGHT)
   */
   public void setTitlePosition(int i)
   {
      if (bean == null) return;
      Integer oldValue = new Integer(bean.getTitlePosition());
      Integer newValue = new Integer(i);
      bean.setTitlePosition(i);
      firePropertyChange("titlePosition", oldValue, newValue);
   }

   /**
      Définit le réglage inverse du graphique.
      @param b true si les couleurs du graphique et du fond sont inversées
   */
   public void setInverse(boolean b)
   {
      if (bean == null) return;
      Boolean oldValue = new Boolean(bean.isInverse());
      Boolean newValue = new Boolean(b);
      bean.setInverse(b);
      firePropertyChange("inverse", oldValue, newValue);
   }

   /**
      Définit les valeurs à afficher dans le graphique.
      @param newValue le nouveau tableau des valeurs
   */
   public void setValues(double[] newValue)
   {
      if (bean == null) return;
      double[] oldValue = bean.getValues();
      bean.setValues(newValue);
      firePropertyChange("values", oldValue, newValue);
   }

   /**
      Définit la couleur du graphique.
      @param newValue la nouvelle couleur
   */
   public void setGraphColor(Color newValue)
   {
      if (bean == null) return;
      Color oldValue = bean.getGraphColor();
      bean.setGraphColor(newValue);
      firePropertyChange("graphColor", oldValue, newValue);
   }

   public void setObject(Object obj)
   {
      bean = (ChartBean2) obj;

      data.setText("");
      for (double value : bean.getValues())
         data.append(value +  "\n");

      normal.setSelected(!bean.isInverse());
      inverse.setSelected(bean.isInverse());

      titleField.setText(bean.getTitle());

      for (int i = 0; i < position.length; i++)
         position[i].setSelected(i == bean.getTitlePosition());

      colorEditor.setValue(bean.getGraphColor());
   }

   public Dimension getPreferredSize()
      { return new Dimension(XPREFSIZE, YPREFSIZE); }

   private static final int XPREFSIZE = 200;
   private static final int YPREFSIZE = 120;
   private ChartBean2 bean;
   private PropertyEditor colorEditor;

   private JTextArea data;
   private JCheckBox normal;
   private JCheckBox inverse;
   private JCheckBox[] position;
   private JTextField titleField;
}
