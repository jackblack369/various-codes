Exemple 9.17 : CircleLayoutTest.java

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CircleLayoutTest
{
   public static void main(String[] args)
   {  
      CircleLayoutFrame frame = new CircleLayoutFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.pack();
      frame.setVisible(true);
   }
}

/**
   Un cadre affichant des boutons disposés en cercle.
*/
class CircleLayoutFrame extends JFrame
{  
   public CircleLayoutFrame()
   {  
      setTitle("CircleLayoutTest");

      setLayout(new CircleLayout());
      add(new JButton("Yellow"));
      add(new JButton("Blue"));
      add(new JButton("Red"));
      add(new JButton("Green"));
      add(new JButton("Orange"));
      add(new JButton("Fuschia"));
      add(new JButton("Indigo"));
   }
}

/**
   Un gestionnaire de mise en forme disposant les composants 
      en cercle.
*/
class CircleLayout implements LayoutManager
{  
   public void addLayoutComponent(String name,
      Component comp)
   {}

   public void removeLayoutComponent(Component comp)
   {}

   public void setSizes(Container parent)
   {  
      if (sizesSet) return;
      int n = parent.getComponentCount();

      preferredWidth = 0;
      preferredHeight = 0;
      minWidth = 0;
      minHeight = 0;
      maxComponentWidth = 0;
      maxComponentHeight = 0;

      // calculer les largeur et hauteur maxi de composants 
      // et affecter la somme des tailles des composants
      // à la taille préférée 
for (int i = 0; i < n; i++)
      {  
         Component c = parent.getComponent(i);
         if (c.isVisible()) 
         {
            Dimension d = c.getPreferredSize();
            maxComponentWidth = Math.max(maxComponentWidth,
               d.width);
            maxComponentHeight = Math.max(maxComponentHeight,
               d.height);
            preferredWidth += d.width;
            preferredHeight += d.height;
         }
      }
      minWidth = preferredWidth / 2;
      minHeight = preferredHeight / 2;
      sizesSet = true;
   }

   public Dimension preferredLayoutSize(Container parent)
   {  
      setSizes(parent);
      Insets insets = parent.getInsets();
      int width = preferredWidth + insets.left
         + insets.right;
      int height = preferredHeight + insets.top
         + insets.bottom;
      return new Dimension(width, height);
   }

   public Dimension minimumLayoutSize(Container parent)
   {  
      setSizes(parent);
      Insets insets = parent.getInsets();
      int width = minWidth + insets.left + insets.right;
      int height = minHeight + insets.top + insets.bottom;
      return new Dimension(width, height);
   }

   public void layoutContainer(Container parent)
   {  
      setSizes(parent);

      // calculer le centre du cercle

      Insets insets = parent.getInsets();
      int containerWidth = parent.getSize().width
         - insets.left - insets.right;
      int containerHeight = parent.getSize().height
         - insets.top - insets.bottom;

      int xcenter = insets.left + containerWidth / 2;
      int ycenter = insets.top + containerHeight / 2;

      // calculer le rayon du cercle

      int xradius = (containerWidth - maxComponentWidth) / 2;
      int yradius = (containerHeight - maxComponentHeight) / 2;
      int radius = Math.min(xradius, yradius);

      // disposer les composants le long du cercle

      int n = parent.getComponentCount();
      for (int i = 0; i < n; i++)
      {  
         Component c = parent.getComponent(i);
         if (c.isVisible())
         {  
            double angle = 2 * Math.PI * i / n;

            // centre du composant
            int x = xcenter +  (int)(Math.cos(angle) * radius);
            int y = ycenter +  (int)(Math.sin(angle) * radius);

            // déplacer le composant pour que son centre soit (x, y)
            // et sa taille égale à sa taille préférée 
            Dimension d = c.getPreferredSize();
            c.setBounds(x - d.width / 2, y - d.height / 2,
               d.width, d.height);
         }
      }
   }

   private int minWidth = 0;
   private int minHeight = 0;
   private int preferredWidth = 0;
   private int preferredHeight = 0;
   private boolean sizesSet = false;
   private int maxComponentWidth = 0;
   private int maxComponentHeight = 0;
}
