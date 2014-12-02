Exemple 7.7 : ImageTest.java

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
 
public class ImageTest
{  
   public static void main(String[] args)
   {  
      ImageFrame frame = new ImageFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}
 
/** 
    Un cadre avec un panneau pour une image
*/
class ImageFrame extends JFrame
{
   public ImageFrame()
   {
      setTitle("ImageTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
 
      // ajouter un panneau au cadre

      ImagePanel panel = new ImagePanel();
      add(panel);
   }

   public static final int DEFAULT_WIDTH = 300;
   public static final int DEFAULT_HEIGHT = 200;  
}

/**
   Un panneau qui affiche une image en mosaïque
*/
class ImagePanel extends JPanel
{  
   public ImagePanel()
   {  
      // acquérir l'image
      try
      {
         image = ImageIO.read(new File("blue-ball.gif"));
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
   }

   public void paintComponent(Graphics g)
   {  
      super.paintComponent(g);
      if (image == null) return;
    
      int imageWidth = image.getWidth(this);
      int imageHeight = image.getHeight(this);
 
      // dessiner l'image dans le coin supérieur gauche
       
      g.drawImage(image, 0, 0, null);
 
      // afficher l'image en mosaïque dans le panneau 
 
      for (int i = 0; i * imageWidth <= getWidth(); i++)
         for (int j = 0; j * imageHeight <= getHeight(); j++)
            if (i + j > 0) 
               g.copyArea(0, 0, imageWidth, imageHeight,
                  i * imageWidth, j * imageHeight);
   }
    
   private Image image;
}
