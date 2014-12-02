Exemple 12.7 : FindDirectories.java

import java.io.*;

public class FindDirectories
{  
   public static void main(String[] args)
   {
      // si aucun argument n'est fourni, commencer au répertoire parent
      if (args.length == 0) args = new String[] { ".." };

      try
      {  
         File pathName = new File(args[0]);
         String[] fileNames = pathName.list();

         // énumérer tous les fichiers du répertoire
         for (int i = 0; i < fileNames.length; i++)
         {  
            File f = new File(pathName.getPath(), 
               fileNames[i]);

            // si le fichier est à nouveau un répertoire, appeler 
            // la méthode main de manière récurrente
            if (f.isDirectory())
            {  
               System.out.println(f.getCanonicalPath());
               main(new String [] { f.getPath() });
            }
         }
      }
      catch(IOException e)
      {  
         e.printStackTrace(); 
      }
   }
}
