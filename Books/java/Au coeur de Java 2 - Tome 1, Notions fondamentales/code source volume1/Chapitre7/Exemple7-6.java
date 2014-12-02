Exemple 7.6 : FontTest.java

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import javax.swing.*;

public class FontTest
{  
   public static void main(String[] args)
   {  
      FontFrame frame = new FontFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Un cadre avec un panneau pour un message texte
*/
class FontFrame extends JFrame
{
   public FontFrame()
   {
      setTitle("FontTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      // ajouter un panneau au cadre

      FontPanel panel = new FontPanel();
      add(panel);
   }

   public static final int DEFAULT_WIDTH = 300;
   public static final int DEFAULT_HEIGHT = 200;  
}

/**
   Un panneau affichant un message centré dans une boîte.
*/
class FontPanel extends JPanel
{  
   public void paintComponent(Graphics g)
   {  
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D)g;

      String message = "Hello, World!";

      Font f = new Font("Serif", Font.BOLD, 36);
      g2.setFont(f);

      // mesurer la taille du message

      FontRenderContext context = g2.getFontRenderContext();
      Rectangle2D bounds = f.getStringBounds(message, context);
 
      // définir (x,y) = coin supérieur gauche du texte

      double x = (getWidth() - bounds.getWidth()) / 2;
      double y = (getHeight() - bounds.getHeight()) / 2;

      /* ajouter jambage ascendant à y pour
         atteindre la ligne de base */

      double ascent = -bounds.getY();
      double baseY = y + ascent;

      // écrire le message

      g2.drawString(message, (int)x, (int)(baseY);

      g2.setPaint(Color.GRAY);

      // dessiner la ligne de base

      g2.draw(new Line2D.Double(x, baseY, 
         x + bounds.getWidth(), baseY));

      // dessiner le rectangle englobant

      Rectangle2D rect = new Rectangle2D.Double(x, y, 
         bounds.getWidth(), 
         bounds.getHeight());
      g2.draw(rect);
   }
}

