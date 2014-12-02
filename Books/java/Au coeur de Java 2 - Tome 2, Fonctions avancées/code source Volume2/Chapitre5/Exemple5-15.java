Exemple 5.15 : EditorPaneTest.java

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

/**
   Ce programme montre comment afficher des documents HTML 
   dans un volet d'édition.
*/
public class EditorPaneTest
{
   public static void main(String[] args)
   {
      JFrame frame = new EditorPaneFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);      
   }
}

/**
   Ce bloc contient un volet d'édition, un champ de texte et un bouton 
   pour entrer une URL et charger un document et un bouton Précédente 
   pour renvoyer à un document précédemment chargé.
*/
class EditorPaneFrame extends JFrame
{
   public EditorPaneFrame()
   {
      setTitle("EditorPaneTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      final Stack<String> urlStack = new Stack<String>();
      final JEditorPane editorPane = new JEditorPane();
      final JTextField url = new JTextField(30);

      // définit un écouteur de lien hypertexte
         
      editorPane.setEditable(false);
      editorPane.addHyperlinkListener(new 
         HyperlinkListener()
         {  
            public void hyperlinkUpdate(HyperlinkEvent event)
            {  
               if (event.getEventType()
                  == HyperlinkEvent.EventType.ACTIVATED)
               {  
                  try
                  {  
                     // se souvenir de l'URL pour le bouton Précédente
                     urlStack.push(event.getURL().toString());
                     // afficher l'URL dans le champ de texte
                     url.setText(event.getURL().toString());
                     editorPane.setPage(event.getURL());
                  }
                  catch (IOException e)
                  {  
                     editorPane.setText("Exception : " + e);
                  }
               }
            }
         });

      // configure la case à cocher pour basculer en mode de modification

      final JCheckBox editable = new JCheckBox();
      editable.addActionListener(new 
         ActionListener()
         {  
            public void actionPerformed(ActionEvent event)
            {  
               editorPane.setEditable(editable.isSelected());
            }
         });

      // configure le bouton de chargement pour charger une URL

      ActionListener listener = new 
         ActionListener()
         {  
            public void actionPerformed(ActionEvent event)
            {  
               try
               {  
                  // se souvenir de l'URL pour le bouton Précédente 
                  urlStack.push(url.getText());
                  editorPane.setPage(url.getText());
               }
               catch (IOException e)
               {  
                  editorPane.setText("Exception : " + e);
               }
            }
         };
   
      JButton loadButton = new JButton("Charger");
      loadButton.addActionListener(listener);
      url.addActionListener(listener);

      // configure le bouton Précédente et l'action du bouton

      JButton backButton = new JButton("Précédente");
      backButton.addActionListener(new 
         ActionListener()
         {  
            public void actionPerformed(ActionEvent event)
            {  
               if (urlStack.size() <= 1) return;
               try
               {  
                  // récupère l'URL du bouton Précédente
                  urlStack.pop();
                  // affiche l'URL dans le champ de texte
                  String urlString = urlStack.peek();
                  url.setText(urlString);
                  editorPane.setPage(urlString);
               }
               catch (IOException e)
               {  
                  editorPane.setText("Exception : " + e);
               }
            }
         });

      add(new JScrollPane(editorPane), BorderLayout.CENTER);

      // place tous les composants de contrôle dans un panneau

      JPanel panel = new JPanel();
      panel.add(new JLabel("URL"));
      panel.add(url);
      panel.add(loadButton);
      panel.add(backButton);
      panel.add(new JLabel("Modifiable"));
      panel.add(editable);

      add(panel, BorderLayout.SOUTH);
   }

   private static final int DEFAULT_WIDTH = 600;
   private static final int DEFAULT_HEIGHT = 400;
}

