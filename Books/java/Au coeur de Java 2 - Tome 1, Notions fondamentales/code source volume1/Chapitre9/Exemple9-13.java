Exemple 9.13 : BoxLayoutTest.java

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BoxLayoutTest
{
   public static void main(String[] args)
   {  
      BoxLayoutFrame frame = new BoxLayoutFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Un cadre qui utilise des objets BoxLayout pour organiser
      différents composants.
*/
class BoxLayoutFrame extends JFrame
{  
   public BoxLayoutFrame()
   {  
      setTitle("BoxLayoutTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      // construire l'objet Box horizontal supérieur

      JLabel label1 = new JLabel("Name:");
      JTextField textField1 = new JTextField(10);
      textField1.setMaximumSize(textField1.getPreferredSize());

      Box hbox1 = Box.createHorizontalBox();
      hbox1.add(label1);
      // séparer à l'aide d'un strut de 10 pixels 
      hbox1.add(Box.createHorizontalStrut(10));
      hbox1.add(textField1);

      // construire l'objet Box horizontal central

      JLabel label2 = new JLabel("Password:");
      JTextField textField2 = new JTextField(10);
      textField2.setMaximumSize(textField2.getPreferredSize());
      

      Box hbox2 = Box.createHorizontalBox();
      hbox2.add(label2);
      // séparer à l'aide d'un strut de 10 pixels 
      hbox2.add(Box.createHorizontalStrut(10));
      hbox2.add(textField2);

      // construire l'objet Box horizontal inférieur

      JButton button1 = new JButton("OK");
      JButton button2 = new JButton("Cancel");

      Box hbox3 = Box.createHorizontalBox();
      hbox3.add(button1);
      // utiliser "glue" pour éloigner les deux boutons
      hbox3.add(Box.createGlue());
      hbox3.add(button2);

      // ajouter les trois objets Box horizontaux
      // à l'intérieur d'un Box vertical 

      Box vbox = Box.createVerticalBox();
      vbox.add(hbox1);
      vbox.add(hbox2);
      vbox.add(Box.createGlue());
      vbox.add(hbox3);

      add(vbox, BorderLayout.CENTER);
   }

   public static final int DEFAULT_WIDTH = 200;
   public static final int DEFAULT_HEIGHT = 200;  
} 
