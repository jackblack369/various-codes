Exemple 9.23 : Win32RegKeyTest.java

import java.util.*;

public class Win32RegKeyTest
{
   public static void main(String[] args)
   {
     Win32RegKey key = new Win32RegKey(
         Win32RegKey.HKEY_LOCAL_MACHINE,
         "Software\\JavSoft\\Java Development Kit\\1.5");

      key.setValue("Utilisateur par défaut", "Bozo le clown");
      key.setValue("Chiffre porte-bonheur", new Integer(13));
      key.setValue("Nombres premiers", new byte[] 
         { 2, 3, 5, 7, 11 });
      
      Enumeration<String> e = key.names();

      while (e.hasMoreElements()) 
      {
         String name = e.nextElement();
         System.out.print(name + " = ");

         Object value = key.getValue(name);

         if (value instanceof byte[])
            for (byte b : (byte[])value) 
            System.out.print((b & 0xFF) +  " ");
         else 
            System.out.print(value);
         
         System.out.println();
      }
   }
}
