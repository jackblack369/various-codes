Exemple 5.12 : TableSortTest.java

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

/**
   Ce programme montre comment trier une colonne de tableau.
   Double-cliquez sur une colonne de tableau pour la trier.
*/
public class TableSortTest
{
   public static void main(String[] args)
   {
      JFrame frame = new TableSortFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);      
   }
}

/**
   Ce bloc contient une table des données de planètes.
*/
class TableSortFrame extends JFrame
{  
   public TableSortFrame()
   {  
      setTitle("TableSortTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      // configure un modèle de table et interpose un élément de tri

      DefaultTableModel model
         = new DefaultTableModel(cells, columnNames);
      final SortFilterModel sorter = new SortFilterModel(model);

      // affiche la table

      final JTable table = new JTable(sorter);
      add(new JScrollPane(table), BorderLayout.CENTER);

      // configure un gestionnaire de double-clic pour les en-têtes de
      // colonnes
      
      table.getTableHeader().addMouseListener(new 
         MouseAdapter()
         {
            public void mouseClicked(MouseEvent event)
            {
               // vérifie les double-clics
               if (event.getClickCount() < 2) return;
               
               // trouve la colonne du clic et
               int tableColumn 
                  = table.columnAtPoint(event.getPoint());
                  
               // la transforme en indice dans le modèle du tableau et 
               // effectue le tri
               int modelColumn 
                  = table.convertColumnIndexToModel(tableColumn);
               sorter.sort(modelColumn);
            }
         });
   }
   
   private Object[][] cells =  
      {
         { "Mercure", 2440.0, 0, false, Color.yellow },
         { "Vénus", 6052.0, 0, false, Color.yellow },
         { "Terre", 6378.0, 1, false, Color.blue },
         { "Mars", 3397.0, 2, false, Color.red },
         { "Jupiter", 71492.0, 16, true, Color.orange },
         { "Saturne", 60268.0, 18, true, Color.orange },
         { "Uranus", 25559.0, 17, true, Color.blue },
         { "Neptune", 24766.0, 8, true, Color.blue },
         { "Pluton", 1137.0, 1, false, Color.black }
      };

   private String[] columnNames =
      { "Planète", "Rayon", "Lunes", "Gazeuse", "Couleur" };

   private static final int DEFAULT_WIDTH = 400;
   private static final int DEFAULT_HEIGHT = 200;
}

/**
   Ce modèle de table prend un modèle de table existant et produit
   un nouveau modèle qui trie les lignes afin que les entrées d'une
   colonne particulière sont triées.
*/
class SortFilterModel extends AbstractTableModel
{
   /**
      Construit un modèle de filtre de tri.
      @param m le modèle de table à filtrer
   */
   public SortFilterModel(TableModel m)
   {  
      model = m;
      rows = new Row[model.getRowCount()];
      for (int i = 0; i < rows.length; i++)
      {  
         rows[i] = new Row();
         rows[i].index = i;
      }
   }

   /**
      Trie les lignes.
      @param c la colonne à trier
   */
   public void sort(int c)
   {  
      sortColumn = c;
      Arrays.sort(rows);
      fireTableDataChanged();
   }

   // Calcule la ligne déplacée pour les trois méthodes qui accèdent
   // aux éléments de modèles

   public Object getValueAt(int r, int c)
      { return model.getValueAt(rows[r].index, c); }

   public boolean isCellEditable(int r, int c)
      { return model.isCellEditable(rows[r].index, c); }

   public void setValueAt(Object aValue, int r, int c)
   {  
      model.setValueAt(aValue, rows[r].index, c);
   }

   // délègue toutes les méthodes restantes au modèle

   public int getRowCount() { return model.getRowCount(); }
   public int getColumnCount() { return model.getColumnCount(); }
   public String getColumnName(int c)
      { return model.getColumnName(c); }
   public Class getColumnClass(int c) 
      { return model.getColumnClass(c); }

   /** 
      Cette classe intérieure contient l'indice de la ligne de modèle.
      Les lignes sont comparées en regardant les entrées de la ligne du 
      modèle dans la colonne de tri.
   */
   private class Row implements Comparable<Row>
   {  
      public int index;
      public int compareTo(Row other)
      {  
         Object a = model.getValueAt(index, sortColumn);
         Object b = model.getValueAt(other.index, sortColumn);
         if (a instanceof Comparable)
            return ((Comparable) a).compareTo(b);
         else
            return a.toString().compareTo(b.toString());
       }
   }

   private TableModel model;
   private int sortColumn;
   private Row[] rows;
}

