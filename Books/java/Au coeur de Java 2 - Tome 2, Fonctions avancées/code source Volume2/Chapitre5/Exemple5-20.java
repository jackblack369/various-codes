Exemple 5.20 : TabbedPaneTest.java

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

/**
   Ce programme montre l'organisateur du composant à onglets.
*/
public class TabbedPaneTest
{
   public static void main(String[] args)
   {
      JFrame frame = new TabbedPaneFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);      
   }
}

/**
   Ce bloc montre un onglet et des boutons pour
   basculer entre la disposition emballée et défilante.
*/
class TabbedPaneFrame extends JFrame
{
   public TabbedPaneFrame()
   {
      setTitle("TabbedPaneTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
      
      tabbedPane = new JTabbedPane(); 
      /**
         Nous mettons les composants à null et mettons en attente leur 
         chargement jusqu'à ce que leur onglet soit affiché pour la 
         première fois.
      */
      
      ImageIcon icon = new ImageIcon("yellow-ball.gif");
      
      tabbedPane.addTab("Mercure", icon, null);
      tabbedPane.addTab("Vénus", icon, null);
      tabbedPane.addTab("Terre", icon, null);
      tabbedPane.addTab("Mars", icon, null);
      tabbedPane.addTab("Jupiter", icon, null);
      tabbedPane.addTab("Saturne", icon, null);
      tabbedPane.addTab("Uranus", icon, null);
      tabbedPane.addTab("Neptune", icon, null);
      tabbedPane.addTab("Pluton", icon, null);
      
      add(tabbedPane, "Center");

      tabbedPane.addChangeListener(new
         ChangeListener()
         {
            public void stateChanged(ChangeEvent event)
            {

               // regarde si l'onglet possède encore un composant nul
   
               if (tabbedPane.getSelectedComponent() == null)
               {
                  // règle le composant sur l'icône de l'image 
      
                  int n = tabbedPane.getSelectedIndex();
                  loadTab(n);
               }
            }
         });

      loadTab(0);
 
      JPanel buttonPanel = new JPanel();
      ButtonGroup buttonGroup = new ButtonGroup();
      JRadioButton wrapButton = new JRadioButton("Emballe les onglets");
      wrapButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               tabbedPane.setTabLayoutPolicy(
                  JTabbedPane.WRAP_TAB_LAYOUT);
            }
         });
      buttonPanel.add(wrapButton);
      buttonGroup.add(wrapButton);
      wrapButton.setSelected(true);
      JRadioButton scrollButton 
         = new JRadioButton("Fait défiler les onglets");
      scrollButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               tabbedPane.setTabLayoutPolicy(
                  JTabbedPane.SCROLL_TAB_LAYOUT);
            }
         });
      buttonPanel.add(scrollButton);
      buttonGroup.add(scrollButton);
      add(buttonPanel, BorderLayout.SOUTH);
   }

   /**
      Charge l'onglet avec l'indice donné.
      @param n L'indice de l'onglet à charger
   */
   private void loadTab(int n)
   {
      String title = tabbedPane.getTitleAt(n);
      ImageIcon planetIcon = new ImageIcon(title + ".gif");
      tabbedPane.setComponentAt(n, new JLabel(planetIcon));

      // indiquer que cet onglet a été visité – pour s'amuser !

      tabbedPane.setIconAt(n, new ImageIcon("red-ball.gif"));
   }

   private JTabbedPane tabbedPane;

   private static final int DEFAULT_WIDTH = 400;
   private static final int DEFAULT_HEIGHT = 300;
}
