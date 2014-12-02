Exemple 11.1 : StackTraceTest.java

import java.util.*;

/**
   Un programme qui affiche une fonction de trace d'un
   appel de méthode récurrent.
*/
public class StackTraceTest
{
   /**
      Calcule la factorielle d'un nombre
      @param n Un entier non-négatif
      @return n! = 1 * 2 * . . . * n
   */
   public static int factorial(int n)
   {
      System.out.println("factorial(" + n + "):");
      Throwable t = new Throwable();
      StackTraceElement[] frames = t.getStackTrace();
      for (StackTraceElement f : frames)
         System.out.println(f);
      int r;
      if (n <= 1) r = 1;
      else r = n * factorial(n - 1);
      System.out.println("return " + r);
      return r;
   }

   public static void main(String[] args)
   {
      Scanner in = new Scanner(System.in);
      System.out.print("Enter n: ");
      int n = in.nextInt();
      factorial(n);
   }
}
