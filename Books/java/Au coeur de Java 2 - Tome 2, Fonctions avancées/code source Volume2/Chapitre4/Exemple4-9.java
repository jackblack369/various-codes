Exemple 4.9 : Customer.java

import java.io.*;

/**
   Description d'un client. Sachez que les objets customer ne sont pas 
   distants--la classe n'implémente pas d'interface distante.
*/
public class Customer implements Serializable
{
  /**
     Construits un client.
     @param theAge l'âge du client
     @param theSex le sexe du client (MASCULIN ou FEMININ)
     @param theHobbies les loisirs du client
  */
  public Customer(int theAge, int theSex, String[] theHobbies)
   {
      age = theAge;
      sex = theSex;
      hobbies = theHobbies;
   }

   /**
      Récupère l'âge du client.
      @return l'âge
   */
   public int getAge() { return age; }

   /**
      Récupère le sexe du client
      @return MASCULIN ou FEMININ
   */
   public int getSex() { return sex; }

   /**
      Teste si ce client a un loisir particulier.
      @param aHobby le loisir à tester
      @return true si ce client a choisi le loisir
   */
   public boolean hasHobby(String aHobby)
   {
      if (aHobby == "") return true;
      for (int i = 0; i < hobbies.length; i++)
         if (hobbies[i].equals(aHobby)) return true;

      return false;
   }

   /**
      Réinitialise cet enregistrement client sur ses valeurs par défaut.
   */
   public void reset()
   {
      age = 0;
      sex = 0;
      hobbies = null;
   }

   public String toString()
   {
      String result = "Age: " + age + ", Sexe: ";
      if (sex == Product.MALE) result += "masculin";
      else if (sex == Product.FEMALE) result += "féminin";
      else result += " masculin ou féminin ";
      result += ", Hobbies:";
      for (int i = 0; i < hobbies.length; i++)
         result += " " + hobbies[i];
      return result;
   }

   private int age;
   private int sex;
   private String[] hobbies;
}
