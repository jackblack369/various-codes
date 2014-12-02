Exemple 1.8 : ThreadPoolTest.java

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
 
public class ThreadPoolTest
{
   public static void main(String[] args) throws Exception
   {
      Scanner in = new Scanner(System.in);
      System.out.print("Entrez le répertoire de base 
                        par ex. /usr/local/jdk5.0/src): ");
      String directory = in.nextLine();
      System.out.print("Entrez le mot clé (par ex. volatile) : ");
      String keyword = in.nextLine();     
 
      ExecutorService pool = Executors.newCachedThreadPool();
 
      MatchCounter counter = new MatchCounter(
                      new File(directory), keyword, pool);           
      Future<Integer> result = pool.submit(counter);
 
      try
      {
         System.out.println(result.get() + " fichiers concordants.");
      }
      catch (ExecutionException e)
      {
         e.printStackTrace();
      }
      catch (InterruptedException e) {}
      pool.shutdown();
 
      int largestPoolSize = ((ThreadPoolExecutor) 
                      pool).getLargestPoolSize();
      System.out.println("taille de pool la plus grande =" + 
                      largestPoolSize);
   }
}
 
/**
   Cette tâche compte les fichiers d'un répertoire et de ses 
   sous-répertoires qui contiennent un mot clé donné.
*/
class MatchCounter implements Callable<Integer>
{
   /**
      Construit un MatchCounter.
      @param directory Le répertoire dans lequel commencer la recherche
      @param keyword Le mot clé à rechercher
      @param pool Le pool de threads pour envoyer les sous-tâches
   */
   public MatchCounter(File directory, String keyword, 
                     ExecutorService pool)
   {
      this.directory = directory;      
      this.keyword = keyword;
      this.pool = pool;
   }
 
   public Integer call()
   {
      count = 0;
      try
      {
         File[] files = directory.listFiles();
         ArrayList<Future<Integer>> results = 
                     new ArrayList<Future<Integer>>();
 
         for (File file : files)      
            if (file.isDirectory()) 
            {               
               MatchCounter counter = new MatchCounter(
                     file, keyword, pool);           
               Future<Integer> result = pool.submit(counter);
               results.add(result);
            }
            else 
            {
               if (search(file)) count++;
            }
 
         for (Future<Integer> result : results)
            try
            {
               count += result.get();
            }
            catch (ExecutionException e)
            {
               e.printStackTrace();
            }
      }
      catch (InterruptedException e) {}
      return count;
   }
 
   /**
      Recherche un mot clé donné dans un fichier.
      @param file Le fichier à parcourir
      @return true si le mot clé figure dans le fichier
   */
   public boolean search(File file)
   {     
      try
      {
         Scanner in = new Scanner(new FileInputStream(file));
         boolean found = false;
         while (!found && in.hasNextLine())
         {
            String line = in.nextLine();
            if (line.contains(keyword)) found = true;
         }
         in.close();
         return found;
      }
      catch (IOException e)
      {
         return false;
      }
   }
 
   private File directory;
   private String keyword;
   private ExecutorService pool;
   private int count;
}
