Exemple 5.14 : TableSelectionTest.java


import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import javax.swing.*;
import javax.swing.table.*;

/**
   Ce programme montre la sélection, l'ajout et le retrait de
   lignes et de colonnes.
*/
public class TableSelectionTest
{
   public static void main(String[] args)
   {
      JFrame frame = new TableSelectionFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);      
   }
}

/**
   Ce bloc présente une table de multiplication et des menus pour
   définir le mode de sélection de ligne/colonne/cellule et pour 
   ajouter et supprimer des lignes et des colonnes.
*/
class TableSelectionFrame extends JFrame
{
   public TableSelectionFrame()
   {
      setTitle("TableSelectionTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      // définit la table de multiplication

      model = new DefaultTableModel(10, 10);
      
      for (int i = 0; i < model.getRowCount(); i++)
         for (int j = 0; j < model.getColumnCount(); j++)
            model.setValueAt((i + 1) * (j + 1)), i, j);
      
      table = new JTable(model);

      add(new JScrollPane(table), "Center");

      removedColumns = new ArrayList<TableColumn>(); 

     // crée le menu

      JMenuBar menuBar = new JMenuBar();
      setJMenuBar(menuBar);      

      JMenu selectionMenu = new JMenu("Sélection");
      menuBar.add(selectionMenu);      

      final JCheckBoxMenuItem rowsItem
        = new JCheckBoxMenuItem("Lignes");
      final JCheckBoxMenuItem columnsItem
        = new JCheckBoxMenuItem("Colonnes");
      final JCheckBoxMenuItem cellsItem
        = new JCheckBoxMenuItem("Cellules");

      rowsItem.setSelected(table.getRowSelectionAllowed());
      columnsItem.setSelected(table.getColumnSelectionAllowed());
      cellsItem.setSelected(table.getCellSelectionEnabled());

      rowsItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               table.clearSelection();
               table.setRowSelectionAllowed(
                  rowsItem.isSelected());
               cellsItem.setSelected(
                  table.getCellSelectionEnabled());
            }
         });
      selectionMenu.add(rowsItem);

      columnsItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               table.clearSelection();
               table.setColumnSelectionAllowed(
                  columnsItem.isSelected());
               cellsItem.setSelected(
                  table.getCellSelectionEnabled());
            }
         });
      selectionMenu.add(columnsItem);

      cellsItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               table.clearSelection();
               table.setCellSelectionEnabled(
                  cellsItem.isSelected());
               rowsItem.setSelected(
                  table.getRowSelectionAllowed());
               columnsItem.setSelected(
                  table.getColumnSelectionAllowed());
            }
         });
      selectionMenu.add(cellsItem);
      
      JMenu tableMenu = new JMenu("Edition");
      menuBar.add(tableMenu);      

      JMenuItem hideColumnsItem = new  JMenuItem("Cacher les colonnes");
      hideColumnsItem.addActionListener(new
         ActionListener()
      {
         public void actionPerformed(ActionEvent event)
         {
            int[] selected = table.getSelectedColumns();
            TableColumnModel columnModel 
              = table.getColumnModel();
         
         /* supprime les colonnes de l'affichage, en commençant par le   
        dernier indice de sorte que les numéros de colonnes ne sont pas  
        affectés.
         */
         
         for (int i = selected.length - 1; i >= 0; i--)
         {
            TableColumn column 
               = columnModel.getColumn(selected[i]);
            table.removeColumn(column);
            
            // enregistre les colonnes supprimées pour la commande 
            // "Afficher les colonnes"
            
            removedColumns.add(column);
         }
      }
   });
      tableMenu.add(hideColumnsItem);

      JMenuItem showColumnsItem = new JMenuItem("Afficher les colonnes");
      showColumnsItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
              // restaure toutes les colonnes supprimées
             for (TableColumn tc : removedColumns)
                 table.addColumn(tc);
             removedColumns.clear();
             }
         });
      tableMenu.add(showColumnsItem);

      JMenuItem addRowItem = new JMenuItem("Ajouter une ligne");
      addRowItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               // ajoute une nouvelle ligne à la table de multiplication 
               // dans le modèle

         Integer[] newCells
            = new Integer[model.getColumnCount()];
         for (int i = 0; i < newCells.length; i++)
            newCells[i] = new Integer((i + 1) 
              * (model.getRowCount() + 1));
         model.addRow(newCells);
      }
   });
 tableMenu.add(addRowItem);

 JMenuItem removeRowsItem = new  JMenuItem("Supprime des lignes");
 removeRowsItem.addActionListener(new
    ActionListener()
    {
       public void actionPerformed(ActionEvent event)
       {
          int[] selected = table.getSelectedRows();

          for (int i = selected.length - 1; i >= 0; i--)
             model.removeRow(selected[i]);
        }
      });
      tableMenu.add(removeRowsItem);

      JMenuItem clearCellsItem = new  JMenuItem("Efface les cellules");
      clearCellsItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               for (int i = 0; i < table.getRowCount(); i++)
                  for (int j = 0; j < table.getColumnCount(); j++)
                     if (table.isCellSelected(i, j))
                        table.setValueAt(0, i, j);
            }
         });
      tableMenu.add(clearCellsItem);
   }
   
   private DefaultTableModel model;
   private JTable table;
   private ArrayList<TableColumn> removedColumns;

   private static final int DEFAULT_WIDTH = 400;
   private static final int DEFAULT_HEIGHT = 300;  
}

