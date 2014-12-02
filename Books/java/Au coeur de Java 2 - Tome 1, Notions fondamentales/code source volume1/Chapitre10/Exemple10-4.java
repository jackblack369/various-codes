Exemple 10.4 : PopupCalculatorApplet.java

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PopupCalculatorApplet extends JApplet
{  
   public void init()
   {  
      // créer un cadre avec un panneau de calculatrice
      
      final JFrame frame = new JFrame();
      frame.setTitle("Calculator");
      frame.setSize(200, 200);
      frame.add(new CalculatorPanel());

      // ajouter un bouton qui affiche ou masque la calculatrice
      
      JButton calcButton = new JButton("Calculator");
      add(calcButton);

      calcButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {  
               frame.setVisible(!frame.isVisible());
            }
         });
   }
}
