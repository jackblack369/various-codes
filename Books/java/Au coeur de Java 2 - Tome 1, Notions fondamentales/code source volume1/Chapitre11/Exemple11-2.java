Exemple 11.2 : ExceptTest.java

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class ExceptTest
{
   public static void main(String[] args)
   {
      ExceptTestFrame frame = new ExceptTestFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Un cadre avec un panneau pour tester diverses exceptions
*/
class ExceptTestFrame extends JFrame
{
   public ExceptTestFrame()
   {
      setTitle("ExceptTest");
      ExceptTestPanel panel = new ExceptTestPanel();
      add(panel);
      pack();
   }
}

/**
   Un panneau avec des boutons radio pour lancer des 
   extraits de code et étudier leur comportement en cas
   d'exceptions
*/
class ExceptTestPanel extends Box
{
   public ExceptTestPanel()
   {
      super(BoxLayout.Y_AXIS);
      group = new ButtonGroup();

      // ajouter des boutons radio pour les extraits de code

      addRadioButton("Integer divide by zero", new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               a[1] = 1 / (a.length - a.length);
            }
         });

      addRadioButton("Floating point divide by zero", new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               a[1] = a[2] / (a[3] - a[3]);
            }
          });

      addRadioButton("Array bounds", new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               a[1] = a[10];
            }
         });

      addRadioButton("Bad cast", new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               a = (double[])event.getSource();
            }
          });

     addRadioButton("Null pointer", new
        ActionListener()
        {
           public void actionPerformed(ActionEvent event)
           {
              event = null;
              System.out.println(event.getSource());
           }
         });

     addRadioButton("sqrt(-1)", new
        ActionListener()
        {
           public void actionPerformed(ActionEvent event)
           {
              a[1] = Math.sqrt(-1);
           }
         });

     addRadioButton("Overflow", new
        ActionListener()
        {
           public void actionPerformed(ActionEvent event)
           {
              a[1] = 1000 * 1000 * 1000 * 1000;
              int n = (int) a[1];
           }
         });

     addRadioButton("No such file", new
        ActionListener()
        {
           public void actionPerformed(ActionEvent event)
           {
              try
              {
                 InputStream in = new FileInputStream("woozle.txt");
              }
              catch (IOException e)
              {
                 textField.setText(e.toString());
              }
           }
         });

      addRadioButton("Throw unknown", new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               throw new UnknownError();
            }
         });

      // ajouter le champ de texte pour l'affichage de l'exception
      textField = new JTextField(30);
      add(textField);
   }

   /**
      Ajoute un bouton radio avec un écouteur donné au
      panneau. Intercepte toute exception dans la méthode
      actionPerformed de l'écouteur.
      @param s Le libellé du bouton radio
      @param listener L'écouteur d'action pour le bouton radio
   */
   private void addRadioButton(String s, ActionListener listener)
   {
      JRadioButton button = new JRadioButton(s, false)
      {
         // le bouton appelle cette méthode pour déclencher 
         // l'événement d'action. Nous la surchargeons pour intercepter 
         // les exceptions
         protected void fireActionPerformed(ActionEvent event)
         {
            try
            {
               textField.setText("No exception");
               super.fireActionPerformed(event);
            }
            catch (Exception e)
            {
               textField.setText(e.toString());
            }
         }
      };

      button.addActionListener(listener);
      add(button);
      group.add(button);
   }

   private ButtonGroup group;
   private JTextField textField;
   private double[] a = new double[10];
}
