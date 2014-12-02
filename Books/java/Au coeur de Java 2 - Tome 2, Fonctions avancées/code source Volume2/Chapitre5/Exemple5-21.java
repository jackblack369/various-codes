Exemple 5.21 : InternalFrameTest.java

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

/**
   Ce programme montre l'utilisation de fenêtres internes.
*/
public class InternalFrameTest
{
   public static void main(String[] args)
   {
      JFrame frame = new DesktopFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);      
   }
}

/**
   Ces fenêtres de bureau contiennent des volets d'édition qui présentent 
   des documents HTML.
*/
class DesktopFrame extends JFrame
{
   public DesktopFrame()
   {
      setTitle("InternalFrameTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
         
      desktop = new JDesktopPane(); 
      add(desktop, BorderLayout.CENTER);
      
      // définit les menus

      JMenuBar menuBar = new JMenuBar();
      setJMenuBar(menuBar);
      JMenu fileMenu = new JMenu("Fichier");
      menuBar.add(fileMenu);
      JMenuItem openItem = new JMenuItem("Nouveau");
      openItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               createInternalFrame(
                  new JLabel(new ImageIcon(planets[counter] + ".gif")), 
                  planets[counter]);
               counter = (counter + 1) % planets.length;
            }
         });
      fileMenu.add(openItem);
      JMenuItem exitItem = new JMenuItem("Quitter");
      exitItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               System.exit(0);
            }
         });
      fileMenu.add(exitItem);
      JMenu windowMenu = new JMenu("Fenêtre");
      menuBar.add(windowMenu);
      JMenuItem nextItem = new JMenuItem("Suivante");
      nextItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               selectNextWindow();
            }
         });
      windowMenu.add(nextItem);
      JMenuItem cascadeItem = new JMenuItem("Cascade");
      cascadeItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               cascadeWindows();
            }
         });
      windowMenu.add(cascadeItem);
      JMenuItem tileItem = new JMenuItem("Juxtaposition");
      tileItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               tileWindows();
            }
         });
      windowMenu.add(tileItem);
      final JCheckBoxMenuItem dragOutlineItem
         = new JCheckBoxMenuItem("Déplacer le contour");
      dragOutlineItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               desktop.setDragMode(dragOutlineItem.isSelected() 
                  ? JDesktopPane.OUTLINE_DRAG_MODE
                  : JDesktopPane.LIVE_DRAG_MODE);
            }
         });
      windowMenu.add(dragOutlineItem);
   }

   /**
      Crée une fenêtre interne sur le bureau.
      @param c le composant à afficher dans la fenêtre interne
      @param t le titre de la fenêtre interne
   */
   public void createInternalFrame(Component c, String t)
   {
      final JInternalFrame iframe = new JInternalFrame(t, 
         true, // la taille peut être modifiée
         true, // la fenêtre peut être fermée
         true, // la fenêtre peut être maximisée
         true); // la fenêtre peut être icônifiée
         
      iframe.add(c, BorderLayout.CENTER);
      desktop.add(iframe);

      iframe.setFrameIcon(new ImageIcon("document.gif"));      

      // ajoute un écouteur pour confirmer la fermeture de la fenêtre 
      iframe.addVetoableChangeListener(new
         VetoableChangeListener()
         {
            public void vetoableChange(PropertyChangeEvent event)
               throws PropertyVetoException
            {  
               String name = event.getPropertyName();
               Object value = event.getNewValue();

               // nous voulons juste vérifier les tentatives pour fermer  
               // une fenêtre
               if (name.equals("closed") && value.equals(true))
               {  
                  // demande à l'utilisateur s'il accepte de fermer la 
                  // fenêtre
                  int result
                     = JOptionPane.showInternalConfirmDialog(
                        iframe, "Prêt à fermer ?"),
                        "Choisissez une option", 
                        JOptionPane.YES_NO_OPTION);

                  // s'il n'est pas d'accord, refus de fermeture
                  if (result != JOptionPane.YES_OPTION)
                     throw new PropertyVetoException(
                        "L'utilisateur a annulé la fermeture", event);
               }
            }           
         });

      // place la fenêtre
      int width = desktop.getWidth() / 2;
      int height = desktop.getHeight() / 2;
      iframe.reshape(nextFrameX, nextFrameY, width, height);

      iframe.show();

      // sélectionne la fenêtre (peut être refusé)
      try
      {
         iframe.setSelected(true);
      }
      catch(PropertyVetoException e)
      {}

      frameDistance = iframe.getHeight() 
            - iframe.getContentPane().getHeight();
            
      // calcule l'emplacement de la prochaine fenêtre
            
      nextFrameX += frameDistance;
      nextFrameY += frameDistance;
      if (nextFrameX + width > desktop.getWidth()) 
         nextFrameX = 0;
      if (nextFrameY + height > desktop.getHeight()) 
         nextFrameY = 0;
   }
   
   /**
      Met en cascade les fenêtres internes non-icônifiées du bureau.
   */
   public void cascadeWindows()
   {
      int x = 0;
      int y = 0;
      int width = desktop.getWidth() / 2;
      int height = desktop.getHeight() / 2;

      for (JInternalFrame frame : desktop.getAllFrames())
      {
         if (!frames[i].isIcon())
         {
            try
            {
               // essaye de modifier la taille des fenêtres maximisées 
                         // (peut être refusé
               frame.setMaximum(false);
               frame.reshape(x, y, width, height);

               x += frameDistance;
               y += frameDistance;
               // s'entoure aux bords du bureau
               if (x + width > desktop.getWidth()) x = 0;
               if (y + height > desktop.getHeight()) y = 0;
            } 
            catch (PropertyVetoException e)
            {}
         }
      }
   }
   
   /**
      Juxtapose les fenêtres internes non-icônifiées du bureau.
   */
   public void tileWindows()
   {
      // compte les fenêtres non icônifiées
      int frameCount = 0;         
      for (JInternalFrame frame : desktop.getAllFrames())
         if (!frame.isIcon()) frameCount++;
      if (frameCount == 0) return;

      int rows = (int) Math.sqrt(frameCount);
      int cols = frameCount / rows;
      int extra = frameCount % rows;
         // nombre de colonnes avec une ligne supplémentaire

      int width = desktop.getWidth() / cols;
      int height = desktop.getHeight() / rows;
      int r = 0;
      int c = 0;
      for (JInternalFrame frame : desktop.getAllFrames()))
      {
         if (!frame.isIcon())
         {
            try
            {
               frame.setMaximum(false);
               frame.reshape(c * width, 
                  r * height, width, height);
               r++;
               if (r == rows)
               {
                  r = 0;
                  c++;
                  if (c == cols - extra)
                  {
                     // commence à ajouter une autre ligne
                     rows++;
                     height = desktop.getHeight() / rows;
                  }
               }
            } 
            catch (PropertyVetoException e)
            {}
         }
      }
   }

   /**
      Amène à l'avant la prochaine fenêtre non icônifiée.
   */
   public void selectNextWindow()
   {
      JInternalFrame[] frames = desktop.getAllFrames();
      for (int i = 0; i < frames.length; i++)
      {
         if (frames[i].isSelected())
         {
             // trouve la prochaine fenêtre non icônifiée qui peut être 
             // sélectionnée
             int next = (i + 1) % frames.length;
             while (next != i)
             {
                if (!frames[next].isIcon()) 
                {
                   try
                   {
                      // toutes les autres fenêtres sont icônifiées ou 
                      // refusées
                     frames[next].setSelected(true);
                     frames[next].toFront();
                     frames[next].toBack();
                     return;
                   }
                   catch (PropertyVetoException e)
                   {}
               }
               next = (next + 1) % frames.length;
            }
         }           
      }
   }
   
   private JDesktopPane desktop;    
   private int nextFrameX;
   private int nextFrameY;
   private int frameDistance;
   private int counter;
   private static final String[] planets =
   {
      "Mercure",
      "Venus",
      "Terre",
      "Mars",
      "Jupiter",
      "Saturne",
      "Uranus",
      "Neptune",
      "Pluton",
   };

   private static final int DEFAULT_WIDTH = 600;
   private static final int DEFAULT_HEIGHT = 400;
}
