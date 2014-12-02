Exemple 4.12 : WarehouseServer.java

import java.rmi.*;
import java.rmi.server.*;
import javax.naming.*;

/**
   Ce programme serveur instancie un objet d'un entrepôt
   distant, l'enregistre auprès du service de nom et attend
   que les clients invoquent les méthodes.
*/
public class WarehouseServer
{
   public static void main(String[] args)
   {
      try
      {
         System.out.println
            ("Construction des implémentations du serveur...");

         WarehouseImpl w = new WarehouseImpl();
         w.add(new ProductImpl("Grille-pain Moulinex", Product.BOTH, 
               18, 200, "Electroménager"));
         w.add(new ProductImpl("Micro-ondes Philips", Product.BOTH, 
               18, 200, "Electroménager"));
         w.add(new ProductImpl("Pelle à vapeur MécaTerrassement", 
               Product.MALE, 20, 60, "Jardinage"));
         w.add(new ProductImpl("Désherbant U238", Product.BOTH, 
               20, 200, "Jardinage"));
         w.add(new ProductImpl("Fragrance Java persistante", 
               Product.FEMALE, 15, 45, "Beauté"));
         w.add(new ProductImpl("Souris informatique BlackRongeur", 
               Product.BOTH, 6, 40, "Ordinateurs"));
         w.add(new ProductImpl("Mon premier Expresso", Product.FEMALE, 
               6, 10, "Electroménager"));
         w.add(new ProductImpl("Eau de Cologne JavaJungle", Product.MALE, 
               15, 45, "Beauté"));
         w.add(new ProductImpl("Machine à Expresso FireWire", 
               Product.BOTH, 20, 50, "Ordinateurs"));
         w.add(new ProductImpl("Livre Les mauvaises habitudes de Java en 
               21 jours", Product.BOTH, 20, 200, "Ordinateurs"));

         System.out.println
            ("Liaison des implémentations du serveur à la base de registres...");
         Context namingContext = new InitialContex();
         namingContext.bind("rmi:central_warehouse", w);

         System.out.println
            ("Attente des invocations des clients...");
      }
      catch (Exception e)
      {
         e.printlnStackTrace();
      }
   }
}
