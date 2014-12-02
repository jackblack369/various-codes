Exemple 7.9 : SysPropAction.java

import java.security.*;

/**
   Cette action recherche une propriété système.
*/
public class SysPropAction implements PrivilegeAction
{
   /**
      Construit une action pour rechercher une propriété donnée.
      @param propertyName Le nom de la propriété (comme "user.home")
   */
   public SysPropAction(String propertyName) 
      { this.propertyName = propertyName; }

   public Object run()
   {
      return System.getProperty(propertyName);
   }

   private String propertyName;
}
