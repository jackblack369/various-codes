Exemple 7.20 : FileReadApplet.java

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

/**
   Cette applet peut s'exécuter "en dehors du bac à sable" et
   lire des fichiers locaux lorsqu'elle reçoit les bonnes permissions.
*/
public class FileReadApplet extends JApplet
{
   public FileReadApplet()
   {
      fileNameField = new JTextField(20);
      JPanel panel = new JPanel();
      panel.add(new JLabel("Nom fichier :"));
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
      Charge le contenu d'un fichier dans la zone de texte.
      @param filename le nom du fichier
   */
   public void loadFile(String filename)
   {
      try
      {  fileText.setText("");
         Scanner in
            = new Scanner(new FileReader(filename));
         while (in.hasNextLine())
         fileText.append(in.nextLine() + "\n");
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
}


