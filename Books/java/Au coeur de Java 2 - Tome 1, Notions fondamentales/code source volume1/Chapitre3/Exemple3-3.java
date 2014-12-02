Exemple 3.3 : Retirement.java

import java.util.*;

public class Retirement
{  
   public static void main(String[] args)
   {  
      // lire les infos entrées
      Scanner input = new Scanner(System.in);

      System.out.print("How much do you need to retire?");
      double goal = in.nextDouble();

      System.out.print("How much money will you contribute every year?");
      double payment =  in.nextDouble();

      System.out.print("Interst rate in %:");
      double interestRate = in.nextDouble();

      double balance = 0;
      int years = 0;

      // mettre à jour le solde du compte tant que cible non atteinte
      while (balance < goal)
      {
         // ajouter versements et intérêts de cette année

         balance += payment;
         double interest = balance * interestRate / 100;
         balance += interest;
         years++;
      }

       System.out.println
         ("You can retire in " + years + " years.");
   }
}
