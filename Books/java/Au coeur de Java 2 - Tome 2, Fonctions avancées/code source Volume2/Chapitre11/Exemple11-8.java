Exemple 11.8 : SetTest.java

import java.util.*;
import java.util.logging.*;

/**
   Ce programme consigne les appels de méthode equals et hashCode 
   lors de l'insertion d'éléments dans un jeu de hachage.
*/
public class SetTest
{  
   public static void main(String[] args)
   {  
      Logger.global.setLevel(Level.FINEST);
      Handler handler = new ConsoleHandler();
      handler.setLevel(Level.FINEST);
      Logger.global.addHandler(handler);

      Set<Item> parts = new HashSet<Item>();
      parts.add(new Item("Grille-pain", 1279));
      parts.add(new Item("Micro-ondes", 4562));
      parts.add(new Item("Grille-pain", 1279));
      System.out.println(parts);
   }
}
