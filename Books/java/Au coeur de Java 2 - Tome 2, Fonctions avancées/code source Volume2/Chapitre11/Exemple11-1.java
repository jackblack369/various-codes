Exemple 11.1 : ButtonTest.java

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
public class ButtonTest
{  
   public static void main(String[] args)
   {  
      ButtonFrame frame = new ButtonFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}
 
/**
   Un cadre avec un panneau de boutons.
*/
class ButtonFrame extends JFrame
{
   public ButtonFrame()
   {
      setTitle("ButtonTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
 
      panel = new JPanel();
      add(panel);
 
      // Créer les boutons
 
      yellowButton = new JButton("Jaune");
      blueButton = new JButton("Bleu");
      redButton = new JButton("Rouge");
 
      // Ajouter les boutons au panneau
 
      panel.add(yellowButton);
      panel.add(blueButton);
      panel.add(redButton);
 
      ActionListenerInstaller.processAnnotations(this);
   }
 
 
   @ActionListenerFor(source="yellowButton")
   public void yellowBackground()
   {
      panel.setBackground(Color.YELLOW);
   }
 
   @ActionListenerFor(source="blueButton")
   public void blueBackground()
   {
      panel.setBackground(Color.BLUE);
   }
 
   @ActionListenerFor(source="redButton")
   public void redBackground()
   {
      panel.setBackground(Color.RED);
   }
 
   public static final int DEFAULT_WIDTH = 300;
   public static final int DEFAULT_HEIGHT = 200;  
 
   private JPanel panel;
   private JButton yellowButton;
   private JButton blueButton;
   private JButton redButton;
}
