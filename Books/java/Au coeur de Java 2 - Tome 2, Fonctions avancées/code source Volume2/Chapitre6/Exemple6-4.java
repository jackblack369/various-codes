Exemple 6.4 : ChartBean.java

package com.horstmann.corejava;

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.util.*;
import java.beans.*;
import java.io.*;
import javax.swing.*;

/**
   Un bean pour dessiner un graphique en barres.
*/
public class ChartBean extends JPanel
{
   public void paint(Graphics g)
   {
      Graphics2D g2 = (Graphics2D)g;

      if (values == null || values.length == 0) return;
      double minValue = 0;
      double maxValue = 0;
      for (int i = 0; i < values.length; i++)
      {
         if (minValue > getValues(i)) minValue = getValues(i);
         if (maxValue < getValues(i)) maxValue = getValues(i);
      }
      if (maxValue == minValue) return;

      Dimension d = getSize();
      Rectangle2D bounds = getBounds();
      double clientWidth = bounds.getWidth();
      double clientHeight = bounds.getHeight();
      double barWidth = clientWidth / values.length;

      g2.setPaint(inverse ? color : Color.white);
      g2.fill(bounds);
      g2.setPaint(Color.black);

      Font titleFont = new Font("SansSerif", Font.BOLD, 20);
      FontRenderContext context = g2.getFontRenderContext();
      Rectangle2D titleBounds 
         = titleFont.getStringBounds(title, context);

      double titleWidth = titleBounds.getWidth();
      double y = -titleBounds.getY();
      double x;
      if (titlePosition == LEFT)
         x = 0;
      else if (titlePosition == CENTER)
         x = (clientWidth - titleWidth) / 2;
      else
         x = clientWidth - titleWidth;

      g2.setFont(titleFont);
      g2.drawString(title, (float)x, (float)y);

      double top = titleBounds.getHeight();
      double scale = (clientHeight - top)
         / (maxValue - minValue);
      y = clientHeight;

      for (int i = 0; i < values.length; i++)
      {
         double x1 = i * barWidth + 1;
         double y1 = top;
         double value = getValues(i);
         double height = value * scale;
         if (Value >= 0)
            y1 += (maxValue - value) * scale);
         else
         {
            y1 += (int)(maxValue * scale);
            height = -height;
         }

         g2.setPaint(inverse ? Color.white : color);
         Rectangle2D bar = new Rectangle2D.Double(x1, y1, 
            barWidth - 2, height);
         g2.fill(bar);
         g2.setPaint(Color.black);
         g2.draw(bar);
      }
   }

   /**
      Définit la propriété de titre.
      @param t le nouveau titre du graphique.
   */
   public void setTitle(String t) { title = t; }

   /**
      Récupère la propriété du titre.
      @return le titre du graphique
   */
   public String getTitle() { return title; }

   /**
      Définit la propriété des valeurs indexées.
      @param v les valeurs à afficher dans le graphique
   */
   public void setValues(double[] v) { values = v; }
 
   /**
      Récupère la propriété des valeurs indexées.
      @return les valeurs à afficher dans le graphique.
   */
   public double[] getValues() { return values; }

   /**
      Définit la propriété des valeurs indexées.
      @param i l'index de la valeur à définir
      @param value la nouvelle valeur de l'index
   */
   public void setValues(int i, double value)
   {
      if (0 <= i && i < values.length) values[i] = value;
   }

   /**
      Récupère la propriété des valeurs indexées.
      @param i l'index de la valeur à obtenir
      @return la valeur de cet index
   */
   public double getValues(int i)
   {  
      if (0 <= i && i < values.length) return values[i];
      return 0;
   }

   /**
      Définit la propriété inverse.
      @param b true si l'affichage est inversé (barres blanches sur fond
      coloré)
   */
    public void setInverse(boolean b) { inverse = b; }
 
   /**
      Récupère la propriété inverse.
      @return true si l'affichage est inversé
   */
   public boolean isInverse() { return inverse; }

   /**
      Définit la propriété titlePosition.
      @param p LEFT, CENTER ou RIGHT
   */
   public void setTitlePosition(int p) { titlePosition = p; }

   /**
      Récupère la propriété titlePosition.
      @return LEFT, CENTER ou RIGHT
   */
   public int getTitlePosition() { return titlePosition; }

   /**
      Définit la propriété graphColor.
      @param c la couleur à utiliser pour le graphe
   */
   public void setGraphColor(Color c) { color = c; }
  
   /**
      Récupère la propriété graphColor.
      @param c la couleur à utiliser pour le graphe
   */
   public Color getGraphColor() { return color; }

   public Dimension getPreferredSize()
   {  
      return new Dimension(XPREFSIZE, YPREFSIZE);
   }

   private static final int LEFT = 0;
   private static final int CENTER = 1;
   private static final int RIGHT = 2;

   private static final int XPREFSIZE = 300;
   private static final int YPREFSIZE = 300;
   private double[] values = { 1, 2, 3 };
   private String title = "Titre";
   private int titlePosition = CENTER;
   private boolean inverse;
   private Color color = Color.red;
}
