Exemple 5.18 : ProgressMonitorInputStreamTest.java

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

/**
   Un programme pour tester un flux d'entrée de contrôle de la 
   progression.
*/
public class ProgressMonitorInputStreamTest
{
   public static void main(String [] args))
   {
      JFrame frame = new TextFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Un cadre contenant un menu pour charger un fichier texte et une zone
   de texte pour afficher son contenu. La zone est construite
   au chargement du fichier et définie comme volet conteneur du
   cadre à la fin du chargement. Ceci évite les tremblements
   au cours du chargement.
*/
class TextFrame extends JFrame
{
   public TextFrame()
   {
      setTitle("ProgressMonitorInputStreamTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      // configurer le menu

      JMenuBar menuBar = new JMenuBar();
      setJMenuBar(menuBar);
      JMenu fileMenu = new JMenu("Fichier");
      menuBar.add(fileMenu);
      openItem = new JMenuItem("Ouvrir");
      openItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               try
               {
                  openFile();
               }
               catch(IOException exception)
               {
                  exception.printStackTrace();
               }
            }
         });
      
      fileMenu.add(openItem);
      exitItem = new JMenuItem("Quitter");
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

   /**
      Invite l'utilisateur à choisir un fichier, charge le fichier
      dans une zone de texte et le définit comme volet conteneur 
      du cadre.
   */
   public void openFile() throws IOException
   {
      JFileChooser chooser = new JFileChooser();
      chooser.setCurrentDirectory(new File("."));
      chooser.setFileFilter(
         new javax.swing.filechooser.FileFilter()
            {
               public boolean accept(File f)
               {
                  String fname = f.getName().toLowerCase();
                  return fname.endsWith(".txt") || f.isDirectory();
               }
               public String getDescription()
               {
                  return "Fichiers texte";
               }
            });

      int r = chooser.showOpenDialog(this);
      if (r != JFileChooser.APPROVE_OPTION) return;
      final File f = chooser.getSelectedFile();

      // configurer la suite de filtres du lecteur et du flux

      FileInputStream fileIn = new FileInputStream(f);
      ProgressMonitorInputStream progressIn
         = new ProgressMonitorInputStream(this, 
         "Lecture de " + f.getName(), fileIn);
      final Scanner in = new Scanner(progressIn);

      // l'activité surveillée doit être dans un nouveau thread.

      Runnable readRunnable = new
         Runnable()
         {
            public void run()
            {
               final JTextArea textArea = new JTextArea();

               while (in.hasNextLine())
               {
                  String line = in.nextLine();
                  textArea.append(line);
                  textArea.append("\n");
               }
               in.close();

               // configurer le volet conteneur dans le thread de 
               // distribution des événements
               EventQueue.invokeLater(new
                  Runnable()
                  {
                     public void run()
                     {
                        setContentPane(new JScrollPane(textArea));
                        validate();
                     }
                  });

            }
         };

      Thread readThread = new Thread(readRunnable);
      readThread.start();
   }

   private JMenuItem openItem;
   private JMenuItem exitItem;

   public static final int DEFAULT_WIDTH = 300;
   public static final int DEFAULT_HEIGHT = 200;
}
