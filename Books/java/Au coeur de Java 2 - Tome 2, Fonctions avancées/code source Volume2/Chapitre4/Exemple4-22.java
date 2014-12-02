Exemple 4.22 : SOAPTest.java

import com.amazon.*;
import java.awt.*;
import java.awt.event.*;
import java.rmi.*;
import java.util.*;
import javax.swing.*;

/**
   Le client du programme d'entreposage. 
*/
public class SOAPTest
{  
   public static void main(String[] args)
   {
      JFrame frame = new SOAPTestFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Une fenêtre permettant de sélectionner l'auteur et d'afficher 
   la réponse du serveur.
*/
class SOAPTestFrame extends JFrame
{  
   public SOAPTestFrame()
   {  
      setTitle("SOAPTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      JPanel panel = new JPanel();

      panel.add(new JLabel("Auteur :"));
      author = new JTextField(20);
      panel.add(author);

      JButton searchButton = new JButton("Rechercher");
      panel.add(searchButton);
      searchButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {  
               result.setText("Veuillez patienter...");
               new Thread(new 
                  Runnable()
                  {
                     public void run()
                     {   
                        String name = author.getText();
                        String books = searchByAuthor(name);
                        result.setText(books);
                     }
                  }).start();
            }            
         });

      result = new JTextArea();
      result.setLineWrap(true);
      result.setEditable(false);

      add(panel, BorderLayout.NORTH);
      add(new JScrollPane(result), BorderLayout.CENTER);
   }

   /**
      Appelle le service Web d'Amazon pour trouver les titres 
      correspondant à l'auteur.
      @param name le nom de l'auteur
      @return une description des titres concordants
   */
   private String searchByAuthor(String name)
   {  
      try
      {         
         AmazonSearchPort port = (AmazonSearchPort) 
            (new AmazonSearchService_Impl().getAmazonSearchPort());

         AuthorRequest request
            = new AuthorRequest(name, "1", "books", "", "lite", "", 
               token, "", "", "");
         ProductInfo response = port.authorSearchRequest(request);          

         Details[] details = response.getDetails();
         StringBuilder r = new StringBuilder();
         for (Details d : details)
         {
            r.append("authors=");
            String[] authors = d.getAuthors();
            if (authors == null) r.append("[]");
            else r.append(Arrays.asList(d.getAuthors()));
            r.append(",title=");
            r.append(d.getProductName());
            r.append(",publisher=");
            r.append(d.getManufacturer());
            r.append(",pubdate=");
            r.append(d.getReleaseDate());
            r.append("\n");
         }
         return r.toString();
      } 
      catch (RemoteException e)
      {  
         return "Exception : " + e;
      }
   }

   private static final int DEFAULT_WIDTH = 450;
   private static final int DEFAULT_HEIGHT = 300;

   private static final String token = "votre jeton vient ici"; 

   private JTextField author;
   private JTextArea result;
}
