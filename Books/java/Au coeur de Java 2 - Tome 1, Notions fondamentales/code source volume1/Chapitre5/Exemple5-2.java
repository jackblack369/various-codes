Exemple 5.2 : PersonTest.java

import java.text.*;
import java.util.*;
 
public class PersonTest
{  
   public static void main(String[] args)
   {  
      Person[] people = new Person[2];
 
      // remplir le tableau people avec des objets Student et Employee
      people[0] 
         = new Employee("Harry Hacker", 50000, 1989, 10, 1);
      people[1] 
         = new Student("Maria Morris", "computer science");
 
      // afficher les noms et descriptions de tous les objets Person
      for (Person p : people)
         System.out.println(p.getName() + ", "
            + p.getDescription());
   }
}
 
abstract class Person
{  
   public Person(String n)
   {  
      name = n;
   }
 
   public abstract String getDescription();
 
   public String getName()
   {  
      return name;
   }

   private String name;
}

class Employee extends Person
{  
   public Employee(String n, double s,
      int year, int month, int day)
   {  
      super(n);
      salary = s;
      GregorianCalendar calendar 
         = new GregorianCalendar(year, month - 1, day);
      hireDay = calendar.getTime();
   }

   public double getSalary()
   {  
      return salary;
   }

   public Date getHireDay()
   {  
      return hireDay;
   }

   public String getDescription()
   {  
      return String.format("an employee with a salary of $%.2f, salary);
   }

   public void raiseSalary(double byPercent)
   {  
      double raise = salary * byPercent / 100;
      salary += raise;
   }
 
   private double salary;
   private Date hireDay;
}
 
class Student extends Person
{  
   /**
      @param n Nom de l'étudiant
      @param m La spécialité de l'étudiant
   */
   public Student(String n, String m)
   {  
      // passer n au constructeur de la superclasse
      super(n);
      major = m;
   }
 
   public String getDescription()
   {  
      return "a student majoring in " + major;
   }
 
   private String major;
}
