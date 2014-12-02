Exemple 10.13 : WebStartCalculator.java

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.jnlp.*;

/**
   Une calculette avec l'historique des calculs qui peut 
   être déployé sous forme d'application Java Web Start.
*/
public class WebStartCalculator
{
   public static void main(String[] args)
   {  
      CalculatorFrame frame = new CalculatorFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Un cadre avec un panneau de calculatrice et un menu pour charger et
   enregistrer l'historique des calculs.
*/
class CalculatorFrame extends JFrame
{
   public CalculatorFrame()
   {
      setTitle();
      panel = new CalculatorPanel();
      add(panel);

      JMenu fileMenu = new JMenu("File");

      JMenuItem openItem = fileMenu.add("Open");
      openItem.addActionListener(new        
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               open();
            }
         });

      JMenuItem saveItem = fileMenu.add("Save");
      saveItem.addActionListener(new        
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               save();
            }
         });
      JMenuBar menuBar = new JMenuBar();
      menuBar.add(fileMenu);
      setJMenuBar(menuBar);

      pack();
   }

   /**
      Récupère le titre de la banque persistante ou 
      demande le titre à l'utilisateur s'il n'existe pas 
      d'entrée préalable.
   */
   public void setTitle()
   {
      try 
      { 
         String title = null;

         BasicService basic = (BasicService)
            ServiceManager.lookup("javax.jnlp.BasicService"); 
         URL codeBase = basic.getCodeBase();

         PersistenceService service = (PersistenceService) 
            ServiceManager.lookup("javax.jnlp.PersistenceService"); 
         URL key = new URL(codeBase, "title");

         try
         {
            FileContents contents = service.get(key);
            InputStream in = contents.getInputStream();
            BufferedReader reader = 
               new BufferedReader(new InputStreamReader(in));
            title = reader.readLine();
         }
         catch (FileNotFoundException e)
         {
            title = JOptionPane.showInputDialog
               ("Please supply a frame title:");
            if (title == null) return;

            service.create(key, 100);
            FileContents contents = service.get(key);
            OutputStream out = contents.getOutputStream(true);
            PrintStream printOut = new PrintStream(out);
            printOut.print(title);               
         }
         setTitle(title);
      } 
      catch (UnavailableServiceException e) 
      { 
         JOptionPane.showMessageDialog(this, e);
      }
      catch (MalformedURLException e) 
      { 
         JOptionPane.showMessageDialog(this, e);
      }
      catch (IOException e) 
      { 
         JOptionPane.showMessageDialog(this, e);
      }      
   }

   /**
      Ouvre un fichier d'historique et actualise l'affichage.
   */
   public void open()
   {
      try 
      {
         FileOpenService service = (FileOpenService) 
            ServiceManager.lookup("javax.jnlp.FileOpenService"); 
         FileContents contents = service.openFileDialog
            (".", new String[] { "txt" });

         JOptionPane.showMessageDialog(this, contents.getName());
         if (contents != null)
         {
            InputStream in = contents.getInputStream();
            BufferedReader reader = new BufferedReader(
               new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null)
            {
               panel.append(line);                  
               panel.append("\n");
            }
         }
      } 
      catch (UnavailableServiceException e) 
      { 
         JOptionPane.showMessageDialog(this, e);
      }
      catch (IOException e) 
      { 
         JOptionPane.showMessageDialog(this, e);
      }
   }

   /**
      Enregistre l'historique des calculs dans un fichier.
   */
   public void save()
   {
      try 
      { 
         ByteArrayOutputStream out = new ByteArrayOutputStream();
         PrintStream printOut = new PrintStream(out);
         printOut.print(panel.getText());
         InputStream data = new ByteArrayInputStream(out.toByteArray());                  
         FileSaveService service = (FileSaveService) 
            ServiceManager.lookup("javax.jnlp.FileSaveService"); 
         service.saveFileDialog(".", new String[] 
            { "txt" }, data, "calc.txt");
      } 
      catch (UnavailableServiceException e) 
      { 
         JOptionPane.showMessageDialog(this, e);
      }
      catch (IOException e) 
      { 
         JOptionPane.showMessageDialog(this, e);
      }
   }

   private CalculatorPanel panel;
}


/**
   Un panneau avec les boutons de la calculette et l'affichage 
   des résultats.
*/
class CalculatorPanel extends JPanel
{  
   /**
      Affiche le panneau.
   */
   public CalculatorPanel()
   {  
      setLayout(new BorderLayout());

      result = 0;
      lastCommand = "=";
      start = true;

      // ajouter l'affichage

      display = new JTextArea(10, 20);

      add(new JScrollPane(display), BorderLayout.NORTH);

      ActionListener insert = new InsertAction();
      ActionListener command = new CommandAction();

      // ajouter les boutons sur une grille 4 x 4

      panel = new JPanel();
      panel.setLayout(new GridLayout(4, 4));

      addButton("7", insert);
      addButton("8", insert);
      addButton("9", insert);
      addButton("/", command);

      addButton("4", insert);
      addButton("5", insert);
      addButton("6", insert);
      addButton("*", command);

      addButton("1", insert);
      addButton("2", insert);
      addButton("3", insert);
      addButton("-", command);

      addButton("0", insert);
      addButton(".", insert);
      addButton("=", command);
      addButton("+", command);

      add(panel, BorderLayout.CENTER);
   }

   /**
      Récupère le texte de l'historique.
      @return L'historique des calculs
   */
   public String getText()
   {
      return display.getText();
   }

   /**
      Annexe une chaîne au texte de l'historique.
      @param s La chaîne à annexer
   */
   public void append(String s)
   {
      display.append(s);
   }

   /**
      Ajoute un bouton au panneau central.
      @param label L'intitulé du bouton
      @param listener L'écouteur du bouton
   */
   private void addButton(String label, ActionListener listener)
   {  
      JButton button = new JButton(label);
      button.addActionListener(listener);
      panel.add(button);
   }

   /**
      Cette action insère la chaîne d'action du bouton à la
      fin du texte affiché.
   */
   private class InsertAction implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {
         String input = event.getActionCommand();
         start = false;
         display.append(input);
      }
   }

   /**
      Cette action exécute la commande traduite par 
      la chaîne d'action du bouton.
   */
   private class CommandAction implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {  
         String command = event.getActionCommand();

         if (start)
         {  
            if (command.equals("-")) 
            { 
               display.append(command); 
               start = false; 
            }
            else 
               lastCommand = command;
         }
         else
         {  
            try
            {
               int lines = display.getLineCount();
               int lineStart = display.getLineStartOffset(lines - 1);
               int lineEnd = display.getLineEndOffset(lines - 1);
               String value = display.getText(
                  lineStart, lineEnd - lineStart);
               display.append(" ");
               display.append(command); 
               calculate(Double.parseDouble(value));
               if (command.equals("="))
                  display.append("\n" + result);
               lastCommand = command;
               display.append("\n");
               start = true;
            }
            catch (BadLocationException e)
            {
               e.printStackTrace();
            }
         }
      }
   }

   /**
      Réalise le calcul en attente. 
      @param x La valeur à accumuler avec le résultat précédent.
   */
   public void calculate(double x)
   {
      if (lastCommand.equals("+")) result += x;
      else if (lastCommand.equals("-")) result -= x;
      else if (lastCommand.equals("*")) result *= x;
      else if (lastCommand.equals("/")) result /= x;
      else if (lastCommand.equals("=")) result = x;
   }

   private JTextArea display;
   private JPanel panel;
   private double result;
   private String lastCommand;
   private boolean start;
}
