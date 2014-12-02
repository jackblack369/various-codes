Exemple 7.2 : Caesar.java

import java.io.*;

/**
   Crypte un fichier à l'aide du code Caesar.
*/
public class Caesar
{
   public static void main(String[] args)
   {
      if (args.length != 3)
      {
         System.out.println("USAGE: clé entrée sortie Caesar java");
         return;
      }

      try
      {
         FileInputStream in = new FileInputStream(args[0]);
         FileOutputStream out = new FileOutputStream(args[1]);
         int key = Integer.parseInt(args[2]);
         int ch;
         while ((ch = in.read()) != -1)
         {
            byte c = (byte)(ch + key);
            out.write(c);
         }
         in.close();
         out.close();
      }
      catch (IOException exception)
      {
         exception.printStackTrace();
      }
   }
}
