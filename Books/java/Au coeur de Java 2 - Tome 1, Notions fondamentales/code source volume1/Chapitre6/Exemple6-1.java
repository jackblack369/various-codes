Exemple 6.1 : EmployeeSortTest.java

import java.util.*;

public class EmployeeSortTest
{
   public static void main(String[] args)
   {
      Employee[] staff = new Employee[3];

      staff[0] = new Employee("Harry Hacker", 35000);
      staff[1] = new Employee("Carl Cracker", 75000);
      staff[2] = new Employee("Tony Tester", 38000);

      Arrays.sort(staff);

      // afficher les informations pour tous les objets Employee
      for (Employee e : staff)
         System.out.println("name=" + e.getName()
            + ",salary=" + e.getSalary());
   }
}

class Employee implements Comparable<Employee>
{
   public Employee(String n, double s)
   {
      name = n;
      salary = s;
   }

   public String getName()
   {
     return name;
   }

   public double getSalary()
   {
     return salary;
   }

   public void raiseSalary(double byPercent)
   {
      double raise = salary * byPercent / 100;
      salary += raise;
   }

    /**
      Compare les salaires des employés
      @param other Un autre objet Employee 
      @return une valeur négative si cet employé 
      a un salaire inférieur à celui de otherObject, 
      0 si les salaires sont identiques,
      une valeur positive dans les autres cas
   */
   public int compareTo(Employee other)
   {  
      if (salary < other.salary) return -1;
      if (salary > other.salary) return 1;
      return 0;
   }

   private String name;
   private double salary;
}
