Exemple 7.6 : SecurityManagerTest.java

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

/**
   Cette classe montre l'utilisation d'un gestionnaire de sécurité
   personnalisé qui interdit la lecture de fichiers de texte contenant
   les mots interdits.
*/
public class SecurityManagerTest
{
   public static void main(String[] args)
   {
      System.setSecurityManager(new WordCheckSecurityManager());
      JFrame frame = new SecurityManagerFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Ce bloc contient un champ de texte permettant d'entrer le nom d'un
   fichier et une zone de texte pour montrer le contenu du fichier 
   chargé.
*/
class SecurityManagerFrame extends JFrame
{
   public SecurityManagerFrame()
   {
      setTitle("SecurityManagerTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      fileNameField = new JTextField(20);
      JPanel panel = new JPanel();
      panel.add(new JLabel("Fichier texte :"));
      panel.add(fileNameField);
      JButton openButton = new JButton("Ouvrir");
      panel.add(openButton);
      openButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               loadFile(fileNameField.getText());
            }
         });

      add(panel, "North");

      fileText = new JTextArea();
      add(new JScrollPane(fileText), "Center");
   }

   /**
      Tente de charger un fichier dans la zone de texte. En cas de 
      déclenchement d'une exception de sécurité, un message est inséré          
      dans la zone de texte.
      @param filename le nom de fichier
   */
   public void loadFile(String filename)
   {
      try
      {
         fileText.setText("");
         Scanner in = new Scanner(new FileReader(filename));
         while (in.hasNextLine()) fileText.append(in.nextLine() + "\n");
         fileText.append(s + "\n");
         in.close();
      }
      catch (IOException e)
      {
         fileText.append(e + "\n");
      }
      catch (SecurityException e)
      {
         fileText.append("Désolé, c'est interdit.\n");
         fileText.append(e + "\n");
      }
   }

   private JTextField fileNameField;
   private JTextArea fileText;
   private static final int DEFAULT_WIDTH = 400;
   private static final int DEFAULT_HEIGHT = 300;
}
