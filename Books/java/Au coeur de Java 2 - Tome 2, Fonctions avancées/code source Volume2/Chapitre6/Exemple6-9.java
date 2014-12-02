Exemple 6.9 : DoubleArrayEditorPanel.java

package com.horstmann.corejava;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.lang.reflect.*;
import java.beans.*;
import javax.swing.*;
import javax.swing.event.*;

/**
   Le panneau qui se trouve dans DoubleArrayEditor. Il contient une
   liste des valeurs du tableau, ainsi que des boutons permettant
   de redimensionner le tableau et de modifier la valeur de liste 
   actuellement sélectionnée.
*/
public class DoubleArrayEditorPanel extends JPanel
{
   public DoubleArrayEditorPanel(PropertyEditorSupport ed)
   {
      editor = ed;
      setArray((double[])ed.getValue());

      setLayout(new GridBagLayout());

      add(sizeField, new GBC(0, 0, 1, 1).setWeight(100, 
         0).setFill(GBC.HORIZONTAL));
      add(valueField, new GBC, 0, 1, 1, 1).setWeight(100,
         0).setFill(GBC.HORIZONTAL));
      add(sizeButton, new GBC, 1, 0, 1, 1).setWeight(100, 0));
      add(valueButton, new GBC, 1, 1, 1, 1).setWeight(100, 0));
      add(new JScrollPane(elementList), new GBC(0, 2, 2, 
         1).setWeight(100, 100).setFill(GBC.BOTH));

      sizeButton.addActionListener(new 
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
               { changeSize(); }
         });

       valueButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
               { changeValue(); }
         });


      elementList.setSelectionMode(
        ListSelectionModel.SINGLE_SELECTION);

      elementList.addListSelectionListener(new
         ListSelectionListener()
         {
            public void valueChanged(ListSelectionEvent event)
            {
               int i = elementList.getSelectedIndex();
               if (i < 0) return;
               valueField.setText("" + array[i]);
            }
         });

      elementList.setModel(model);
      elementList.setSelectedIndex(0);
   }

   /**
      Cette méthode est appelée lorsque l'utilisateur souhaite
      modifier la taille du tableau.
   */
   public void changeSize()
   {
      fmt.setParseIntegerOnly(true);
      int s = 0;
      try
      {
         s = fmt.parse(sizeField.getText()).intValue();
         if (s < 0)
            throw new ParseException("Hors limites", 0);
      }
      catch(ParseException e)
      {
         JOptionPane.showMessageDialog(this, "" + e,
            "Erreur de saisie", JOptionPane.WARNING_MESSAGE);
         sizeField.requestFocus();
         return;
      }
      if (s == array.length) return;
      setArray((double[])arrayGrow(array, s));
      editor.setValue(array);
      editor.firePropertyChange();
   }

   /**
      Cette méthode est appelée lorsque l'utilisateur souhaite modifier 
      la valeur de tableau actuellement sélectionnée.
   */
   public void changeValue()
   {
      double v = 0;
      fmt.setParseIntegerOnly(false);
      try
      {
         v = fmt.parse(valueField.getText()).doubleValue();
      }
      catch (ParseException e)
      {
         JOptionPane.showMessageDialog(this, "" + e,
            "Erreur de saisie", JOptionPane.WARNING_MESSAGE);
         valueField.requestFocus();
         return;
      }
      int currentIndex = elementList.getSelectedIndex();
      setArray(currentIndex, v);
      editor.firePropertyChange();
   }

   /**
      Définit la propriété du tableau indexé.
      @param v le tableau à modifier
   */
   public void setArray(double[] v)
   {
      if (v == null) array = new double[0];
      else array = v;
      model.setArray(array);
      sizeField.setText("" + array.length);
      if (array.length > 0)
      {
         valueField.setText("" + array[0]);
         elementList.setSelectedIndex(0);
      }
      else
         valueField.setText("");
   }

   /**
      Récupère la propriété du tableau indexé.
      @return le tableau modifié
   */
   public double[] getArray()
   {
      return (double[]) array.clone();
   }

   /**
      Définit la propriété du tableau indexé.
      @param i l'index dont la valeur est à définir
      @param value la nouvelle valeur pour l'index donné
   */
   public void setArray(int i, double value)
   {
      if (0 <= i && i < array.length)
      {
         model.setValue(i, value);
         elementList.setSelectedIndex(i);
         valueField.setText("" + value);
      }
   }

   /**
      Récupère la propriété du tableau indexé.
      @param i l'index dont la valeur est à récupérer
      @return la valeur à l'index donné
   */
   private double getArray(int i)
   {
      if (0 <= i && i < array.length) return array[i];
      return 0;
   }

   /**
      Redimensionne un tableau.
      @param a le tableau à agrandir
      @param newLength la nouvelle longueur
      @return un tableau avec la longueur donnée et les mêmes éléments 
      que a aux positions habituelles
   */
   private static Object arrayGrow(Object a, int newLength)
   {
      Class cl = a.getClass();
      if (!cl.isArray()) return null;
      Class componentType = a.getClass().getComponentType();
      int length = Array.getLength(a);

      Object newArray = Array.newInstance(componentType,
         newLength);
      System.arraycopy(a, 0, newArray, 0,
         Math.min(length, newLength));
      return newArray;
   }

   private PropertyEditorSupport editor;
   private double[] array;
   private NumberFormat fmt = NumberFormat.getNumberInstance();
   private JTextField sizeField = new JTextField(4);
   private JTextField valueField = new JTextField(12);
   private JButton sizeButton = new JButton("Redim");
   private JButton valueButton = new JButton("Modif");
   private JList elementList = new JList();
   private DoubleArrayListModel model = new DoubleArrayListModel();
}

/**
   Le modèle de liste pour la liste d'éléments dans l'éditeur.
*/
class DoubleArrayListModel extends AbstractListModel
{
   public int getSize() { return array.length; }

   public Object getElementAt(int i)
      { return "[" + i + "] " + array[i]; }

   /**
      Définit un nouveau tableau à afficher dans la liste.
      @param a le nouveau tableau
   */
   public void setArray(double[] a)
   {
      int oldLength = array == null ? 0 : array.length;
      array = a;
      int newLength = array == null ? 0 : array.length;
      if (oldLength > 0) fireIntervalRemoved(this, 0, oldLength);
      if (newLength > 0) fireIntervalAdded(this, 0, newLength);
   }

   /**
      Modifie une valeur dans le tableau à afficher dans la liste.
      @param i l'index dont la valeur doit changer
      @param value la nouvelle valeur pour l'index donné
   */
   public void setValue(int i, double value)
   {
      array[i] = value;
      fireContentsChanged(this, i, i);
   }

   private double[] array;
}

