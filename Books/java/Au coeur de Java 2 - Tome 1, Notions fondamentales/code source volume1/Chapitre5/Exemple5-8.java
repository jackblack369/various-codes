Exemple 5.8 : MethodPointerTest.java

import java.lang.reflect.*;

public class MethodPointerTest
{  
   public static void main(String[] args) throws Exception
   {  
      /* obtenir les pointeurs sur
         les méthodes square et sqrt */
      Method square = MethodPointerTest.class.getMethod("square",
         double.class);
      Method sqrt = Math.class.getMethod("sqrt", double.class);

      // afficher les tables de valeurs x- et y-

      printTable(1, 10, 10, square);
      printTable(1, 10, 10, sqrt);
   }

    /**
      Renvoie le carré d'un nombre  
      @param x Un nombre 
      @return x au carré
   */
   public static double square(double x) 
   { 
      return x * x; 
   }

    /**
      Affiche une table avec des valeurs x et y pour une méthode
      @param from La limite inférieure des valeurs x
      @param to La limite supérieure des valeurs x 
      @param n Le nombre de rangées dans la table
      @param f Une méthode avec un paramètre double et 
         une valeur renvoyée double
   */
   public static void printTable(double from, double to,
      int n, Method f)
   {  
      // Affiche la méthode comme en-tête de la table 
      System.out.println(f);

       /* construit le formateur pour affichage
         avec une précision de 4 chiffres */

      double dx = (to - from) / (n - 1);

      for (double x = from; x <= to; x += dx)
      {  
         try
         {  
            double y = (Double) f.invoke(null, x);
            System.out.printf("%10.4f | %10.4f%n", x, y);
         } 
         catch (Exception e) { e.printStackTrace(); }
      }
   }
}
