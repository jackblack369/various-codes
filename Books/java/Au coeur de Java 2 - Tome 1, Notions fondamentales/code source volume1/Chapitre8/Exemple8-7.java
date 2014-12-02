Exemple 8.7 : EventSourceTest.java

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.beans.*;

public class EventSourceTest
{
   public static void main(String[] args)
   {
      EventSourceFrame frame = new EventSourceFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Un cadre qui contient un panneau avec des dessins
*/
class EventSourceFrame extends JFrame
{
   public EventSourceFrame()
   {
      setTitle("EventSourceTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      // ajouter le panneau au cadre

      final PaintCountPanel panel = new PaintCountPanel();
      add(panel);

      panel.addPropertyChangeListener(new
         PropertyChangeListener()
         {
            public void propertyChange(PropertyChangeEvent event)
            {
               setTitle("EventSourceTest - " + event.getNewValue());
            }
         });
   }

   public static final int DEFAULT_WIDTH = 400;
   public static final int DEFAULT_HEIGHT = 200;
}

/**
   Un panneau qui compte la fréquence du dessin.
*/
class PaintCountPanel extends JPanel
{
   public void paintComponent(Graphics g)
   {
      int oldPaintCount = paintCount;
      paintCount++;
      firePropertyChangeEvent(new PropertyChangeEvent(this,
         "paintCount", oldPaintCount, paintCount));
      super.paintComponent(g);
   }

/**
   Ajoute un écouteur de changement
   @param listener L'écouteur à ajouter
*/
public void addPropertyChangeListener(PropertyChangeListener listener)
{
   listenerList.add(PropertyChangeListener.class, listener);
}

/**
   Supprime un écouteur de changement
   @param listener L'écouteur à supprimer
*/
public void removePropertyChangeListener(PropertyChangeListener listener)
{
   listenerList.remove(PropertyChangeListener.class, listener);
}

public void firePropertyChangeEvent(PropertyChangeEvent event)
{
   EventListener[] listeners =    
       listenerList.getListeners(PropertyChangeListener.class);
   for (EventListener l : listeners)
       ((PropertyChangeListener) l).propertyChange(event);
}

public int getPaintCount()
{
   return paintCount;
}

private int paintCount;
}
