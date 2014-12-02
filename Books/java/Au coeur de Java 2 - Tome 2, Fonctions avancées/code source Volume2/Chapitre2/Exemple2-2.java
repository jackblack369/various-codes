Exemple 2.2 : SetTest.java

import java.util.*;

/**
   Ce programme utilise un set pour imprimer tous les mots uniques dans 
   System.in.
*/
public class SetTest
{ 
   public static void main(String[] args)
   {  
      Set<String> words = new HashSet<String>(); //HashSet implémente Set
         // définit un HashSet, LinkedHashSet or TreeSet
      long totalTime = 0;

      Scanner in = new Scanner(System.in);
      while (in.hasNext())
      {  
         String word = in.next();
         long callTime = System.currentTimeMillis();
         words.add(word);
         callTime = System.currentTimeMillis() - callTime;
         totalTime += callTime;
 
      }
 
      Iterator<String> iter = words.iterator();
      for (int i = 1; I <= 20; i++)
         System.out.println(iter.next());
      System.out.println("...");
      System.out.println(words.size()
         + " mots différents. " + totalTime + " millisecondes.");
   }
}
