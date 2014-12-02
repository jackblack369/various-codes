Exemple 12.6 : SerialCloneTest.java

import java.io.*;
import java.util.*;

public class SerialCloneTest
{  
   public static void main(String[] args)
   {  
      Employee harry = new Employee("Harry Hacker", 35000,
         1989, 10, 1);
      // cloner Harry
      Employee harry2 = (Employee)harry.clone();

      // muter Harry
      harry.raiseSalary(10);

      // maintenant Harry et le clone sont différents
      System.out.println(harry);
      System.out.println(harry2);
   }
}

/**
   Une classe dont la méthode clone utilise la sérialisation.
*/
class SerialCloneable implements Cloneable, Serializable
{  
   public Object clone()
   {  
      try
      {  
         // enregistrer l'objet dans un tableau d'octets
         ByteArrayOutputStream bout = new 
            ByteArrayOutputStream();
         ObjectOutputStream out 
            = new ObjectOutputStream(bout);
         out.writeObject(this);
         out.close();

         // lire un clone de l'objet dans le tableau d'octets
         ByteArrayInputStream bin = new 
            ByteArrayInputStream(bout.toByteArray());
         ObjectInputStream in = new ObjectInputStream(bin);
         Object ret = in.readObject();
         in.close();

         return ret;
      }  
      catch (Exception e)
      {  
         return null;
      }
   }
}

/**
   La classe connue Employee, redéfinie pour étendre 
   la classe SerialCloneable. 
*/
class Employee extends SerialCloneable
{  
   public Employee(String n, double s, 
      int year, int month, int day)
   {  
      name = n;
      salary = s;
      GregorianCalendar calendar
         = new GregorianCalendar(year, month - 1, day);
         // GregorianCalendar utilise 0 = janvier
      hireDay = calendar.getTime();
   }

   public String getName()
   {  
      return name;
   }

   public double getSalary()
   {  
      return salary;
   }

   public Date getHireDay()
   {  
      return hireDay;
   }

   public void raiseSalary(double byPercent)
   {  
      double raise = salary * byPercent / 100;
      salary += raise;
   }

   public String toString()
   {  
      return getClass().getName()
         + "[name=" + name
         + ",salary=" + salary
         + ",hireDay=" + hireDay
         + "]";
   }

   private String name;
   private double salary;
   private Date hireDay;
}
