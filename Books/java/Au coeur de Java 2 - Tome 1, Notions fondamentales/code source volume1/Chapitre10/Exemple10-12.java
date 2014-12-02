Exemple 10.12 : ResourceTest.java

import java.awt.*;
import java.awt.event.*;
import java.applio.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

public class ResourceText
{
   public static void main(String[] args)
   {  
      ResourceTestFrame frame = new ResourceTestFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Un cadre avec un panneau disposant de composants pour démontrer
   l'accès aux ressources à partir d'un fichier JAR.
*/
class ResourceTestFrame extends JFrame 
{  
   public ResourceTestFrame()
   {  
      setTitle("ResourceTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
      add(new AboutPanel());
   }

   public static final int DEFAULT_WIDTH = 300;
   public static final int DEFAULT_HEIGHT = 300;
}

/**
   Un panneau avec une zone de texte et un bouton "About". Appuyer 
   dessus remplit la zone avec le texte d'une ressource.
*/
class AboutPanel extends JPanel
{
   public AboutPanel()
   {
      setLayout(new BorderLayout());

      // Ajouter la zone de texte
      textArea = new JTextArea();
      add(new JScrollPane(textArea), BorderLayout.CENTER);

      // Ajouter le bouton About 
      URL aboutURL = AboutPanel.class.getResource("about.gif");
      JButton aboutButton = new JButton("About", 
         new ImageIcon(aboutURL));
      aboutButton.addActionListener(new AboutAction());
      add(aboutButton, BorderLayout.SOUTH);
   }

   private JTextArea textArea;

   private class AboutAction implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {
         InputStream stream = 
            AboutPanel.class.getResourceAsStream("about.txt");
         Scanner in = new Scanner(stream);
         while (in.hasNext())
            textArea.append(in.nextLine() + "\n");
      } 
   }
}
