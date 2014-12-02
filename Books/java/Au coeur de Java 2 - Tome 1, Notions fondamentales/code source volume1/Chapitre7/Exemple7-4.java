Exemple 7.4 : DrawTest.java

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class DrawTest
{  
   public static void main(String[] args)
   {  
      DrawFrame frame = new DrawFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Un cadre contenant un panneau avec des dessins
*/
class DrawFrame extends JFrame
{
   public DrawFrame()
   {
      setTitle("DrawTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      // ajouter un panneau au cadre

      DrawPanel panel = new DrawPanel();
      add(panel);
   }

   public static final int DEFAULT_WIDTH = 400;
   public static final int DEFAULT_HEIGHT = 400;  
}

/**
   Un panneau qui affiche des rectangles et des ellipses. 
*/
class DrawPanel extends JPanel
{  
   public void paintComponent(Graphics g)
   {  
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D)g;

      // dessiner un rectangle

      double leftX = 100;
      double topY = 100;
      double width = 200;
      double height = 150;

      Rectangle2D rect = new Rectangle2D.Double(leftX, topY,
         width, height);
      g2.draw(rect);

      // dessiner l'ellipse à l'intérieur

      Ellipse2D ellipse = new Ellipse2D.Double();
      ellipse.setFrame(rect);
      g2.draw(ellipse);

      // tracer une ligne diagonale

      g2.draw(new Line2D.Double(leftX, topY, 
         leftX + width, topY + height));

      // dessiner un cercle ayant le même centre

      double centerX = rect.getCenterX();
      double centerY = rect.getCenterY();
      double radius = 150;

      Ellipse2D circle = new Ellipse2D.Double();
      circle.setFrameFromCenter(centerX, centerY,
         centerX + radius, centerY + radius);
      g2.draw(circle);
   }
}
