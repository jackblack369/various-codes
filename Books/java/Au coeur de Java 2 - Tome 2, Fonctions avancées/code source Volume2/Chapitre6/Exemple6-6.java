Exemple 6.6 : InverseEditor.java

package com.horstmann.corejava;

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.beans.*;

/**
   L'éditeur de propriété pour la propriété inverse du ChartBean.
   La propriété inverse bascule entre des barres du graphique et
   un fond coloré.
*/
public class InverseEditor extends PropertyEditorSupport
{
   public Component getCustomEditor()
      { return new InverseEditorPanel(this); }
   public boolean supportsCustomEditor() { return true; }
   public boolean isPaintable() { return true; }
   public String getAsText() { return null; }
   public String getJavaInitializationString()
     { return "" + getValue(); }

   public void paintValue(Graphics g, Rectangle box)
   {
      Graphics2D g2 = (Graphics2D)g;    
      boolean isInverse = (Boolean) getValue();
      String s = isInverse ? "Inverse" : "Normal";
      g2.setPaint(isInverse ? Color.black : Color.white);
      g2.fill(box);
      g2.setPaint(isInverse ? Color.white : Color.black);
      FontRenderContext context = g2.getFontRenderContext();
      Rectangle2D stringBounds = g2.getFont().getStringBounds(
         s, context);
      double w = stringBounds.getWidth();
      double x = box.x;
      if (w < box.width) x += (box.width - w) / 2;
      double ascent = -stringBounds.getY();
      double y = box.y
         + (box.height – stringBounds.getHeight()) / 2 + ascent;
      g.drawString(s, (float) x, (float) y);
   }
}
