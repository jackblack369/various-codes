Exemple 4.2 : EmployeeTest.java

import java.util.*;

public class EmployeeTest
{  
   public static void main(String[] args)
   {  
      // remplir le tableau staff avec trois objets Employee 
      Employee[] staff = new Employee[3];

      staff[0] = new Employee("Carl Cracker", 75000,
         1987, 12, 15);
      staff[1] = new Employee("Harry Hacker", 50000,
         1989, 10, 1);
      staff[2] = new Employee("Tony Tester", 40000,
         1990, 3, 15);

      // augmenter tous les salaires de 5%
      for (Employee e : staff)
         e.raiseSalary(5);

      // afficher les informations concernant 
      // tous les objets Employee
      for (Employee e : staff)
         System.out.println("name=" + e.getName()
            + ",salary=" + e.getSalary()
            + ",hireDay=" + e.getHireDay());
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
         // Avec GregorianCalendar 0 désigne janvier
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
