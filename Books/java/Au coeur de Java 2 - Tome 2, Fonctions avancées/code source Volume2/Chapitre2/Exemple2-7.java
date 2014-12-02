Exemple 2.7 : ShuffleTest.java

import java.util.*;

/**
   Ce programme montre les algorithmes de tri et de mélange 
   aléatoire.
*/
public class ShuffleTest
{  
   public static void main(String[] args)
   {  
      List<Integer> numbers = new ArrayList<Integer>();
      for (int i = 1; i <= 49; i++)
         numbers.add(i);
      Collections.shuffle(numbers);
      List<Integer> winningCombination = numbers.subList(0, 6);
      Collections.sort(winningCombination);
      System.out.println(winningCombination);
   }
}
