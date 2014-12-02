Exemple 7.2 : CenteredFrameTest.java

/**import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CenteredFrameTest
{  
   public static void main(String[] args)
   {  
      CenteredFrame frame = new CenteredFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);  
   }
}

class CenteredFrame extends JFrame
{
   public CenteredFrame()
   {
      // extraire les dimensions de l'écran

      Toolkit kit = Toolkit.getDefaultToolkit();
      Dimension screenSize = kit.getScreenSize();
      int screenHeight = screenSize.height;
      int screenWidth = screenSize.width;

      // centrer le cadre au milieu de l'écran

      setSize(screenWidth / 2, screenHeight / 2);
      setLocation(screenWidth / 4, screenHeight / 4);

      // définir l'icône et le titre du cadre

      Image img = kit.getImage("icon.gif");
      setIconImage(img);
      setTitle("CenteredFrame");
   }
}
