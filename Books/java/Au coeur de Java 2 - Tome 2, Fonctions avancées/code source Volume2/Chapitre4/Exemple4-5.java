Exemple 4.5 : ProductClient.java

import java.rmi.*;
import java.rmi.server.*;
import javax.naming.*;

/**
   Ce programme montre comment appeler une méthode distante
   sur deux objets localisés grâce au service de nom.
*/
public class ProductClient
{
   public static void main(String[] args)
   {
      System.setProperty("java.security.policy", "client.policy");
      System.setSecurityManager(new RMISecurityManager());
      String url = "rmi://hôte_local/";
         // utilisez "rmi://votre_serveur.com/"
         // lorsque le serveur se trouve sur la machine distante
         // votre_serveur.com
      try
      {
         Context namingContext = new InitialContext();         
         Product c1 = (Product) namingContext.lookup(url + "grille-pain");
         Product c2 = (Product) namingContext.lookup(url + "micro-ondes");
         System.out.println(c1.getDescription());
         System.out.println(c2.getDescription());
      }
      catch(Exception e)
      {
         e.printStackTrace();
      }
   }
}
