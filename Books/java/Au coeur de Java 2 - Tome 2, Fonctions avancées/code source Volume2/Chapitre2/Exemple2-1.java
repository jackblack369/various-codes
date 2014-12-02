Exemple 2.1 : LinkedListTest.java

import java.util.*;

/**
   Ce programme présente le fonctionnement des listes chaînées.
*/
public class LinkedListTest
{  
   public static void main(String[] args)
   {  
      List<String> a = new LinkedList<String>();
      a.add("Claire");
      a.add("Carl");
      a.add("Erica");

      List<String> b = new LinkedList<String>();
      b.add("Bob");
      b.add("Doug");
      b.add("Frances");
      b.add("Gloria");

      // fusionne les mots de b dans a

      ListIterator<String> aIter = a.listIterator();
      Iterator<String> bIter = b.iterator();

      while (bIter.hasNext())
      {  
         if (aIter.hasNext()) aIter.next();
         aIter.add(bIter.next());
      }

      System.out.println(a);

      // supprime un mot sur deux dans b

      bIter = b.iterator();
      while (bIter.hasNext())
      {  
         bIter.next(); // saute un élément
         if (bIter.hasNext())
         {  
            bIter.next(); // saute un élément
            bIter.remove(); // supprime cet élément
         }
      }

      System.out.println(b);

      // Opération globale : supprime tous les mots dans b de a

      a.removeAll(b);

      System.out.println(a);
   }
}
