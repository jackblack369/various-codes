Exemple 2.8 : Sieve.java

import java.util.*;

/** 
    Ce programme exécute le test du crible d'Eratosthène.
    Il calcule tous les nombres premiers jusqu'à 2 000 000. 
*/
public class Sieve
{
   public static void main(String[] s)
   {
      int n = 2000000;
      long start = System.currentTimeMillis();
      BitSet b = new BitSet(n + 1);
      int count = 0;
      int i;
      for (i = 2; i <= n; i++)
         b.set(i);
      i = 2;
      while (i * i <= n)
      {
         if (b.get(i))
         {
            count++;
            int k = 2 * i;
            while (k <= n)
            {
               b.clear(k);
               k += i;
            }
         }
         i++;
      }      
      while (i <= n)
      {
         if (b.get(i))
            count++;
         i++;
      }
      long end =  System.currentTimeMillis();
      System.out.println(count + " nombres premiers");
      System.out.println((end - start) +  " millisecondes");
   }
}
