Exemple 8.4 : MouseTest.java

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.awt.geom.*;
import javax.swing.*;

public class MouseTest
{
   public static void main(String[] args)
   {
      MouseFrame frame = new MouseFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Un cadre avec un panneau pour tester 
       les opérations de la souris
*/
class MouseFrame extends JFrame
{
   public MouseFrame()
   {
      setTitle("MouseTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      // ajouter un panneau au cadre

      MousePanel panel = new MousePanel();
      add(panel);
   }

   public static final int DEFAULT_WIDTH = 300;
   public static final int DEFAULT_HEIGHT = 200;  
}

/**
   Un panneau avec des opérations de la souris 
       pour l'ajout et la suppression de carrés.
*/
class MousePanel extends JPanel
{
   public MousePanel()
   {
      squares = new ArrayList<Rectangle2D>();
      current = null;
 
      addMouseListener(new MouseHandler());
      addMouseMotionListener(new MouseMotionHandler());
   }

   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D)g;

      // dessiner tous les carrés
      for (Rectangle2D r : squares)
         g2.draw(r);
   }

   /**
      Trouve le premier carré contenant un point.
      @param p Un point
      @return Le premier carré contenant p
   */
   public Rectangle2D find(Point2D p)
   {
      for (Rectangle2D r : squares)
      {
         if (r.contains(p)) return r;
      }
      return null;
   }

   /**
      Ajoute un carré à la collection.
      @param p Le centre du carré
   */
   public void add(Point2D p)
   {
      double x = p.getX();
      double y = p.getY();
 
      current = new Rectangle2D.Double(
         x - SIDELENGTH / 2,
         y - SIDELENGTH / 2,
         SIDELENGTH,
         SIDELENGTH);
      squares.add(current);
      repaint();
   }

   /**
      Supprime un carré de la collection.
      @param s Le carré à supprimer
   */
   public void remove(Rectangle2D s)
   {
      if (s == null) return;
      if (s == current) current = null;
      squares.remove(s);
      repaint();
   }

 
   private static final int SIDELENGTH = 10;
   private ArrayList<Rectangle2D> squares;
   private Rectangle2D current;
   // le carré contenant le pointeur de la souris

   private class MouseHandler extends MouseAdapter
   {
      public void mousePressed(MouseEvent event)
      {
         /* ajouter un nouveau carré si le pointeur
            n'est pas à l'intérieur d'un carré */ 
         current = find(event.getPoint());
         if (current == null)
            add(event.getPoint());
      }

      public void mouseClicked(MouseEvent event)
      {
         // supprimer le carré courant si double-clic dessus
         current = find(event.getPoint());
         if (current != null && event.getClickCount() >= 2)
            remove(current);
      }
   }

   private class MouseMotionHandler
      implements MouseMotionListener
   {
      public void mouseMoved(MouseEvent event)
      {
         /* définit le pointeur sous forme de croix 
            s'il est à l'intérieur d'un carré */

         if (find(event.getPoint()) == null)
            setCursor(Cursor.getDefaultCursor());
         else
            setCursor(Cursor.getPredefinedCursor
               (Cursor.CROSSHAIR_CURSOR));
      }

      public void mouseDragged(MouseEvent event)
      {
         if (current != null)
         {
            int x = event.getX();
            int y = event.getY();

            /* tirer le carré courant pour 
               le centrer à la position (x, y) */
            current.setFrame(
               x - SIDELENGTH / 2,
               y - SIDELENGTH / 2,
               SIDELENGTH,
               SIDELENGTH);
            repaint();
         }
      }
   }
}
