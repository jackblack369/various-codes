Exemple 8.5 : LocaleCombo.java

import java.awt.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

/**
   Cette liste permet de choisir un paramètre régional. Les paramètres
   sont affichés dans le paramètre de la liste et triés selon le tri 
   des paramètres régionaux affichés.
*/
public class LocaleCombo extends JComboBox
{
   /**
      Construit une liste de paramètres qui affiche une collection 
      inaltérable de paramètres régionaux.
      @param locales Les paramètres à afficher dans cette liste
   */
   public LocaleCombo(Locale[] locales) 
   {
      this.locales = (Locale[]) locales.clone();
      sort();
      setSelectedItem(getLocale());
   }

   public void setLocale(Locale newValue) 
   { 
      super.setLocale(newValue); 
      sort();
   }

   private void sort()
   {
      Object selected = getSelectedItem();
      final Locale loc = getLocale();
      final Collator collator = Collator.getInstance(loc);
      final Comparator<Locale> comp = new
         Comparator<Locale>()
         {
            public int compare(Locale a, Locale b)
            {
               return collator.compare(a.getDisplayName(loc), 
                  b.getDisplayName(loc));
            }
         };
      Arrays.sort(locales, comp);      
      setModel(new
         ComboBoxModel()
         {
            public Object getElementAt(int i) { return locales[i]; }
            public int getSize() { return locales.length; }
            public void addListDataListener(ListDataListener l) {}
            public void removeListDataListener(ListDataListener l) {}
            public Object getSelectedItem() { 
               return selected >= 0 ? locales[selected] : null; }
            public void setSelectedItem(Object anItem) 
            { 
               if (anItem == null) selected = -1; 
               else selected = Arrays.binarySearch(
                  locales, (Locale) anItem, comp);                
            }

            private int selected;
         });         
      setSelectedItem(selected);
   }

   public ListCellRenderer getRenderer()
   {
      if (renderer == null)
      {
         final ListCellRenderer originalRenderer = super.getRenderer();
         if (originalRenderer == null) return null;
         renderer = new 
            ListCellRenderer()
            {
               public Component getListCellRendererComponent(JList list,
                  Object value, int index, boolean isSelected, 
                  boolean cellHasFocus)
               {
                  String renderedValue = ((Locale) 
                     value).getDisplayName(getLocale());
                  return originalRenderer.getListCellRendererComponent(
                     list, renderedValue, index, isSelected, 
                     cellHasFocus);
               }               
            };
      }
      return renderer;
   }

   public void setRenderer(ListCellRenderer newValue)
   {  
      renderer = null;
      super.setRenderer(newValue);
   }
