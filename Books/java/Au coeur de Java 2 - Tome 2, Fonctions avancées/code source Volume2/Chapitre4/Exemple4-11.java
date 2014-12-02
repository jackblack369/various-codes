Exemple 4.11 : WarehouseImpl.java

import java.io.*;
import java.rmi.*;
import java.util.*;
import java.rmi.server.*;
import java.util.*;
import java.util.concurrent.locks.*;

/**
   Cette classe correspond à l'implémentation de l'interface
   distante de l'entrepôt.
*/
public class WarehouseImpl
   extends UnicastRemoteObject
   implements Warehouse
{
   /**
      Construit une implémentation d'entrepôt.
   */
   public WarehouseImpl()
      throws RemoteException
   {
     products = new ArrayList<ProductImpl>();
     add(new ProductImpl("Core Java Book",
         0, 200, Product.BOTH, "Ordinateurs"));
   }

   /**
      Ajoute un produit à l'entrepôt. Ceci est une méthode locale.
      @param p Le produit à ajouter
   */
   public void add(ProductImpl p)
   {  
      Lock wlock = rwlock.writeLock();
      wlock.lock();
      try 
      {
         products.add(p);
      }
      finally
      {
         wlock.unlock();
      }
   }

   public ArrayList<Product> find(Customer c)
      throws RemoteException
   {
      Lock rlock = rwlock.readLock();
      rlock.lock();
      try 
      {
      ArrayList<Product> result = new ArrayList<Product>();
      //ajoute tous les produits correspondants
      for (ProductImpl p : products)
      {
         if (p.match(c)) result.add(p);
      }
      //ajoute le produit correspondant à tous, une copie de CoreJava
      if (!result.contains(products.get(0)))
         result.add(products.get(0));

      //nous réinitialisons c simplement pour montrer que c est
      //une copie de l'objet client
      c.reset();
      return result;
      }
      finally
      {
         rlock.unlock();
      }
   }

   private ArrayList<ProductImpl> products;
   private ReadWriteLock rwlock = new ReentrantReadWriteLock();
}
