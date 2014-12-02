Exemple 12.10 : HrefMatch.java

import java.io.*;
import java.net.*;
import java.util.regex.*;

/**
   Ce programme affiche toutes les URL d'une page Web par 
   correspondance d'une expression ordinaire qui décrit la
   balise HTML <a href=...>. Lance le programme comme 
   java HrefMatch URL
*/
public class HrefMatch
{
   public static void main(String[] args)
   {
      try
      {
         // récupérer la chaîne URL à partir de la ligne de commande ou
         // utiliser celle par défaut
         String urlString;
         if (args.length > 0) urlString = args[0];
         else urlString = "http://java.sun.com";

         // ouvrir le lecteur pour l'URL
         InputStreamReader in = new InputStreamReader(
            new URL(urlString).openStream());

         // lire le contenu dans le tampon des chaînes
         StringBuilder input = new StringBuilder();
         int ch;
         while ((ch = in.read()) != -1) input.append((char) ch);

         // rechercher toutes les occurrences du motif
         String patternString =
            "<a\\s+href\\s*=\\s*(\"[^\"]*\"|[^\\s>])\\s*>";
         Pattern pattern = Pattern.compile(
            patternString, Pattern.CASE_INSENSITIVE);
         Matcher matcher = pattern.matcher(input);

         while (matcher.find())
         {
            int start = matcher.start();
            int end = matcher.end();
            String match = input.substring(start, end);
            System.out.println(match);
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      catch (PatternSyntaxException e)
      {
         e.printStackTrace();
      }
   }
}
