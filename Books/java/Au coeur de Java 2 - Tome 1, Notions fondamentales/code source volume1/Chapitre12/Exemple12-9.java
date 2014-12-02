Exemple 12.9 : RegexTest.java

import java.util.*;
import java.util.regex.*;

/**
   Ce programme teste la correspondance d'expressions ordinaires.
   Entrez un motif et les chaînes à faire concorder ou appuyez sur
   Cancel pour le fermer. Si le motif contient des groupes, les 
   limites du groupe s'affichent dans la correspondance.
*/
public class RegExTest
{
   public static void main(String[] args)
   {
      Scanner in = new Scanner(System.in);
      System.out.println("Enter pattern: ");
      String patternString = in.nextLine();

      Pattern pattern = null;
      try
      {
         pattern = Pattern.compile(patternString);
      }
      catch (PatternSyntaxException e)
      {
         System.out.println("Pattern syntax error");
         System.exit(1);
      }

      while (true)
      {
         System.out.println("Enter string to match: ");
         String input = in.nextLine();        
         if (input == null || input.equals("")) return;
         Matcher matcher = pattern.matcher(input);
         if (matcher.matches())
         {
            System.out.println("Match");
            int g = matcher.groupCount();
            if (g > 0)
            {
               for (int i = 0; i < input.length(); i++)
               {
                  for (int j = 1; j <= g; j++)
                     if (i == matcher.start(j)) 
                        System.out.print('(');
                  System.out.print(input.charAt(i));
                  for (int j = 1; j <= g; j++)
                     if (i + 1 == matcher.end(j)) 
                        System.out.print(')');
               }
               System.out.println();
            }
         }
         else
            System.out.println("No match");
      }
   }
}
