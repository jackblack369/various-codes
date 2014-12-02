Exemple 4.4 : ShowBindings.java

import java.rmi.*;
import java.rmi.server.*;
import javax.naming.*;

/**
   Ce programme présente toutes les liaisons RMI.
*/
public class ShowBindings
{
   public static void main(String[] args)
   {
      try
      {
         Context namingContext = new InitialContext();
         NamingEnumeration<NameClassPair> e = namingContext.list("rmi:");
         while (e.hasMore())
            System.out.println(e.next().getName());
      }
      catch(Exception e)
      {
         e.printStackTrace();
      }
   }
}
