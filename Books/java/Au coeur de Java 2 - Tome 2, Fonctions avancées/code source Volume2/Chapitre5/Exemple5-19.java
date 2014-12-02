Exemple 5.19 : SplitPaneTest.java

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

/**
   Ce programme montre l'organisateur du composant séparateur.
*/
public class SplitPaneTest
{
   public static void main(String[] args)
   {
      JFrame frame = new SplitPaneFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);      
   }
}

/**
   Ce bloc est constitué de deux volets imbriqués pour montrer 
   les images et les données des planètes.
*/
class SplitPaneFrame extends JFrame
{
   public SplitPaneFrame()
   {
      setTitle("SplitPaneTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      // définit les composants pour les noms des planètes, les images et 
      // les descriptions

      final JList planetList = new JList(planets);
      final JLabel planetImage = new JLabel();
      final JTextArea planetDescription = new JTextArea();

      planetList.addListSelectionListener(new
         ListSelectionListener()
         {
            public void valueChanged(ListSelectionEvent event)
            {  
               Planet value 
                  = (Planet)planetList.getSelectedValue();

               // met à jour l'image et la description

               planetImage.setIcon(value.getImage());
               planetDescription.setText(value.getDescription());
            }
         });
      
      // définit les séparateurs

      JSplitPane innerPane 
         = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
            planetList, planetImage);

      innerPane.setContinuousLayout(true);
      innerPane.setOneTouchExpandable(true);

      JSplitPane outerPane 
         = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
            innerPane, planetDescription);

      add(outerPane, BorderLayout.CENTER);
   }
   
   private Planet[] planets =
      {
         new Planet("Mercure", 2440, 0),
         new Planet("Vénus", 6052, 0),
         new Planet("Terre", 6378, 1),
         new Planet("Mars", 3397, 2),
         new Planet("Jupiter", 71492, 16),
         new Planet("Saturne", 60268, 18),
         new Planet("Uranus", 25559, 17),
         new Planet("Neptune", 24766, 8),
         new Planet("Pluton", 1137, 1),
      };
   private static final int DEFAULT_WIDTH = 300;
   private static final int DEFAULT_HEIGHT = 300;
}

/**
   Décrit une planète.
*/
class Planet
{
   /**
      Construit une planète.
      @param n le nom de la planète
      @param r le rayon de la planète
      @param m le nombre de lunes
   */
  public Planet(String n, double r, int m)
   {  name = n;
      radius = r;
      moons = m;
      image = new ImageIcon(name + ".gif");
   }
   
   public String toString()
   {
      return name;
   }
   
   /**
      Obtient une description de la planète.
      @return la description
   */
   public String getDescription()
   {
      return "Rayon: " + radius + "\nLunes: " + moons + "\n";
   }
   
   /**
      Obtient une image de la planète.
      @return l'image
   */
   public ImageIcon getImage()
   {
      return image;
   }

   private String name;
   private double radius;
   private int moons;
   private ImageIcon image;
}
