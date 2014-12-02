Exemple 4.7 : Employee.java

package com.horstmann.corejava;
   // les classes de ce fichier font partie de ce package

import java.util.*;
   // les instructions import viennent après l'instruction package 

public class Employee
{  
   public Employee(String n, double s,
      int year, int month, int day)
   {
      name = n;
      salary = s;
      GregorianCalendar calendar
         = new GregorianCalendar(year, month - 1, day);
         // GregorianCalendar utilise 0 pour janvier
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
