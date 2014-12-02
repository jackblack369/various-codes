Exemple 4.2 : ProductImpl.java

import java.rmi.*;
import java.rmi.server.*;

/**
   Il s'agit d'une classe d'implémentation pour les objets 
   du produit distant.
*/
public class ProductImpl
   extends UnicastRemoteObject
   implements Product
{
   /**
      Construit une implémentation de produit
      @param n le nom du produit
   */
   public ProductImpl(String n) throws RemoteException
   {
      name = n;
   }

   public String getDescription() throws RemoteException
   {
       return "Je suis un " + name + ". Achetez-moi!";
   }

   private String name;
}
