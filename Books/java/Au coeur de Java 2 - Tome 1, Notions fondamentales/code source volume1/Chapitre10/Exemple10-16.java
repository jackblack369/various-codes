Exemple 10.16 : PreferencesTest.java

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.logging.*;
import java.util.prefs.*;
import javax.swing.*;
import javax.swing.event.*;

/**
   Un programme pour tester les paramètres de préférence. Le programme
   se souvient de la position et de la taille du cadre.
*/
public class PreferencesTest
{
   public static void main(String[] args)
   {
      PreferencesFrame frame = new PreferencesFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Un cadre qui restaure la position et la taille à partir des
   préférences utilisateur et actualise les préférences à la fermeture.
*/
class PreferencesFrame extends JFrame
{
   public PreferencesFrame()
   {
      setTitle("PreferencesTest");

      // récupérer la position et la taille à partir des préférences

      Preferences root = Preferences.userRoot();
      final Preferences node = root.node("/com/horstmann/corejava");
      int left = node.getInt("left", 0);
      int top = node.getInt("top", 0);
      int width = node.getInt("width", DEFAULT_WIDTH);
      int height = node.getInt("height", DEFAULT_HEIGHT);
      setBounds(left, top, width, height);

      // configurer le sélecteur des fichiers XML

      final JFileChooser chooser = new JFileChooser();
      chooser.setCurrentDirectory(new File("."));

      // accepter tous les fichiers se terminant par .xml
      chooser.setFileFilter(new
         javax.swing.filechooser.FileFilter()
         {
            public boolean accept(File f)
            {
               return f.getName().
                  toLowerCase().endsWith(".xml") || f.isDirectory();
            }
            public String getDescription()
            {
               return "XML files";
            }
         });


      // configurer les menus
      JMenuBar menuBar = new JMenuBar();
      setJMenuBar(menuBar);
      JMenu menu = new JMenu("File");
      menuBar.add(menu);

      JMenuItem exportItem = new JMenuItem("Export preferences");
      menu.add(exportItem);
      exportItem.addActionListener(new 
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               if(chooser.showSaveDialog(
                  PreferencesFrame.this) == JFileChooser.APPROVE_OPTION)
               {
                  try
                  {
                     OutputStream out = new 
                        FileOutputStream(chooser.getSelectedFile());
                     node.exportSubtree(out);
                     out.close();
                  }
                  catch (Exception e)
                  {
                     e.printStackTrace();
                  }
               }
            }
         });

      JMenuItem importItem = new JMenuItem("Import preferences");
      menu.add(importItem);
      importItem.addActionListener(new 
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               if(chooser.showOpenDialog(
                  PreferencesFrame.this) == JFileChooser.APPROVE_OPTION)
               {
                  try
                  {
                     InputStream in = new FileInputStream(
                        chooser.getSelectedFile());
                     node.importPreferences(in);
                     in.close();
                  }
                  catch (Exception e)
                  {
                     e.printStackTrace();
                  }
               }
            }
         });

      JMenuItem exitItem = new JMenuItem("Exit");
      menu.add(exitItem);
      exitItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               node.putInt("left", getX());
               node.putInt("top", getY());
               node.putInt("width", getWidth());
               node.putInt("height", getHeight());
               System.exit(0);
            }
         });
   }
   public static final int DEFAULT_WIDTH = 300;
   public static final int DEFAULT_HEIGHT = 200;  
}
