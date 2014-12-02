Exemple 11.5 : ConsoleWindow.java

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

/**
   Une fenêtre qui affiche les mots (bytes) envoyés à System.out
   et System.err
*/
public class ConsoleWindow
{
   public static void init()
   {
      JFrame frame = new JFrame();
      frame.setTitle("ConsoleWindow");
      final JTextArea output = new JTextArea();
      output.setEditable(false);
      frame.add(new JScrollPane(output));
      frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
      frame.setLocation(DEFAULT_LEFT, DEFAULT_TOP);
      frame.setFocusableWindowState(false);
      frame.setVisible(true);

      // définir un PrintStream qui envoie ses mots à la
      // zone de texte de sortie
      PrintStream consoleStream = new PrintStream(new
         OutputStream()
         {
            public void write(int b) {} // jamais appelé
            public void write(byte[] b, int off, int len)
            {
               output.append(new String(b, off, len));
            }
         });

      // définir System.out et System.err sur ce flux
      System.setOut(consoleStream);
      System.setErr(consoleStream);
   }

   public static final int DEFAULT_WIDTH = 300;
   public static final int DEFAULT_HEIGHT = 200;
   public static final int DEFAULT_LEFT = 200;
   public static final int DEFAULT_TOP = 200;
}
