Exemple 6.6 : StaticInnerClassTest.java

public class StaticInnerClassTest
{  
   public static void main(String[] args)
   {  
      double[] d = new double[20];
      for (int i = 0; i < d.length; i++)
         d[i] = 100 * Math.random();
      ArrayAlg.Pair p = ArrayAlg.minmax(d);
      System.out.println("min = " + p.getFirst());
      System.out.println("max = " + p.getSecond());
   }
}

class ArrayAlg
{  
   /**
      Une paire de nombres à virgule flottante 
   */
   public static class Pair
   { 
      /** 
          Construit une paire à partir de
          deux nombres à virgule flottante
          @param f Le premier nombre 
          @param s Le second nombre 
      */
      public Pair(double f, double s)
      {  
         first = f;
         second = s;
      }
 
      /**
         Renvoie le premier nombre de la paire
         @return le premier nombre 
      */
      public double getFirst()
      {  
         return first;
      }

      /**
         Renvoie le second nombre de la paire
         @return le second nombre 
      */
      public double getSecond()
      {  
         return second;
      }

      private double first;
      private double second;
   }

   /**
      Calcule à la fois le minimum et le maximum d'un tableau
      @param values Un tableau de nombres à virgule flottante 
      @return une paire dont le premier élément est le minimum et 
          dont le second élément est le maximum
   */
   public static Pair minmax(double[] values)
   {  
      double min = Double.MAX_VALUE;
      double max = Double.MIN_VALUE;
      for (double v : values)
      {  
         if (min > v) min = v;
         if (max < v) max = v;
      }
      return new Pair(min, max);
   }
}
