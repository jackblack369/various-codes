Exemple 3.3 : QueryDB.java

import java.net.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

/**
   Ce programme présente plusieurs requêtes complexes de bases de 
   données.
*/
public class QueryDB
{
   public static void main(String[] args)
   {
      JFrame frame = new QueryDBFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Ce bloc affiche des menus déroulants pour les paramètres de requête,
   une zone de texte pour les résultats de la commande et des boutons 
   pour lancer une requête et une mise à jour.
*/
class QueryDBFrame extends JFrame
{
   public QueryDBFrame()
   {
      setTitre("QueryDB");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
      setLayout(new GridBagLayout());

      Auteurs = new JComboBox();
      Auteurs.setEditable(false);
      Auteurs.addItem("Non précisé");

      Editeurs = new JComboBox();
      Editeurs.setEditable(false);
      Editeurs.addItem("Non précisé");

      result = new JTextArea(4, 50);
      result.setEditable(false);

      PrixChange = new JTextField(8);
      PrixChange.setText("-5.00");

      try
      {
         conn = getConnection();
         Statement stat = conn.createStatement();

         String query = "SELECT Nom FROM Auteurs";
         ResultSet rs = stat.executeQuery(query);
         while (rs.next())
            Auteurs.addItem(rs.getString(1));
         rs.close();

         query = "SELECT Nom FROM Editeurs";
         rs = stat.executeQuery(query);
         while (rs.next())
            Editeurs.addItem(rs.getString(1));
         rs.close();
         stat.close();
      }
      catch(SQLException e)
      {
         result.setText("");
         while (e !=null)
         {
            result.append("" + e);
            e = e.getNextException();
         }
      }
      catch (IOException e)
      {
         result.setText("" + e);
      }

      // Nous utilisons la classe GBC du Chapitre 9 du Volume 1
      add(Auteurs, new GBC(0, 0, 2, 1));

      add(Editeurs, gbc, 2, 0, 2, 1);

      JButton queryButton = new JButton("Requête");
      queryButton.addActionListener(new
          ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               executeQuery();
            }
         });
      add(queryButton, new GBC(0, 1, 1, 1).setInsets(3));

      JButton changeButton = new JButton("Modification des prix");
      changeButton.addActionListener(new
           ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               changePrices();
            }
         });
      add(changeButton, new GBC(2, 1, 1, 1).setInsets(3));

      add(PrixChange, new GBC(3, 1, 1, 1).setFill(GBC.HORIZONTAL));

      add(new JScrollPane(result), new GBC(0, 2, 4, 1).
          setFill(GBC.BOTH).setWeight(100, 100));

      addWindowListener(new
         WindowAdapter()
         {
            public void windowClosing(WindowEvent event)
            {
               try
               {  
                  if (conn != null) conn.close();
               }
               catch(SQLException e) 
               {
                  while (e != null)
                  {
                     e.printStackTrace();
                     e = e.getNextException();
                  }
               }
            }
         });
   }

   /**
      Exécute la requête sélectionnée.
   */
   private void executeQuery()
      {
         ResultSet rs = null;
         try
         {
            String Auteur = (String) Auteurs.getSelectedItem();
            String Editeur = (String) Editeurs.getSelectedItem();
            if (!Auteur.equals("Non précisé")           
                && !Editeur.equals("Non précisé"))
            {
               if (AuteurEditeurQueryStmt == null)
                  AuteurEditeurQueryStmt
                  = conn.prepareStatement(AuteurEditeurQuery);
               AuteurEditeurQueryStmt.setString(1, Auteur);
               AuteurEditeurQueryStmt.setString(2, Editeur);
               rs = AuteurEditeurQueryStmt.executeQuery();
            }
            else if (!Auteur.equals("Non précisé")
               && Editeur.equals("Non précisé"))
            {
               if (AuteurQueryStmt == null)
                  AuteurQueryStmt
                     = conn.prepareStatement(AuteurQuery);
               AuteurQueryStmt.setString(1, Auteur);
               rs = AuteurQueryStmt.executeQuery();
            }
            else if (Auteur.equals("Non précisé")
               && !Editeur.equals("Non précisé"))
            {
               if (EditeurQueryStmt == null)
                  EditeurQueryStmt
                     = conn.prepareStatement(EditeurQuery);
               EditeurQueryStmt.setString(1, Editeur);
               rs = EditeurQueryStmt.executeQuery();
            }
            else
            {
                if (allQueryStmt == null)
                  allQueryStmt = conn.prepareStatement(allQuery);
               rs = allQueryStmt.executeQuery();
            }

            result.setText("");
            while (rs.next())
            {
               result.append(rs.getString(1));
               result.append(", ");
               result.append(rs.getString(2));
               result.append("\n");
         }
         rs.close();
      }
      catch(Exception e)
      {
                result.setText("");
                while (e != null)
         {
            result.append("" + e);
            e = e.getNextException();
         }
      }
   }

   /**
      Exécute une instruction de mise à jour pour modifier les prix.
   */
  public void changePrices()
      {
         String Editeur = (String)Editeurs.getSelectedItem();
         if (Editeur.equals("Non précisé"))
         {
             result.setText
               ("Je suis désolé, je ne peux pas faire cela.");
             return;
          }
          try
          {
             if (prixUpdateStmt == null)
                prixUpdateStmt = conn.prepareStatement(prixUpdate);
             prixUpdateStmt.setString(1, prixChange.getText());
             prixUpdateStmt.setString(2, Editeur);
             int r = prixUpdateStmt.executeUpdate();
             result.setText(r + " enregistrements mis à jour.");
            }
            catch(SQLException e)
            {
                result.setText("");
                while (e != null)
         {
            result.append("" + e);
            e = e.getNextException();
            }
      }
   }

   /**
      Obtient une connexion depuis les propriétés spécifiées
      dans le fichier database.properties
      @return la connexion à la base de données
   */
   public static Connection getConnection()
      throws SQLException, IOException
   {  
      Properties props = new Properties();
      FileInputStream in 
         = new FileInputStream("database.properties");
      props.load(in);
      in.close();

      String drivers = props.getProperty("jdbc.drivers");
      if (drivers != null)
         System.setProperty("jdbc.drivers", drivers);
      String url = props.getProperty("jdbc.url");
      String username = props.getProperty("jdbc.username");
      String password = props.getProperty("jdbc.password");

      return DriverManager.getConnection(url, username, password);
   }

   public static final int DEFAULT_WIDTH = 400;
   public static final int DEFAULT_HEIGHT = 400;

   private JComboBox Auteurs;
   private JComboBox Editeurs;
   private JTextField PrixChange;
   private JTextArea result;
   private Connection conn;
   private PreparedStatement AuteurQueryStmt;
   private PreparedStatement AuteurEditeurQueryStmt;
   private PreparedStatement EditeurQueryStmt;
   private PreparedStatement allQueryStmt;
   private PreparedStatement priceUpdateStmt;

   private static final String AuteurEditeurQuery = 
      "SELECT Livres.Prix, Livres.Titre FROM Livres, LivresAuteurs, 
          Auteurs, Editeurs" +
      " WHERE Auteurs.Auteur_Id = LivresAuteurs.Auteur_Id AND 
          LivresAuteurs.ISBN = Livres.ISBN" +
      " AND Livres.Editeur_Id = Editeurs.Editeur_Id AND Auteurs.Nom
          = ?" + 
      " AND Editeurs.Nom = ?";

   private static final String AuteurQuery =
      "SELECT Livres.Prix, Livres.Titre FROM Livres, LivresAuteurs, 
          Auteurs" +
      " WHERE Auteurs.Auteur_Id = LivresAuteurs.Auteur_Id AND 
          LivresAuteurs.ISBN = Livres.ISBN" +
      " AND Auteurs.Nom = ?";

   private static final String EditeurQuery =
      "SELECT Livres.Prix, Livres.Titre FROM Livres, Editeurs" +
      " WHERE Livres.Editeur_Id = Editeurs.Editeur_Id AND Editeurs.Nom
          = ?";

   private static final String allQuery = "SELECT Livres.Prix, 
          Livres.Titre FROM Livres";

   private static final String prixUpdate =
      "UPDATE Livres " + "SET Prix = Prix + ? " +
      " WHERE Livres.Editeur_Id = (SELECT Editeur_Id FROM Editeurs WHERE 
          Nom = ?)";
}
