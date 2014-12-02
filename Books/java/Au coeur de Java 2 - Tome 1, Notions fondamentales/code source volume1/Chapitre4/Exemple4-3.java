Exemple 4.3 : StaticTest.java

public class StaticTest
{
   public static void main(String[] args)
   {
      // remplir le tableau staff avec 3 objets Employee 
      Employee[] staff = new Employee[3];

      staff[0] = new Employee("Tom", 40000);
      staff[1] = new Employee("Dick", 60000);
      staff[2] = new Employee("Harry", 65000);

      // imprimer les informations concernant 
      // tous les objets Employee
      for (Employee e : staff)
      {
         e.setId();
         System.out.println("name=" + e.getName()
            + ",id=" + e.getId()
            + ",salary=" + e.getSalary());
      }

      int n = Employee.getNextId(); // appel méthode statique
      System.out.println("Next available id=" + n);
   }
}

class Employee
{
   public Employee(String n, double s)
   {
      name = n;
      salary = s;
      id = 0;
   }

   public String getName()
   {
      return name;
   }

   public double getSalary()
   {
      return salary;
   }

   public int getId()
   {
      return id;
   }

   public void setId()
   {
      id = nextId; // définir id à prochain id disponible
      nextId++;
   }

   public static int getNextId()
   {
      return nextId; // renvoie un champ statique
   }

   public static void main(String[] args) // test unitaire
   {
      Employee e = new Employee("Harry", 50000);
      System.out.println(e.getName() + " " + e.getSalary());
   }

   private String name;
   private double salary;
   private int id;
   private static int nextId = 1;
}