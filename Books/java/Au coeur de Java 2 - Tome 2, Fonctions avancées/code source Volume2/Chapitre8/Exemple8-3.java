Exemple 8.3 : EnumCombo.java

import java.util.*;
import javax.swing.*;

/**
   Une liste modifiable permettant aux utilisateurs de 
   choisir parmi plusieurs valeurs de champ statiques 
   dont les noms sont fournis dans le constructeur.
*/
public class EnumCombo extends JComboBox
{
   /**
      Construit un EnumCombo.
      @param cl une classe
      @param labels un tableau de noms de champs 
       statiques de cl
   */
   public EnumCombo(Class cl, String[] labels)
   {
      for (int i = 0; i < labels.length; i++)
      {
         String label = labels[i];
         String name = label.toUpperCase().replace(' ', '_');
         int value = 0;
         try
         {
            java.lang.reflect.Field f = cl.getField(name);
            value = f.getInt(cl);
         }
         catch (Exception e)
         {
            label = "(" + label + ")";
         }
         table.put(label, value);
         addItem(label);
      }
      setSelectedItem(labels[0]);
   }

   /**
      Renvoie la valeur du champ sélectionné par 
      l'utilisateur.
      @return la valeur de champ statique
   */
   public int getValue()
   {
      return table.get(getSelectedItem());
   }

   private Map<String, Integer> table = new TreeMap<String, Integer>();
}
