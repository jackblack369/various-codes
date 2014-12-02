Exemple 11.7 : EventTracerTest.java

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class EventTracerTest
{
   public static void main(String[] args)
   {
      JFrame frame = new EventTracerFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

class EventTracerFrame extends JFrame
{
   public EventTracerFrame()
   {
      setTitle("EventTracerTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      // ajouter un curseur et un bouton
      add(new JSlider(), BorderLayout.NORTH);
      add(new JButton("Test"), BorderLayout.SOUTH);

      // intercepter tous les événements des composants dans le cadre
      EventTracer tracer = new EventTracer();
      tracer.add(this);
   }

   public static final int DEFAULT_WIDTH = 400;
   public static final int DEFAULT_HEIGHT = 400;
}
