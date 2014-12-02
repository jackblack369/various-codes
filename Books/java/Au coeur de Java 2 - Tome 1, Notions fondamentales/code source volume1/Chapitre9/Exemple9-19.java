Exemple 9.19 : DialogTest.java

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
  
public class DialogTest
{
   public static void main(String[] args)
   {  
      DialogFrame frame = new DialogFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Un cadre avec un menu dont l'action Fichier->A propos (File->About)
      affiche une boîte de dialogue.
*/
class DialogFrame extends JFrame 
{  
   public DialogFrame()
   {  
      setTitle("DialogTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      // construire un menu Fichier 

      JMenuBar menuBar = new JMenuBar();
      setJMenuBar(menuBar);
      JMenu fileMenu = new JMenu("File");
      menuBar.add(fileMenu);

      // ajouter les options A propos et Quitter au menu

      // L'option A propos affiche la boîte de dialogue correspondante 

      JMenuItem aboutItem = new JMenuItem("About");
      aboutItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               if (dialog == null) // première fois
                  dialog = new AboutDialog(DialogFrame.this);   
               dialog.setVisible(true); // boîte de dialogue 
            }
         });
      fileMenu.add(aboutItem);

      // L'option Quitter permet de quitter le programme  

      JMenuItem exitItem = new JMenuItem("Exit");
      exitItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               System.exit(0);
            }
         });
      fileMenu.add(exitItem);
   }

   public static final int DEFAULT_WIDTH = 300;
   public static final int DEFAULT_HEIGHT = 200;  

   private AboutDialog dialog;
}

/**
   Une boîte de dialogue modale qui affiche un message et 
      attend que l'utilisateur clique sur le bouton OK.
*/
class AboutDialog extends JDialog
{  
   public AboutDialog(JFrame owner)
   {  
      super(owner, "About DialogTest", true);         

      // ajouter le libellé HTML au centre

      add(new JLabel(
         "<html><h1><i>Core Java</i></h1><hr>
            By Cay Horstmann and Gary Cornell</html>"),
         BorderLayout.CENTER);

      // Le bouton OK ferme la boîte de dialogue 
       
      JButton ok = new JButton("OK");
      ok.addActionListener(new 
         ActionListener() 
         {  
            public void actionPerformed(ActionEvent event) 
            { 
               setVisible(false); 
            } 
         });

      // ajouter le bouton OK dans la partie sud 
       
      JPanel panel = new JPanel();
      panel.add(ok);
      add(panel, BorderLayout.SOUTH);

      setSize(250, 150);
   }
} 