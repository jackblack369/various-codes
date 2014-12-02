Exemple 9.14 : Printf3Test.java

import java.io.*;

class Printf3Test
{
   public static void main(String[] args)
   {
      double price = 44.95;
      double tax = 7.75;
      double amountDue = price * (1 + tax / 100);
      PrintWriter out = new PrintWriter(System.out);
      Printf3.fprint(out,
         "Montant dû = %8.2f\n", amountDue);
      out.flush();
   }
}
