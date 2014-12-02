Exemple 2.5 : MapTest.java

import java.util.*;

/**
   Ce programme montre l'utilisation d'une carte avec le type de clé
   String et le type de valeur Employee.
*/
public class MapTest
{  
   public static void main(String[] args)
   {  
      Map<String, Employee> staff = new HashMap<String, Employee>();
      staff.put("144-25-5464", new Employee("Nicolas Babled"));
      staff.put("567-24-2546", new Employee("Ludovic Bertin"));
      staff.put("157-62-7935", new Employee("Nicolas Serres"));
      staff.put("456-62-5527", new Employee("Pierre Dumas"));

      // imprime toutes les entrées

      System.out.println(staff);

      // supprime une entrée

      staff.remove("567-24-2546");

      // remplace une entrée

      staff.put("456-62-5527", new Employee("David Fontanella"));

      // recherche une valeur

      System.out.println(staff.get("157-62-7935"));

      // parcourt toutes les entrées

      for (Map.Entry<String, Employee> entry : staff.entrySet();
      {
         String key = entry.getKey();
         Employee value = entry.getValue();
         System.out.println("key=" + key + ", value=" + value);
      }
   }
}

/**
   Une classe d'employés minimaliste pour un objectif de test.
*/
class Employee
{ 
   /**
      Génère un employé avec un salaire de $0.
      @param n le nom de l'employé
   */
   public Employee(String n)
   {  
      name = n;
      salary = 0;
   }

   public String toString()
   {  
      return "[name=" + name + ", salary=" + salary + "]";
   }

   private String name;
   private double salary;
}
