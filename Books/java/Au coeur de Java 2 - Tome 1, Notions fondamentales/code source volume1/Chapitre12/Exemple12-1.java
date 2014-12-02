Exemple 12.1 : ZipTest.java

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.zip.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

public class ZipTest
{  
   public static void main(String[] args)
   {  
      ZipTestFrame frame = new ZipTestFrame();
      frame.setTitle("ZipTest");
      frame.setSize(300, 400);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.show();
   }
}

/**
   Un cadre contenant une zone de texte pour afficher le contenu d'un 
   fichier dans une archive zip, une liste combinée pour choisir 
   différents fichiers de l'archive et un menu pour charger une nouvelle 
   archive.
*/
class ZipTestFrame extends JFrame
{
   public ZipTestFrame()
   {
      // ajouter le menu et les éléments Open et Exit
      JMenuBar menuBar = new JMenuBar();
      JMenu menu = new JMenu("File");

      JMenuItem openItem = new JMenuItem("Open");
      menu.add(openItem);
      openItem.addActionListener(new OpenAction());

      JMenuItem exitItem = new JMenuItem("Exit");
      menu.add(exitItem);
      exitItem.addActionListener(new 
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               System.exit(0);
            }
         });

      menuBar.add(menu);
      setJMenuBar(menuBar);

      // ajouter la zone de texte et la liste déroulante
      fileText = new JTextArea();
      fileCombo = new JComboBox();
      fileCombo.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               loadZipFile((String)fileCombo.getSelectedItem());
            }
         });

      Container contentPane = getContentPane();
      contentPane.add(fileCombo, BorderLayout.SOUTH);
      contentPane.add(fileText, BorderLayout.CENTER);
   }

   /**
      Voici l'écouteur du menu File->Open.
   */
   private class OpenAction implements ActionListener
   {
      public void actionPerformed(ActionEvent evt)
      {
         // Demande un fichier zip à l'utilisateur
         JFileChooser chooser = new JFileChooser();
         chooser.setCurrentDirectory(new File("."));
         ExtensionFileFilter filter = new ExtensionFileFilter();
         filter.addExtension(".zip");
         filter.addExtension(".jar");
         filter.setDescription("ZIP archives");
         chooser.setFileFilter(filter);
         int r = chooser.showOpenDialog(ZipTestFrame.this);
         if (r == JFileChooser.APPROVE_OPTION)
         {  
            zipname = chooser.getSelectedFile().getPath();
            scanZipFile();
         }  
      }
   }

   /**
      Analyse le contenu de l'archive zip et remplit 
      la liste combinée.
   */
   public void scanZipFile()
   {  
      fileCombo.removeAllItems();
      try
      {  
         ZipInputStream zin = new ZipInputStream(new
            FileInputStream(zipname));
         ZipEntry entry;
         while ((entry = zin.getNextEntry()) != null)
         {  
            fileCombo.addItem(entry.getName());
            zin.closeEntry();
         }
         zin.close();
      }
      catch (IOException e)
      {  
         e.printStackTrace(); 
      }
   }

   /**
      Charge un fichier depuis l'archive zip dans la zone de texte
      @param name Le nom du fichier dans l'archive
   */
   public void loadZipFile(String name)
   {  
      try
      {  
         ZipInputStream zin = new ZipInputStream(new
            FileInputStream(zipname));
         ZipEntry entry;
         fileText.setText("");

         // retrouver l'entrée ayant le nom correspondant dans l'archive
         while ((entry = zin.getNextEntry()) != null)
         {  
            if (entry.getName().equals(name))
            {  
               // lire l'entrée dans la zone de texte
               BufferedReader in = new BufferedReader(new
                  InputStreamReader(zin));
               String line;
               while ((line = in.readLine()) != null)
               {
                  fileText.append(line);
                  fileText.append("\n");
               }
            }
            zin.closeEntry();
         }
         zin.close();
      }
      catch (IOException e)
      {  
         e.printStackTrace(); 
      }
   }

   private JComboBox fileCombo;
   private JTextArea fileText;
   private String zipname;
}

/**
   Ce filtre de fichier correspond à tous les fichiers avec un jeu 
   d'extensions donné. De FileChooserTest au Chapitre 9
*/
class ExtensionFileFilter extends FileFilter
{
   /**
      Ajoute une extension que ce filtre de fichiers reconnaît.
      @param extension Une extension de fichier (comme ".txt" ou "txt")
   */
   public void addExtension(String extension)
   {
      if (!extension.startsWith("."))
         extension = "." + extension;
      extensions.add(extension.toLowerCase());     
   }

   /**
      Définit une description pour le jeu de fichiers reconnu par 
      ce filtre de fichiers.
      @param aDescription Une description pour le jeu de fichiers
   */
   public void setDescription(String aDescription)
   {
      description = aDescription;
   }

   /**
      Renvoie une description du jeu de fichiers reconnu par 
      ce filtre de fichiers.
      @return Une description pour le jeu de fichiers
   */
   public String getDescription()
   {
      return description; 
   }

   public boolean accept(File f)
   {
      if (f.isDirectory()) return true;
      String name = f.getName().toLowerCase();

      // vérifier si le nom du fichier se termine par l'une des 
      // extensions
      for (int i = 0; i < extensions.size(); i++)
         if (name.endsWith((String)extensions.get(i))) 
            return true;
      return false;
   }
   
   private String description = "";
   private ArrayList extensions = new ArrayList();
}
