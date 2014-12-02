Exemple 9.19 : Printf4Test.java

import java.io.*;

class Printf4Test
{
   public static void main(String[] args)
   {
      double price = 44.95;
      double tax = 7.75;
      double amountDue = price * (1 + tax / 100);
      PrintWriter out = new PrintWriter(System.out);
      /* Cet appel déclenchera une exception – notez les %% */
      Printf4.fprint(out, "Montant dû = %%8.2f\n", amountDue);
      out.flush();
   }
}
