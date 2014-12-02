Exemple 4.10 : Warehouse.java

import java.rmi.*;
import java.util.*;

/**
   L'interface distante d'un entrepôt avec des produits.
*/
public interface Warehouse extends Remote
{
   /**
      Récupère les produits correspondant au client.
      @param c le client concerné
      @return une liste de tableau des produits correspondants
   */
  ArrayList<Product> find(Customer c) throws RemoteException;
}
