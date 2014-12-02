Exemple 5.16 : ProgressBarTest.java

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.Timer;

/**
   Ce programme montre l'utilisation d'une barre de
   progression pour contrôler l'évolution d'un thread.
*/
public class ProgressBarTest
{
   public static void main(String [] args))
   {
      JFrame frame = new ProgressBarFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Un cadre qui contient un bouton pour lancer une activité
   simulée, une barre de progression et une zone de texte pour
   le résultat.
*/
class ProgressBarFrame extends JFrame
{
   public ProgressBarFrame()
   {
      setTitle("ProgressBarTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      // cette zone de texte contient le résultat de l'activité
      textArea = new JTextArea();

      //configure le cadre avec un bouton et une barre de progression

      JPanel panel = new JPanel();
      startButton = new JButton("Démarrer");
      progressBar = new JProgressBar();
      progressBar.setStringPainted(true);
      panel.add(startButton);
      panel.add(progressBar);

      checkBox = new JCheckBox("indéterminé");
      checkBox.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               progressBar.setIndeterminate(checkBox.isSelected());
            }
         });
      panel.add(checkBox);
      add(new JScrollPane(textArea), BorderLayout.CENTER);
      add(panel, BorderLayout.SOUTH);

      // configurer l'action du bouton

      startButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               progressBar.setMaximum(1000);
               activity = new SimulatedActivity(1000);
               new Thread(activity).start();
               activityMonitor.start();
               startButton.setEnabled(false);
            }
         });


   // configurer l'action du minuteur

   activityMonitor = new Timer(500, new
      ActionListener()
      {
         public void actionPerformed(ActionEvent event)
         {
            int current = activity.getCurrent();

            // afficher la progression
            textArea.append(current + "\n");
            progressBar.setStringPainted(!progressBar.isIndeterminate());
            progressBar.setValue(current);
 
            // vérifier si la tâche est terminée
            if (current == activity.getTarget())
            {
               activityMonitor.stop();
               startButton.setEnabled(true);
            }
         }
      });
   }

   private Timer activityMonitor;
   private JButton startButton;
   private JProgressBar progressBar;
   private JCheckBox checkBox;
   private JTextArea textArea;
   private SimulatedActivity activity;

   public static final int DEFAULT_WIDTH = 400;
   public static final int DEFAULT_HEIGHT = 200;
}

/**
   Un exécutable d'activité simulée.
*/
class SimulatedActivity implements Runnable
{
   /**
      Construit l'objet de thread de l'activité simulée. Le
      thread incrémente un compteur de 0 jusqu'à la cible donnée.
      @param t la valeur cible du compteur.
   */
   public SimulatedActivity(int t)
   {
      current = 0;
      target = t;
   }

   public int getTarget()
   {
      return target;
   }

   public int getCurrent()
   {
      return current;
   }

   public void run()
   {
      try
      {
         while (current < target)
         {
            Thread.sleep(100);
            current++;
         }
      }
      catch(InterruptedException e)
      {
      }
   }

   private volatile int current;
   private int target;
}

