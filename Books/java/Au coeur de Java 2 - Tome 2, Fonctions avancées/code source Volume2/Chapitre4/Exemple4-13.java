Exemple 4.13 : WarehouseClient.java

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
import javax.naming.*;
import javax.swing.*;

/**
   Le client du programme de l'entrepôt. 
*/
public class WarehouseClient
{
   public static void main(String[] args)
   {
      try
      {
         System.setProperty("java.security.policy", "client.policy");
         System.setSecurityManager(new RMISecurityManager());

         Properties props = new Properties();
         String fileName = "WarehouseClient.properties";
         FileInputStream in = new FileInputStream(fileName);
         props.load(in);
         String url = props.getProperty("warehouse.url");
         if (url == null)
            url = "rmi://localhost/central_warehouse";

         Context namingContext = new InitialContext();
         Warehouse centralWarehouse = (Warehouse) 
                      namingContext.lookup(url);
         JFrame frame = new WarehouseClientFrame(centralWarehouse);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setVisible(true);
      }
      catch(Exception e)
      {
        e.printStackTrace();
      }
   }

/**
   Un bloc pour sélectionner l'âge, le sexe et les hobbies du client
   et pour montrer les produits correspondants naissant d'un appel 
   distant à l'entrepôt.
*/
class WarehouseClientFrame extends JFrame
{  
   public WarehouseClientFrame(Warehouse warehouse)
   {  
      this.warehouse = warehouse;
      setTitle("WarehouseClient");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      JPanel panel = new JPanel();
      panel.setLayout(new GridLayout(0, 2));


      panel.add(new JLabel("Age:"));
      age = new JTextField(4);
      age.setText("20");
      panel.add(age);

      female = new JRadioButton("féminin", true);
      male = new JRadioButton("masculin", true);
      ButtonGroup group = new ButtonGroup();
      panel.add(female); group.add(female);
      panel.add(male); group.add(male);

      panel.add(new JLabel("Hobbies: "));
      hobbies = new ArrayList<JCheckBox>();
      for (String h : new String[] { "jardinage", "beauté",
         "ordinateurs", "électroménager", "sports" })
         JCheckBox checkBox = new JCheckBox(h);
         hobbies.add(checkBox);
         panel.add(checkBox);
      }

      result = new JTextArea(4, 40);
      result.setEditable(false);

      JPanel buttonPanel = new JPanel();
      JButton submitButton = new JButton("Envoyer");
      buttonPanel.add(submitButton);
      submitButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {  
               callWarehouse();
            }            
         });

      add(panel, BorderLayout.NORTH);
      add(result, BorderLayout.CENTER);
      add(buttonPanel, BorderLayout.SOUTH);
   }

   /**
      Appelle l'entrepôt distant pour trouver des produits
      correspondants.
   */
   private void callWarehouse()
   {  
      try
      {  
         ArrayList<String> selected = new ArrayList<String>();
         for (JCheckBox checkBox : hobbies)
            if (checkBox.isSelected()) selected.add(checkBox.getText());
         Customer c = new Customer(Integer.parseInt(age.getText()),
            (male.isSelected() ? Product.MALE : 0)
            + (female.isSelected() ? Product.FEMALE : 0),
            selected.toArray(new String[selected.size()]));
         ArrayList<Product> recommendations = warehouse.find(c);
         result.setText(c + "\n");
         for (Product p : recommendations)
         {  
            String t = p.getDescription() + "\n";
            result.append(t);
         }
      }
      catch(Exception e)
      {  
         e.printStackTrace();
         result.setText("Exception: " + e);
      }
   }

   private static final int DEFAULT_WIDTH = 300;
   private static final int DEFAULT_HEIGHT = 300;
   
   private Warehouse warehouse;
   private JTextField age;
   private JRadioButton male;
   private JRadioButton female;
   private ArrayList<JCheckBox> hobbies;
   private JTextArea result;
}
