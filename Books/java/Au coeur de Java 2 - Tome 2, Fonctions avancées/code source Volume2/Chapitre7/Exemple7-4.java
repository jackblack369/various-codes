Exemple 7.4 : PermissionTest.java

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.security.*;
import java.util.*;
import javax.swing.*;

/**
   Cette classe montre la permission personnalisée WordCheckPermission.
*/
public class PermissionTest
{
   public static void main(String[] args)
   {
      System.setSecurityManager(new SecurityManager());
      JFrame f = new PermissionTestFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Ce bloc contient un champ de texte pour insérer des mots
   dans une zone de texte protégée des "mots interdits".
*/
class PermissionTestFrame extends JFrame
{
   public PermissionTestFrame()
   {
      setTitle("PermissionTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      textField = new JTextField(20);
      JPanel panel = new JPanel();
      panel.add(textField);
      JButton openButton = new JButton("Insérer");
      panel.add(openButton);
      openButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               insertWords(textField.getText());
            }
         });

      add(panel, BorderLayout.NORTH);

      textArea = new WordCheckTextArea();
      add(new JScrollPane(textArea), BorderLayout.CENTER);
   }

   /**
      Essaie d'insérer des mots dans la zone de texte.
      Affiche une boîte de dialogue si la tentative a échoué.
      @param words les mots à insérer
   */
   public void insertWords(String words)
   {
      try
      {
         textArea.append(words + "\n");
      }
      catch (SecurityException e)
      {
         JOptionPane.showMessageDialog(this,
            "Désolé, c'est interdit.");
      }
   }

   private JTextField textField;
   private WordCheckTextArea textArea;
   private static final int DEFAULT_WIDTH = 400;
   private static final int DEFAULT_HEIGHT = 300;
}

/**
   Une zone de texte dont la méthode append crée une 
   vérification de sécurité afin qu'aucun mot interdit ne 
   soit ajouté.
*/
class WordCheckTextArea extends JTextArea
{
   public void append(String text)
   {
      WordCheckPermission p
         = new WordCheckPermission(text, "insert");
      SecurityManager manager = System.getSecurityManager();
      if (manager != null) manager.checkPermission(p);
      super.append(text);
   }
}
