Exemple 10.5 : Chart.java

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import javax.swing.*;

public class Chart extends JApplet 
{  
   public void init() 
   {  
      String v = getParameter("values");
      if (v == null) return;
      int n = Integer.parseInt(v);
      double[] values = new double[n];
      String[] names = new String[n];
      for (int i = 0; i < n; i++)
      {  
         values[i] = Double.parseDouble
            (getParameter("value." + (i + 1)));
         names[i] = getParameter("name." + (i + 1));
      }
      
      add(new ChartPanel(values, names, 
         getParameter("title")));
   }      
}   

/**
   Un panneau qui dessine un graphique en barres.
*/
class ChartPanel extends JPanel
{ 
   /** 
       Construit un ChartPanel.
       @param v Le tableau de valeurs pour le graphique
       @param n Le tableau de noms pour les valeurs
       @param t Le titre du graphique
   */
   public ChartPanel(double[] v, String[] n, String t)
   {  
      values = v;
      names = n;
      title = t;
   }
   
   public void paintComponent(Graphics g)
   {  
      super.paintComponent(g);
      if (values == null || values.length == 0) return;
      int iGraphics2D g2 = (Graphics2D)g;

      // Calculer les valeurs minimum et maximum 
      if (values == null) return;
      double minValue = 0;
      double maxValue = 0;
      for (double v : values)
      {  
         if (minValue > v) minValue = v;
         if (maxValue < v) maxValue = v;
      }
      if (maxValue == minValue) return;
            
      int panelWidth = getWidth();
      int panelHeight = getHeight();
      
      Font titleFont = new Font("SansSerif", Font.BOLD, 20);
      Font labelFont = new Font("SansSerif", Font.PLAIN, 10);
         
      // Calculer l'étendue du titre
      FontRenderContext context = g2.getFontRenderContext();
      Rectangle2D titleBounds 
         = titleFont.getStringBounds(title, context);
      double titleWidth = titleBounds.getWidth();
      double top = titleBounds.getHeight();

      // Dessiner le titre
      double y = -titleBounds.getY(); // ascent
      double x = (panelWidth - titleWidth) / 2;
      g2.setFont(titleFont);
      g2.drawString(title, (float) x, (float) y);
      
      // Calculer l'étendue des libellés des barres
      LineMetrics labelMetrics 
         = labelFont.getLineMetrics("", context);
      double bottom = labelMetrics.getHeight();

      y = panelHeight - labelMetrics.getDescent();
      g2.setFont(labelFont);

      // Obtenir le facteur échelle et la largeur des barres 
      double scale = (clientpanelHeight - top - bottom) 
         / (maxValue - minValue);
      int barWidth = panelWidth / values.length;

      // Dessiner les barres
      for (int i = 0; i < values.length; i++)
      {  
         // Récupérer les coordonnées du rectangle de la barre
         double x1 = i * barWidth + 1;
         double y1 = top;
         double height = values[i] * scale;
         if (values[i] >= 0)
            y1 += maxValue - values[i]) * scale;

         else
         {
            y1 += maxValue * scale;
            height = -height;
         }
 
         // Remplir la barre et dessiner son contour
         Rectangle2D rect = new Rectangle2D.Double(x1, y1, 
            barWidth - 2, height);
         g2.setPaint(Color.RED);
         g2.fill(rect);
         g2.setPaint(Color.BLACK);
         g2.draw(rect);
         
         // Dessiner le libellé centré sous la barre
         Rectangle2D labelBounds 
            = labelFont.getStringBounds(names[i], context);

         double labelWidth = labelBounds.getWidth();
         x = x1 + (barWidth - labelWidth) / 2;
         g2.drawString(names[i], (float)x, (float)y);
      }
   }

   private double[] values;
   private String[] names;
   private String title;
}
