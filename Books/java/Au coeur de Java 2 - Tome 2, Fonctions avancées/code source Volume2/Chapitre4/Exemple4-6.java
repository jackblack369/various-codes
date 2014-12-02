Exemple 4.6 : Product.java

import java.rmi.*;

/**
   L'interface des objets product distants.
*/
public interface Product extends Remote
{
   /**
      Récupère la description de ce produit.
      @return la description du produit
   */
   String getDescription() throws RemoteException;
}
