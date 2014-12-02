Exemple 5.10 : InvestmentTable.java

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import javax.swing.*;
import javax.swing.table.*;

/**
   Ce programme vous montre comment construire un tableau à partir d'un 
   modèle de tableau.
*/
public class InvestmentTable
{
   public static void main(String[] args)
   {
      JFrame frame = new InvestmentTableFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);      
   }
}

/**
   Ce bloc contient le tableau d'investissement.
*/
class InvestmentTableFrame extends JFrame
{  
   public InvestmentTableFrame()
   {  
      setTitle("InvestmentTable");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      TableModel model = new InvestmentTableModel(30, 5, 10);
      JTable table = new JTable(model);
      add(new JScrollPane(table));
   }

   private static final int DEFAULT_WIDTH = 600;
   private static final int DEFAULT_HEIGHT = 300;
}

/** 
   Ce modèle de tableau calcule les entrées des cellules 
   chaque fois qu'elles sont demandées. Le contenu du tableau présente
   la croissance d'un investissement pendant un certain nombre d'années
   sous différents taux d'intérêt.
*/
class InvestmentTableModel extends AbstractTableModel
{
  /**
     Construit un modèle de tableau d'investissement.
     @param y le nombre d'années
     @param r1 le plus bas taux d'intérêt du tableau
     @param r2 le plus haut taux d'intérêt du tableau
  */
  public InvestmentTableModel(int y, int r1, int r2)
   {
      years = y;
      minRate = r1;
      maxRate = r2;
   }
   
   public int getRowCount() { return years; }
   
   public int getColumnCount() { return maxRate - minRate + 1; }
   
   public Object getValueAt(int r, int c)
   {
      double rate = (c + minRate) / 100.0;
      int nperiods = r;
      
      double futureBalance = INITIAL_BALANCE 
         * Math.pow(1 + rate, nperiods);

      return String.format("%.2f", futureBalance);
   }
   
   public String getColumnName(int c) { return (c + minRate) + "%"; }

   private int years;
   private int minRate;
   private int maxRate;
   
   private static double INITIAL_BALANCE = 100000.0;
}
