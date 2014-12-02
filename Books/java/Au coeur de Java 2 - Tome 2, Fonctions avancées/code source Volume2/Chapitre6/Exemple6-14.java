Exemple 6.14 : EnumDelegate.java

import java.beans.*;

/**
   Cette classe peut être utilisée pour sauvegarder tous types enum dans 
   une archive JavaBeans.
*/
public class EnumDelegate extends DefaultPersistenceDelegate 
{
   protected Expression instantiate(Object oldInstance, Encoder out) 
   {
      return new Expression(Enum.class,
         "valueOf", 
         new Object[] { oldInstance.getClass(), ((Enum) 
            oldInstance).name() });
   }            
}

