Exemple 9.2 : TextTest.java

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;

public class TextTest 
{
   public static void main(String[] args)
   {  
      TextTestFrame frame = new TextTestFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Un cadre avec deux champs de texte pour définir l'heure.
*/
class TextTestFrame extends JFrame
{  
   public TextTestFrame()
   {  
      setTitle("TextTest");

      DocumentListener listener = new ClockFieldListener();

      // ajouter un panneau avec les champs de texte

      JPanel panel = new JPanel();

      panel.add(new JLabel("Hours:"));
      hourField = new JTextField("12", 3);
      panel.add(hourField);
      hourField.getDocument().addDocumentListener(listener);
       
      panel.add(new JLabel("Minutes:"));
      minuteField = new JTextField("00", 3);
      panel.add(minuteField);
      minuteField.getDocument().addDocumentListener(listener);
   
      add(panel, BorderLayout.SOUTH);

      // ajouter l'horloge

      clock = new ClockPanel();
      add(clock, BorderLayout.CENTER);
      pack();
   }

   /**
      Affecte à l'horloge les valeurs indiquées 
         dans les champs de texte.
   */
   public void setClock()
   {  
      try
      {
         int hours 
            = Integer.parseInt(hourField.getText().trim());
         int minutes 
            = Integer.parseInt(minuteField.getText().trim());
         clock.setTime(hours, minutes);
      }
      catch (NumberFormatException e) {}
      /* ne pas configurer l'horloge si les entrées 
         saisies ne peuvent pas être analysées */
   }
       
   public static final int DEFAULT_WIDTH = 300;
   public static final int DEFAULT_HEIGHT = 300;  

   private JTextField hourField;
   private JTextField minuteField;
   private ClockPanel clock;

   private class ClockFieldListener implements DocumentListener
   {
      public void insertUpdate(DocumentEvent event) { setClock(); }
      public void removeUpdate(DocumentEvent event) { setClock(); }
      public void changedUpdate(DocumentEvent event) {}
   }
}

/**
   Un panneau avec un dessin d'horloge.
*/
class ClockPanel extends JPanel
{
   public ClockPanel()
   {
      setPreferredSize(new Dimension(2 * RADIUS + 1, 2 * RADIUS + 1));
   }
   public void paintComponent(Graphics g)
   {
      // dessiner le cadran

      super.paintComponent(g); 
      Graphics2D g2 = (Graphics2D)g;
      Ellipse2D circle 
         = new Ellipse2D.Double(0, 0, 2 * RADIUS, 2 * RADIUS);
      g2.draw(circle);
       
      // dessiner la grande aiguille

      double hourAngle 
         = Math.toRadians(90 - 360 * minutes / (12 * 60));
      drawHand(g2, hourAngle, HOUR_HAND_LENGTH);

      // dessiner la petite aiguille

      double minuteAngle 
         = Math.toRadians(90 - 360 * minutes / 60);
      drawHand(g2, minuteAngle, MINUTE_HAND_LENGTH);
   }

   public void drawHand(Graphics2D g2, 
      double angle, double handLength)
   {
      Point2D end = new Point2D.Double(
         RADIUS + handLength * Math.cos(angle), 
         RADIUS - handLength * Math.sin(angle));
      Point2D center = new Point2D.Double(RADIUS, RADIUS);
      g2.draw(new Line2D.Double(center, end));
   }
    
   /**
      Définit l'heure à afficher sur l'horloge
      @param h Heures
      @param m Minutes
   */
   public void setTime(int h, int m)
   {  
      minutes = h * 60 + m;
      repaint();
   }
    
   private double minutes = 0;
   private int RADIUS = 100;
   private double MINUTE_HAND_LENGTH = 0.8 * RADIUS;
   private double HOUR_HAND_LENGTH = 0.6 * RADIUS;
}
