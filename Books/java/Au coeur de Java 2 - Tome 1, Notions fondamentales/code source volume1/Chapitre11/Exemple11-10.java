Exemple 11.10 : BuggyButtonFrame.java

import java.awt.*;
import javax.swing.*;

public class BuggyButtonFrame extends JFrame
{
   public BuggyButtonFrame()
   {
      setTitle("BuggyButtonTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      // ajouter un panneau au cadre

      BuggyButtonPanel panel = new BuggyButtonPanel();
      add(panel);
   }

   public static final int DEFAULT_WIDTH = 300;
   public static final int DEFAULT_HEIGHT = 200;  
}
