Exemple 1.6 : BlockingQueueTest.java

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
 
public class BlockingQueueTest
{
   public static void main(String[] args)
   {
      Scanner in = new Scanner(System.in);
      System.out.print("Entrez le répertoire de base 
                       (par ex. /usr/local/jdk5.0/src) : ");
      String directory = in.nextLine();
      System.out.print("Entrez le mot clé (par ex. volatil) : ");
      String keyword = in.nextLine();     
 
      final int FILE_QUEUE_SIZE = 10;
      final int SEARCH_THREADS = 100;
 
      BlockingQueue<File> queue = new 
                 ArrayBlockingQueue<File>(FILE_QUEUE_SIZE);
 
      FileEnumerationTask enumerator = new FileEnumerationTask(queue, 
                 new File(directory));
      new Thread(enumerator).start();
      for (int i = 1; i <= SEARCH_THREADS; i++)
         new Thread(new SearchTask(queue, keyword)).start();
   }
}
 
/**
   Cette tâche recense tous les fichiers d'un répertoire et de 
   ses sous-répertoires.
*/
class FileEnumerationTask implements Runnable
{
   /**
      Construit un FileEnumerationTask.
      @param queue la file d'attente de blocage à laquelle sont ajoutés
                   les fichiers recensés
      @param startingDirectory le répertoire dans lequel commencer 
                   le recensement
   */
   public FileEnumerationTask(BlockingQueue<File> queue, 
                   File startingDirectory)
   {
      this.queue = queue;
      this.startingDirectory = startingDirectory;      
   }
 
   public void run()
   {
      try
      {
         enumerate(startingDirectory);
         queue.put(DUMMY); 
      }
      catch (InterruptedException e) {}
   }
 
   /**
      Recense tous les fichiers d'un répertoire donné et de 
      ses sous-répertoires 
      @param directory le répertoire où commencer
   */
   public void enumerate(File directory) throws InterruptedException
   {     
      File[] files = directory.listFiles();
      for (File file : files)      {
         if (file.isDirectory()) enumerate(file);
         else queue.put(file); 
      }
   }
 
   public static File DUMMY = new File("");
 
   private BlockingQueue<File> queue;
   private File startingDirectory;
}
 
/**
   Cette tâche recherche les fichiers pour un mot clé donné.
*/
class SearchTask implements Runnable
{
   /**
      Construit un SearchTask.
      @param queue la file à partir de laquelle prendre les fichiers
      @param keyword le mot clé à rechercher
   */
   public SearchTask(BlockingQueue<File> queue, String keyword)
   {
      this.queue = queue;
      this.keyword = keyword;
   }
 
   public void run()
   {
      try
      {
         boolean done = false;
         while (!done)
         {
            File file = queue.take();
            if (file == FileEnumerationTask.DUMMY) { queue.put(file); 
                            done = true; }
            else search(file);
         }
      }
      catch (IOException e) { e.printStackTrace(); }
      catch (InterruptedException e) {}
   }
 
   /**
      Recherche un fichier pour un mot clé donné et affiche 
      toutes les lignes correspondantes.
      @param file le fichier à rechercher
   */
   public void search(File file) throws IOException
   {     
      Scanner in = new Scanner(new FileInputStream(file));
      int lineNumber = 0;
      while (in.hasNextLine())
      {
         lineNumber++;         
         String line = in.nextLine();
         if (line.contains(keyword))
            System.out.printf("%s:%d:%s%n", file.getPath(), 
                         lineNumber, line);
      }
      in.close();
   }
 
   private BlockingQueue<File> queue;
   private String keyword;
}
