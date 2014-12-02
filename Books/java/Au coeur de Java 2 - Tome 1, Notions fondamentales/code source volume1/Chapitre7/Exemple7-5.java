Exemple 7.5 : FillTest.java

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class FillTest
{  
   public static void main(String[] args)
   {  
      FillFrame frame = new FillFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Un cadre contenant un panneau avec des dessins 
*/
class FillFrame extends JFrame
{
   public FillFrame()
   {
      setTitle("FillTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      // ajouter un panneau au cadre

       FillPanel panel = new FillPanel();
       add(panel);
   }

   public static final int DEFAULT_WIDTH = 400;
   public static final int DEFAULT_HEIGHT = 400;  
}

/**
   Un panneau affichant des rectangles et ellipses colorés
*/
class FillPanel extends JPanel
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
      g2.setPaint(Color.RED);
      g2.fill(rect);

      // dessiner l'ellipse englobée

      Ellipse2D ellipse = new Ellipse2D.Double();
      ellipse.setFrame(rect);
      g2.setPaint(new Color(0,  128, 128)); // bleu-vert foncé
      g2.fill(ellipse);
   }
}

