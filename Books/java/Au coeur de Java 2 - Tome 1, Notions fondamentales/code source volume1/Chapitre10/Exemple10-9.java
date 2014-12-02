Exemple 10.9 : Bookmark.java

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.util.*;
import java.net.*;
import javax.swing.*;

public class Bookmark extends JApplet 
     
   public void init()
   {  
      Box box = Box.createVerticalBox();
      ButtonGroup group = new ButtonGroup();

      int i = 1;
      String urlString;

      // lire tous les paramètres link.n 
      while ((urlString = getParameter("link." + i)) != null)
      {  

         try
         {  
            final URL url = new URL(urlString);

            // créer un bouton radio pour chaque lien
            JRadioButton button = new JRadioButton(urlString);
            box.add(button);
            group.add(button);

            // choisir le bouton radio qui affiche l'URL dans 
            // le cadre "droit" 
            button.addActionListener(new 
               ActionListener()
               {
                  public void actionPerformed(ActionEvent event)
                  {
                     {
                        AppletContext context = getAppletContext();
                        context.showDocument(url, "right");
                     }
                  });
         } 
         catch(MalformedURLException e) 
         { 
            e.printStackTrace(); 
         }

         i++;
      }

      add(box);
   }
}
