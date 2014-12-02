Exemple 12.5 : ObjectRefTest.java

import java.io.*;
import java.util.*;

class ObjectRefTest
{  
   public static void main(String[] args)
   {  
      Employee harry = new Employee("Harry Hacker", 50000,
         1989, 10, 1);
      Manager boss = new Manager("Carl Cracker", 80000,
         1987, 12, 15);
      boss.setSecretary(harry);

      Employee[] staff = new Employee[3];

      staff[0] = boss;
      staff[1] = harry;
      staff[2] = new Employee("Tony Tester", 40000,
         1990, 3, 15);
      
      try
      {  
         // enregistrer tous les dossiers employé dans le 
         // fichier employee.dat
         ObjectOutputStream out = new ObjectOutputStream(new 
            FileOutputStream("employee.dat"));
         out.writeObject(staff);
         out.close();

         // récupérer tous les enregistrements dans un nouveau tableau
         ObjectInputStream in =  new ObjectInputStream(new 
            FileInputStream("employee.dat"));
         Employee[] newStaff = (Employee[])in.readObject();
         in.close();

         // augmenter le salaire du secrétaire
         newStaff[1].raiseSalary(10); 

         // afficher les enregistrements employé nouvellement lus
         for (int i = 0; i < newStaff.length; i++)
            System.out.println(newStaff[i]);
      }
      catch (Exception e)
      {  
         e.printStackTrace(); 
      }
   }
}

class Employee implements Serializable
{
   public Employee() {}

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

class Manager extends Employee
{  
   /**
      Construit un Manager sans secrétaire
      @param n Le nom de l'employé
      @param s Le salaire
      @param year L'année d'embauche
      @param year Le mois d'embauche
      @param year Le jour d'embauche
   */
   public Manager(String n, double s,
      int year, int month, int day)
   {  
      super(n, s, year, month, day);
      secretary = null;
   }

   /**
      Attribue un secrétaire au cadre.
      @param s Le secrétaire
   */
   public void setSecretary(Employee s)
   {  
      secretary = s;
   }

   public String toString()
   {
      return super.toString()
        + "[secretary=" + secretary
        + "]";
   }

   private Employee secretary;
}
