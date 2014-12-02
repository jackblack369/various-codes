Exemple 2.3 : TreeSetTest.java

import java.util.*;

/**
   Ce programme trie un set d'éléments en comparant 
   leurs descriptions.
*/
public class TreeSetTest
{  
   public static void main(String[] args)
   {  
      SortedSet<Item> parts = new TreeSet<Item>();
      parts.add(new Item("Grille-pain", 1234));
      parts.add(new Item("Accessoire", 4562));
      parts.add(new Item("Modem", 9912));
      System.out.println(parts);

      SortedSet<Item> sortByDescription = new TreeSet<Item>(new
         Comparator<Item>()
         {  
            public int compare(Item a, Item b)
            {  
               String descrA = a.getDescription();
               String descrB = b.getDescription();
               return descrA.compareTo(descrB);
            }
         });

      sortByDescription.addAll(parts);
      System.out.println(sortByDescription);
   }
}

/**
   Un élément avec une description et un numéro de code.
*/
class Item implements Comparable<Item>
{ 
   /**
      Construit un élément.
      @param aDescription la description de l'élément
      @param aPartNumber le numéro de code de l'élément
   */
   public Item(String aDescription, int aPartNumber)
   {  
      description = aDescription;
      partNumber = aPartNumber;
   }

   /**
      Obtient la description de cet élément.
      @return la description
   */
   public String getDescription()
   {  
      return description;
   }

   public String toString()
   {  
      return "[description=" + description
         + ", partNumber=" + partNumber + "]";
   }

   public boolean equals(Object otherObject)
   {  
      if (this == otherObject) return true;
      if (otherObject == null) return false;
      if (getClass() != otherObject.getClass()) return false;
      Item other = (Item) otherObject;
      return description.equals(other.description)
         && partNumber == other.partNumber;
   }

   public int hashCode()
   {  
      return 13 * description.hashCode() + 17 * partNumber;
   }

   public int compareTo(Item other)
   {  
      return partNumber - other.partNumber;
   }

   private String description;
   private int partNumber;
}
