Exemple 3.2 : ExecSQL.java

import java.io.*;
import java.util.*;
import java.sql.*;

/**
   Exécute toutes les instructions SQL d'un fichier.
   Appelez ce programme comme suit
   java -classpath driverPath:. ExecSQL commandFile
*/
class ExecSQL
{
   public static void main (String args[])
   {
      try
      {
         Scanner in;
         if (args.length == 0)
            in = new Scanner(System.in);
         else
            in = new Scanner(new File(args[0]));
        
         Connection conn = getConnection();
         try
         {
            Statement stat = conn.createStatement();

            while (true)
            {
               if (args.length == 0) System.out.println(
                        "Entrer une commande ou EXIT pour quitter :");

               if (!in.hasNextLine()) return;

               String line = in.nextLine();
               if (line.equalsIgnoreCase("EXIT")) return;
               try
               {
                  boolean hasResultSet = stat.execute(line);
                  if (hasResultSet)
                     showResultSet(stat);
               }
               catch (SQLException e)
               {
                  while (e != null)
                  {  
                     e.printStackTrace();
                     e = e.getNextException();
                  }
               }
            }
         }
         finally
         {
            conn.close();
         }
      }
      catch (SQLException e)
      {
         while (e != null)
         {
            e.printStackTrace ();
            e = e.getNextException();
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
   }

   /**
      Obtient une connexion des propriétés spécifiées
      dans le fichier database.properties
      @return la connexion à la base de données
   */
   public static Connection getConnection()
      throws SQLException, IOException
   {
      Properties props = new Properties();
      FileInputStream in = new FileInputStream("database.properties");
      props.load(in);
      in.close();

      String drivers = props.getProperty("jdbc.drivers");
      if (drivers != null) System.setProperty("jdbc.drivers", drivers);
      String url = props.getProperty("jdbc.url");
      String username = props.getProperty("jdbc.username");
      String password = props.getProperty("jdbc.password");

      return DriverManager.getConnection(url, username, password);
   }

   /**
      Affiche un ensemble de résultats.
      @param stat l'instruction dont l'ensemble de résultats doit 
      être affiché
   */
   public static void showResultSet(Statement stat)
      throws SQLException
   {
      ResultSet result = stat.getResultSet();
      ResultSetMetaData metaData = result.getMetaData();
      int columnCount = metaData.getColumnCount();

      for (int i = 1; i <= columnCount; i++)
      {  
         if (i > 1) System.out.print(", ");
         System.out.print(metaData.getColumnLabel(i));
      }
      System.out.println();

      while (result.next())
      {
         for (int i = 1; i <= columnCount; i++)
         {  
            if (i > 1) System.out.print(", ");
            System.out.print(result.getString(i));
         }
         System.out.println();
      }
      result.close();
   }
}
