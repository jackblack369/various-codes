Exemple 7.17 : MessageDigestTest.java

import java.io.*;
import java.security.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
   Ce programme calcule le condensé du message d'un fichier
   ou le contenu d'une zone de texte.
*/
public class MessageDigestTest
{
   public static void main(String[] args)
   {
      JFrame f = new MessageDigestFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Ce bloc contient un menu permettant de calculer le condensé
   du message d'un fichier ou d'une zone de texte, de boutons
   pour basculer entre SHA-1 et MD5, une zone de texte et un
   champ de texte affichant le condensé du message.
*/
class MessageDigestFrame extends JFrame
{
   public MessageDigestFrame()
   {
      setTitle("MessageDigestTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      JPanel panel = new JPanel();
      ButtonGroup group = new ButtonGroup();
      addRadioButton(panel, "SHA-1", group);
      addRadioButton(panel, "MD5", group);

      add(panel, BorderLayout.NORTH);
      add(new JScrollPane(message), BorderLayout.CENTER);
      add(digest, BorderLayout.SOUTH);
      digest.setFont(new Font("Monospaced", Font.PLAIN, 12));

      setAlgorithm("SHA-1");

      JMenuBar menuBar = new JMenuBar();
      JMenu menu = new JMenu("Fichier");
      JMenuItem fileDigestItem = new JMenuItem("Condensé fichier");
      fileDigestItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
               { loadFile(); }
         });
      menu.add(fileDigestItem);
      JMenuItem textDigestItem
         = new JMenuItem("Condensé zone texte");
      textDigestItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               String m = message.getText();
               computeDigest(m.getBytes());
            }
         });
      menu.add(textDigestItem);
      menuBar.add(menu);
      setJMenuBar(menuBar);
   }

   /**
      Ajoute un bouton pour sélectionner un algorithme.
      @param c le conteneur dans lequel placer le bouton
      @param name le nom de l'algorithme
      @param g le groupe de boutons
   */
   public void addRadioButton(Container c, final String name,
      ButtonGroup g)
   {
      ActionListener listener = new
         ActionListener()
         {  public void actionPerformed(ActionEvent event)
            { setAlgorithm(name); }
         };
      JRadioButton b 
         = new JRadioButton(name, g.getButtonCount() == 0);
      c.add(b);
      g.add(b);
      b.addActionListener(listener);
   }

   /**
      Définit l'algorithme utilisé pour calculer le condensé.
      @param alg le nom de l'algorithme
   */
   public void setAlgorithm(String alg)
   {
      try
      {
         currentAlgorithm = MessageDigest.getInstance(alg);
         digest.setText("");
      }
      catch (NoSuchAlgorithmException e)
      {
         digest.setText("" + e);
      }
   }

   /**
      Charge un fichier et calcule son condensé de message.
   */
   public void loadFile()
   {
      JFileChooser chooser = new JFileChooser();
      chooser.setCurrentDirectory(new File("."));

      int r = chooser.showOpenDialog(this);
      if(r == JFileChooser.APPROVE_OPTION)
      {
         try
         {
            String name
               = chooser.getSelectedFile().getAbsolutePath();
               computeDigest(loadBytes(name));
         }
         catch (IOException e)
         {
            JOptionPane.showMessageDialog(null, e);
         }
      }
   }

   /**
      Charge les octets dans un fichier.
      @param name le nom du fichier
      @return un tableau avec les octets du fichier
   */
   public byte[] loadBytes(String name) throws IOException
   {
      FileInputStream in = null;

      in = new FileInputStream(name);
      try
      {
         ByteArrayOutputStream buffer
            = new ByteArrayOutputStream();
         int ch;
         while ((ch = in.read()) != -1)
            buffer.write(ch);
         return buffer.toByteArray();
      }
      finally
      {
         in.close();
      }
   }

   /**
      Calcule le condensé du message d'un tableau d'octets et
      l'affiche dans le champ de texte.
      @param b les octets pour lesquels le condensé du message
      doit être calculé
   */
   public void computeDigest(byte[] b)
   {
      currentAlgorithm.reset();
      currentAlgorithm.update(b);
      byte[] hash = currentAlgorithm.digest();
      String d = "";
      for (int i = 0; i < hash.length; i++)
      {
         int v = hash[i] & 0xFF;
         if (v < 16) d += "0";
         d += Integer.toString(v, 16).toUpperCase() + " ";
      }
      digest.setText(d);
   }

   private JTextArea message = new JTextArea();
   private JTextField digest = new JTextField();
   private MessageDigest currentAlgorithm;
   private static final int DEFAULT_WIDTH = 400;
   private static final int DEFAULT_HEIGHT  = 300;
}
