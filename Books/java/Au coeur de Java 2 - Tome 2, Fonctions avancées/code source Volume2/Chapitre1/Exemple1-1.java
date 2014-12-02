Exemple 1.1 : Bounce.java


import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

/**
   Présente une balle rebondissante animée.
*/
public class Bounce
{
   public static void main(String[] args)
   {
      JFrame frame = new BounceFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Une balle qui bouge et rebondit sur les bords
   d'un rectangle
*/
class Ball
{
   /**
      Déplace la balle à la position suivante, en inversant la direction
      si elle touche l'un des bords
   */
   public void move(Rectangle2D bounds)
   {
      x += dx;
      y += dy;
      if (x < bounds.getMinX())
      { 
         x = bounds.getMinX();
         dx = -dx;
      }
      if (x + XSIZE >= bounds.getMaxX())
      {
         x = bounds.getMaxX() - XSIZE; 
         dx = -dx; 
      }
      if (y < bounds.getMinY())
      {
         y = bounds.getMinY(); 
         dy = -dy;
      }
      if (y + YSIZE >= bounds.getMaxY())
      {
         y = bounds.getMaxY() - YSIZE;
         dy = -dy; 
      }
   }

   /**
      Récupère la forme de la balle à sa position actuelle.
   */
   public Ellipse2D getShape()
   {
      return new Ellipse2D.Double(x, y, XSIZE, YSIZE);
   }

   private static final int XSIZE = 15;
   private static final int YSIZE = 15;
   private double x = 0;
   private double y = 0;
   private double dx = 1;
   private double dy = 1;
}

/**
   L'écran où se dessinent les balles.
*/
class BallPanel extends JPanel
{
   /**
      Ajouter une balle à l'écran.
      @param b la balle à ajouter
   */
   public void add(Ball b)
   {
      balls.add(b);
   }

   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;
      for (Ball b : balls)
      {
         g2.fill(b.getShape());
      }
   }

   private ArrayList<Ball> balls = new ArrayList<Ball>();
}

/**
  Le bloc avec l'écran et les boutons.
*/
class BounceFrame extends JFrame
{
   /**
      Elabore le bloc de l'écran pour montrer la balle rebondissante et 
      les boutons Démarrer et Fermer.
   */
   public BounceFrame()
   {
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
      setTitle("Bounce");

      panel = new BallPanel();
      add(panel, BorderLayout.CENTER);
      JPanel buttonPanel = new JPanel();
      addButton(buttonPanel, "Démarrer",
         new ActionListener()
            {  
               public void actionPerformed(ActionEvent event)
               {
                  addBall();
               }
            });

      addButton(buttonPanel, "Fermer",
         new ActionListener()
            {
               public void actionPerformed(ActionEvent event)
               {
                  System.exit(0);
               }
            });
      add(buttonPanel, BorderLayout.SOUTH);
   }

   /**
      Ajoute un bouton à un conteneur.
      @param c le conteneur
      @param title le nom du bouton
      @param listener l'écouteur d'action pour le bouton
   */
   public void addButton(Container c, String title,
      ActionListener listener)
   {
      JButton button = new JButton(title);
      c.add(button);
      button.addActionListener(listener);
   }

   /**
      Ajoute une balle rebondissante à l'écran et la fait 
      rebondir 1 000 fois.
   */
   public void addBall()
   {
      try
      {
         Ball ball = new Ball();
         panel.add(ball);

         for (int i = 1; i <= STEPS; i++)
         {
            ball.move(panel.getBounds());
            panel.paint(panel.getGraphics());
            Thread.sleep(DELAY);
         }
      }
      catch (InterruptedException e)
      {                    
      }
   }

   private BallPanel panel;
   public static final int DEFAULT_WIDTH = 450;
   public static final int DEFAULT_HEIGHT = 350;  
   public static final int STEPS = 1000;
   public static final int DELAY = 3;
}
