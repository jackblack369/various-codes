Exemple 6.4 : InnerClassTest.java

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class InnerClassTest
{  
   public static void main(String[] args)
   {  
      TalkingClock clock = new TalkingClock(1000, true);
      clock.start();

      // laisser le programme fonctionner jusqu'à ce que l'utilisateur
      // clique sur "OK"
      JOptionPane.showMessageDialog(null, "Quit program?");
      System.exit(0);
   }
}

/**
   Une horloge qui affiche l'heure à intervalles réguliers.
*/
class TalkingClock
{  
   /**
      Construit une horloge parlante
      @param interval l'intervalle entre les messages (en millièmes de 
      seconde
      @param beep true si l'horloge doit sonner
   */
   public TalkingClock(int interval, boolean beep)
   {  
      this.interval = interval;
      this.beep = beep;
   }

   /**
      Lance l'horloge.
   */
   public void start()
   {
      ActionListener listener = new TimePrinter();
      Timer t = new Timer(interval, listener);
      t.start();
   }

   private int interval;
   private boolean beep;

   private class TimePrinter implements ActionListener
   {  
      public void actionPerformed(ActionEvent event)
      {  
         Date now = new Date();
         System.out.println("At the tone, the time is " + now);
         if (beep) Toolkit.getDefaultToolkit().beep();
      }
   }
}
