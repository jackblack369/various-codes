Exemple 11.7 : Item.java

public class Item
{ 
   /**
      Construit un élément.
      @param aDescription La description de l'élément
      @param aPartNumber Le numéro de pièce de l'élément
   */
   public Item(String aDescription, int aPartNumber)
   {  
      description = aDescription;
      partNumber = aPartNumber;
   }

   /**
      Récupère la description de cet élément.
      @return La description
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

   @LogEntry(logger="global") public boolean equals(Object otherObject)
   {  
      if (this == otherObject) return true;
      if (otherObject == null) return false;
      if (getClass() != otherObject.getClass()) return false;
      Item other = (Item) otherObject;
      return description.equals(other.description)
         && partNumber == other.partNumber;
   }

   @LogEntry(logger="global") public int hashCode()
   {  
      return 13 * description.hashCode() + 17 * partNumber;
   }

   private String description;
   private int partNumber;
}
