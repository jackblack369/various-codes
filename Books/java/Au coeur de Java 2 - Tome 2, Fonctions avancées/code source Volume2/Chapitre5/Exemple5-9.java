Exemple 5.9 : PlanetTable.java

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

/**
   Ce programme montre comment afficher un tableau simple.
*/
public class PlanetTable
{
   public static void main(String[] args)
   {
      JFrame frame = new PlanetTableFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);      
   }
}

/**
   ce bloc contient un tableau des données sur les planètes.
*/
class PlanetTableFrame extends JFrame
{
   public PlanetTableFrame()
   {
      setTitle("PlanetTable");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
      final JTable table = new JTable(cells, columnNames);
      add(new JScrollPane(table), BorderLayout.CENTER);
      JButton printButton = new JButton("Imprimer");
      printButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               try
               {
                  table.print();
               }
               catch (java.awt.print.PrinterException e)
               {
                  e.printStackTrace();
               }
            }
         });
       JPanel buttonPanel = new JPanel();
       buttonPanel.add(printButton);
       add(buttonPanel, BorderLayout.SOUTH);
}

private Object [][] cells =
{
   {"Mercure", 2440.0, 0, false, Color.yellow },
   {"Venus", 6052.0, 0, false, Color.yellow },
   {"Terre", 6378.0, 1, false, Color.blue },
   {"Mars", 3397.0, 2, false, Color.red },
   {"Jupiter", 71492.0, 16, true, Color.orange },
   {"Saturne", 60268.0, 18, true, Color.orange },
   {"Uranus", 25559.0, 17, true, Color.blue },
   {"Neptune", 24766.0, 8, true, Color.blue },
   {"Pluton", 1137.0, 1, false, Color.black }
};

   private String[] columnNames =
      { "Planète", "Rayon", "Lunes", "Gazeuse", "Couleur" };

   private static final int DEFAULT_WIDTH = 400;
   private static final int DEFAULT_HEIGHT = 200;
}
