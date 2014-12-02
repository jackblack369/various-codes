Exemple 5.1 : ManagerTest.java

import java.util.*;

public class ManagerTest
{  
   public static void main(String[] args)
   {  
      // construire un objet Manager 
      Manager boss = new Manager("Carl Cracker", 80000,
         1987, 12, 15);
      boss.setBonus(5000);

      Employee[] staff = new Employee[3];

      // remplir le tableau staff avec des objets Manager et Employee

      staff[0] = boss;
      staff[1] = new Employee("Harry Hacker", 50000,
         1989, 10, 1);
      staff[2] = new Employee("Tommy Tester", 40000,
         1990, 3, 15);

      // imprimer les infos concernant tous les objets Employee 
      for (Employee e : staff)
         System.out.println("name=" + e.getName()
            + ",salary=" + e.getSalary());
   }
}
 
class Employee
{  
   public Employee(String n, double s,
      int year, int month, int day)
   {  
      name = n;
      salary = s;
      GregorianCalendar calendar
         = new GregorianCalendar(year, month - 1, day);
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

   private String name;
   private double salary;
   private Date hireDay;
}

class Manager extends Employee
{  
   /**
      @param n Nom de l'employé
      @param s Le salaire
      @param year L'année d'embauche
      @param month Le mois d'embauche
      @param day Le jour d'embauche
   */
   public Manager(String n, double s,
      int year, int month, int day)
   {  
      super(n, s, year, month, day);
      bonus = 0;
   }

   public double getSalary()
   { 
      double baseSalary = super.getSalary();
      return baseSalary + bonus;
   }

   public void setBonus(double b)
   {  
      bonus = b;
   }

   private double bonus;
}
